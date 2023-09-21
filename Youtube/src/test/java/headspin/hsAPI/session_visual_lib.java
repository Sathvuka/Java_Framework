package headspin.hsAPI;

import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static headspin.basicFunctionalities.PropertyFileReader.getConfigProperty;
import static headspin.globalVariables.GlobalVariables.*;
public class session_visual_lib {
    static long video_start_timestamp;
    static BigDecimal captureStarted;
    public static long segmentTimeStep;

   public static Map<String,Object> pageload;

    public static void run_record_session_info() throws IOException {
        run_add_annotation_data();
        run_add_session_data();
    }

    public static void run_add_annotation_data() throws IOException {
        get_video_start_timestamp();
        wait_for_session_video_becomes_available();
        add_kpi_labels(kpi_labels,KPI_LABEL_CATEGORY);
    }



    public static void get_video_start_timestamp() throws IOException {
        Map<String, BigDecimal> capture_timestamp = new HashMap<>();
        boolean wait_until_capture_complete = true;
        try {
            if (wait_until_capture_complete) {
                System.out.println("Session ID" +sessionID);
                String jsonResponse=HsAPI.get_capture_timestamp(sessionID);
                JSONObject json = new JSONObject(jsonResponse);
                captureStarted = (BigDecimal) json.get("capture-started");
                capture_timestamp.put("capture-started",captureStarted);
                video_start_timestamp =capture_timestamp.get("capture-started").multiply(BigDecimal.valueOf(1000)).longValue();
            } else {
                String jsonResponse=HsAPI.get_capture_timestamp(sessionID);
                JSONObject json = new JSONObject(jsonResponse);
                captureStarted = (BigDecimal) json.get("capture-started");
                capture_timestamp.put("capture-started", captureStarted);
                video_start_timestamp = capture_timestamp.get("capture-started").multiply(BigDecimal.valueOf(1000)).longValue();
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static void wait_for_session_video_becomes_available() throws IOException {
        String status= HsAPI.get_session_video_metadata(sessionID);
        if (status.contains("video_duration_ms")){
            System.out.println(status);
        }
    }
    public static void add_kpi_labels(Map<String,Map<String,String>> kpi_labels, String KPI_LABEL_CATEGORY) throws IOException {
        System.out.println(kpi_labels);
        for (String label_key : kpi_labels.keySet()) {
            float start_sensitivity = 0, end_sensitivity = 0;
            long new_label_start_time = 0, new_label_end_time = 0;
            Map<String, String> label = kpi_labels.get(label_key);
            System.out.println(label.get("start")+":"+label_key+" starttime");
            System.out.println(label.get("end")+":"+label_key+" endtime");

            if (label.containsKey("start") && label.containsKey("end")) {
                System.out.println(video_start_timestamp);
                long labelStartTime = Long.parseLong(label.get("start"))- video_start_timestamp;
                long labelEndTime = Long.parseLong(label.get("end")) - video_start_timestamp;

                if (labelStartTime < 0)
                    labelStartTime = (long) 0.01F;
                HsAPI.add_label(sessionID, label_key, "desired region", ((float)labelStartTime/1000) , ((float)labelEndTime/1000));

                if (label.containsKey("start_sensitivity"))
                    start_sensitivity = Float.parseFloat(label.get("start_sensitivity"));
                if (label.containsKey("end_sensitivity"))
                    end_sensitivity = Float.parseFloat(label.get("end_sensitivity"));

                if (label.containsKey("segment_start") && label.containsKey("segment_end")) {
                       List screen_change_list = getScreenChangeListDivide(label_key, labelStartTime, labelEndTime, start_sensitivity, end_sensitivity);
                    try {
                        if (screen_change_list != null && !screen_change_list.isEmpty()) {
                            long segmentStart = Long.parseLong(label.get("segment_start"));
                            long segmentEnd = Long.parseLong(label.get("segment_end"));
                            if (segmentStart<0)
                                segmentStart=screen_change_list.size()+segmentStart;
                            if(segmentEnd<0)
                                segmentEnd=screen_change_list.size()+segmentEnd;
                            if (segmentStart < screen_change_list.size() && segmentEnd < screen_change_list.size()) {
                                 Long segment_start= (Long)screen_change_list.get((int) segmentStart);
                                 Long segment_end=(Long)screen_change_list.get((int) segmentEnd);
                                 new_label_start_time = segment_start.longValue();
                                 new_label_end_time = segment_end.longValue();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                 } else {
                    if (label.containsKey("start") && label.containsKey("end")) {
                        pageload = HsAPI.get_pageloadtime(sessionID, label_key, labelStartTime, labelEndTime, start_sensitivity , end_sensitivity );
                        if (pageload.containsKey("page_load_regions")) {
                            List<Map<String, Object>> pageLoadRegions = (List<Map<String, Object>>) pageload.get("page_load_regions");
                            if (!pageLoadRegions.isEmpty() && !pageLoadRegions.get(0).containsKey("error_msg")) {
                                Integer starttime= (Integer) pageLoadRegions.get(0).get("start_time");
                                Integer endtime=(Integer) pageLoadRegions.get(0).get("end_time");
                                new_label_start_time = starttime.longValue();
                                new_label_end_time = endtime.longValue();
                            }
                            System.out.println("New Label Start Time: " + new_label_start_time);
                            System.out.println("New Label End Time: " + new_label_end_time);
                        }
                    }
                   }
                if(new_label_start_time != 0 &&  new_label_end_time!= 0)
               {
                    kpi_labels.get(label_key).put("start", String.valueOf(new_label_start_time));
                    kpi_labels.get(label_key).put("end", String.valueOf(new_label_end_time));
                    // Validate KPI = 0
                    float kpiValue = new_label_end_time - new_label_start_time;
                    if (Math.abs(kpiValue) == 0.0) {
                        kpi_labels.get(label_key).put("start", String.valueOf(new_label_start_time -50));
                        kpi_labels.get(label_key).put("end", String.valueOf(new_label_end_time +50));
                    }
                    System.out.println(sessionID + " " + label_key + " " + KPI_LABEL_CATEGORY + " " + (float)(new_label_start_time) / 1000 + " " +(float) (new_label_end_time) / 1000);
             }
                HsAPI.add_label(sessionID,label_key,KPI_LABEL_CATEGORY,((float)new_label_start_time)/1000, ((float)new_label_end_time)/1000);
            }
        }
    }

   public static List getScreenChangeListDivide(String label_key, float labelStartTime, float labelEndTime, float start_sensitivity, float end_sensitivity) {
       List screenChangeList = new ArrayList<>();
       int sn = 0;
       int snLimit = 10;
       int segmentTimeStep = 100;
       long new_label_start_time = 0, new_label_end_time = 0;
       try {
           segmentTimeStep = (int) session_visual_lib.segmentTimeStep;
       } catch (NullPointerException e) {
           System.err.println("Null Pointer Error");
       }
       pageload = HsAPI.get_pageloadtime(sessionID, (label_key + sn), labelStartTime, labelEndTime, start_sensitivity , end_sensitivity );
       if (pageload.containsKey("page_load_regions") && !(pageload.get("page_load_regions").toString().contains("error_msg"))) {
           while (true) {
               List<Map<String, Object>> pageLoadRegions = (List<Map<String, Object>>) pageload.get("page_load_regions");
               Integer start_time= (Integer) pageLoadRegions.get(0).get("start_time");
               Integer end_time=(Integer) pageLoadRegions.get(0).get("end_time");
               screenChangeList.add(start_time.longValue());
               screenChangeList.add(end_time.longValue());
               sn++;
               if (snLimit < sn) {
                   break;
               }
               Integer starttime= (Integer) pageLoadRegions.get(0).get("start_time") +segmentTimeStep;
               Integer endtime=(Integer) pageLoadRegions.get(0).get("end_time") -segmentTimeStep;
               new_label_start_time = starttime.longValue();
               new_label_end_time = endtime.longValue();
               if (new_label_start_time > new_label_end_time) {
                   break;
               }
               pageload = HsAPI.get_pageloadtime(sessionID, (label_key + sn),new_label_start_time, new_label_end_time,start_sensitivity, end_sensitivity);
               if (pageLoadRegions.get(0).get("start_time") ==null || pageLoadRegions.get(0).get("end_time") ==null || (pageload.get("page_load_regions").toString().contains("error_msg")))
                   break;

               if(!pageload.containsKey("page_load_regions") || "error_msg".equals(pageload.get("page_load_regions").toString())) {
                   if (pageload.containsKey("status")) {
                       System.err.println("ScreenChange Failed");
                   }
                   break;
               }
           }
       } else {
           System.err.println("ScreenChange Failed");
       }
       screenChangeList = (List) screenChangeList.stream().distinct().sorted().collect(Collectors.toList());
       System.out.println(label_key + " " + screenChangeList);

       return screenChangeList;

    }
    public static void  run_add_session_data() throws IOException {
        Map<String, Object> sessionData = getGeneralSessionData();
        HsAPI.addSessionData(sessionData);
        String descriptionString = "";
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) sessionData.get("data");
        for (Map<String, Object> data : dataList) {
            descriptionString +=  data.get("key") + " : " + data.get("value") + "\n";
        }

        HsAPI.update_session_name_and_description(sessionID, test_name, descriptionString);
        HsAPI.addSessionTags(sessionID,tags);

    }

    public static Map<String, Object> getGeneralSessionData() {
        String session_status=" ";
        List<Map<String, Object>> data = new ArrayList<>();
        if (status!="Pass") {
            session_status = "Failed";
            Map<String, Object> failReason = new HashMap<>();
            failReason.put("key", "fail_reason");
            failReason.put("value", status);
            data.add(failReason);
        }
        else
            session_status="Passed";
        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("session_id", sessionID);
        sessionData.put("test_name", test_name);
        sessionData.put("status", session_status);

        Map<String, Object> appInfo = new HashMap<>();
        appInfo.put("key", "app");
        appInfo.put("value",getConfigProperty("AppPackage"));
        data.add(appInfo);

        Map<String, Object> statusInfo = new HashMap<>();
        statusInfo.put("key", "status");
        statusInfo.put("value", session_status);
        data.add(statusInfo);

        List<Map<String, Object>> kpidataList ;
        kpidataList=add_kpi_data_from_labels(sessionData);
        for (Map<String, Object> kpidata : kpidataList)
        {
            data.add(kpidata);
        }
        sessionData.put("data", data);
        System.out.println( sessionData + ":Session Data");
        return sessionData;
    }

    public static List<Map<String, Object>> add_kpi_data_from_labels(Map<String, Object> sessionData) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> data = null;
        for (String label_key : kpi_labels.keySet()) {
            if (kpi_labels.get(label_key) != null && kpi_labels.get(label_key).containsKey("start") && kpi_labels.get(label_key).containsKey("end")) {
                data = new HashMap<>();
                data.put("key", label_key);
                long start_time = Long.parseLong(kpi_labels.get(label_key).get("start"));
                long end_time = Long.parseLong(kpi_labels.get(label_key).get("end"));
                if (start_time != 0 && end_time != 0) {
                    long duration = (end_time - start_time);
                    data.put("value", duration);
                    dataList.add(data);
                }
            }
        }
        return dataList;
    }
}
