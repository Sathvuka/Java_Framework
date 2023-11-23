package io.headspin.lib;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.headspin.lib.Config.deviceurl;
import static io.headspin.utils.ConfigTest.Appium_Driver;


public class HsAPI {
    Map<String, String> tags = new HashMap<>();
    static String apiKey =  get_access_token();
    static String url_root = "https://api-dev.headspin.io/v0";
    public HsAPI() throws IOException {
        //get hostname,device_sku and os from the desired device
        String url = "https://api-dev.headspin.io/v0/devices";
        URL deviceurl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) deviceurl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append("  ").append(line).append(System.lineSeparator());
            }
            reader.close();
            String r1 = response.toString();
            String desiredDeviceId = Appium_Driver.getCapabilities().getCapability("udid").toString();
            try {
                JSONObject jsonObject = new JSONObject(r1);
                JSONArray devicesArray = jsonObject.getJSONArray("devices");
                for (int i = 0; i < devicesArray.length(); i++) {
                    JSONObject device = devicesArray.getJSONObject(i);
                    String deviceId = device.getString("device_id");
                    if (desiredDeviceId.equals(desiredDeviceId)) {

                        tags.put("hostname", device.getString("hostname"));
                        tags.put("device_sku",  device.getJSONArray("device_skus").getString(0));
                        tags.put("OS", device.getString("device_type"));
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
          connection.disconnect();
        }
    }
    //
    public static String get_capture_timestamp(String sessionID) throws IOException {
        String request_url = url_root + "/sessions/" + sessionID + "/timestamps";
        StringBuilder response = new StringBuilder();

        URL requesturl = new URL(request_url);
        HttpURLConnection connection = (HttpURLConnection) requesturl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append("  ").append(line).append(System.lineSeparator());
            }
            System.out.println(response);
            reader.close();

        }
        else{
                System.err.println("HTTP Request failed with response code: " + responseCode);
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String line1;
                while ((line1 = br.readLine()) != null) {
                    System.out.println(line1);
                }
            return null;
        }
        connection.disconnect();
        return response.toString();
    }

    public static String get_session_video_metadata(String sessionId) throws IOException {
        String request_url = "https://api-dev.headspin.io/v0/sessions/"+ sessionId +"/video/metadata";
        StringBuilder response = new StringBuilder();
        URL requesturl = new URL(request_url);
        HttpURLConnection connection = (HttpURLConnection) requesturl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append("  ").append(line).append(System.lineSeparator());
            }
            reader.close();
        }
        else{
            System.err.println("Failed to get session video metadata");
        }
        connection.disconnect();
        return response.toString();


    }
    public static void add_label(String sessionId, String name, String category, float startTime, float endTime) throws IOException {

        add_label(sessionId, name, category, startTime, endTime, false, "user", null);
    }
    public static void add_label(String sessionId, String name, String category, float start_time, float end_time, boolean pinned, String labelType, String data) throws IOException {
        String request_url = url_root + "/sessions/" +  sessionId + "/label/add";

       // System.out.println(start_time + " start_time"+end_time+"end_time");

        URL url = new URL(request_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Create JSON payload
        String jsonPayload = "{\"name\":\"" + name + "\","
                + "\"category\":\"" + category + "\","
                + "\"start_time\":\"" + start_time + "\","
                + "\"end_time\":\"" + end_time + "\","
                + "\"data\":\"" + data + "\","
                + "\"pinned\":" + pinned + ","
                + "\"label_type\":\"" + labelType + "\"}";

        // Write JSON payload to the request body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
           System.out.println("Labels Sucessfully Added");
        }
        else {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        connection.disconnect();
    }

    public static Map<String, Object> get_pageloadtime(String sessionId, String name, float labelStartTime, float labelEndTime, float startSensitivity, float endSensitivity) {
        try {
            String requestUrl = url_root + "/sessions/analysis/pageloadtime/" + sessionId;
            ObjectMapper objectMapper = new ObjectMapper();

            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            //  JSON payload
            Map<String, Object> dataPayload = new HashMap<>();
            Map<String, String> regionTimes = new HashMap<>();
            System.out.println((labelStartTime / 1000)+"labelStartTime");
            System.out.println((labelEndTime / 1000) +"labelEndTime");

            regionTimes.put("start_time", String.valueOf((labelStartTime / 1000)));
            regionTimes.put("end_time", String.valueOf((labelEndTime / 1000)));
            regionTimes.put("name", name);

            //  list to hold the region object
            List<Map<String,String>> regionsList = new ArrayList<>();
            regionsList.add(regionTimes);

            dataPayload.put("regions",regionsList);

            if (startSensitivity != 0) {
                dataPayload.put("start_sensitivity", startSensitivity);
            }
            if (endSensitivity != 0) {
                dataPayload.put("end_sensitivity", endSensitivity);
            }


            //  JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                objectMapper.writeValue(os, dataPayload);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                Map<String, Object> results = objectMapper.readValue(inputStream, new TypeReference<>() {
                });
                inputStream.close();
                System.out.println("result:"+results);
                return results;
            }
            else{
                System.err.println("HTTP Request failed with response code: " + responseCode);
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
            connection.disconnect();
            return null;
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (StreamWriteException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update_session_name_and_description(String sessionID, String testName, String descriptionString) throws IOException {
        String requestUrl =url_root + "/sessions/" + sessionID + "/description";
         Map<String, Object> dataPayload = new HashMap<>();
         dataPayload.put("name",testName);
         dataPayload.put("description", descriptionString);

         URL url = new URL(requestUrl);
         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("POST");
         connection.setRequestProperty("Authorization", "Bearer " + apiKey);
         connection.setRequestProperty("Content-Type", "application/json");
         connection.setDoOutput(true);

         ObjectMapper objectMapper = new ObjectMapper();                         
         String jsonPayload = objectMapper.writeValueAsString(dataPayload);
          try (OutputStream os = connection.getOutputStream()) {
                     byte[] input = jsonPayload.getBytes("utf-8");
                     os.write(input, 0, input.length);
           }
          int responseCode = connection.getResponseCode();
          if (responseCode == HttpURLConnection.HTTP_OK) {
                 System.out.println("Tags added successfully.");
          }
          else {
                 System.err.println("HTTP Request failed with response code: " + responseCode);
             }
          connection.disconnect();
    }

    public static void addSessionTags(String sessionID, List<String> tags) throws IOException {
        String apiEndpoint = "https://api-dev.headspin.io/v0/sessions/tags/" + sessionID;

        URL url = new URL(apiEndpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(tags);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Tags added successfully.");
        } else {
            System.err.println("HTTP Request failed with response code: " + responseCode);
        }
        connection.disconnect();
    }

    public static void addSessionData(Map<String, Object> sessionData) throws IOException {
           String requestUrl = "https://api-dev.headspin.io/v0/perftests/upload";

           ObjectMapper objectMapper = new ObjectMapper();
           String payload = objectMapper.writeValueAsString(sessionData);

           URL url = new URL(requestUrl);
           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
           connection.setRequestMethod("POST");
           connection.setRequestProperty("Authorization", "Bearer " + apiKey);
           connection.setRequestProperty("Content-Type", "application/json");
           connection.setDoOutput(true);


           try (OutputStream os = connection.getOutputStream()) {
                 byte[] input = payload.getBytes("utf-8");
                  os.write(input, 0, input.length);
            }


           int responseCode = connection.getResponseCode();
           if (responseCode == 200) {
              System.out.println("Session Tags added successfully.");

           } else {
                System.err.println("HTTP Request failed with response code: " + responseCode);
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String line;
                while ((line = br.readLine()) != null) {
                  System.out.println(line);
                }

           }
        connection.disconnect();
    }
    public static String get_access_token(){
        String appium_url= String.valueOf(deviceurl);
        int v0Index = appium_url.indexOf("/v0/");
        if (v0Index != -1) {
            String substring = appium_url.substring(v0Index + 4);
            int endIndex = substring.indexOf("/");
            if (endIndex != -1) {
                return substring.substring(0, endIndex);
            } else {
                return null;
            }
        }
        return null;
    }
    }


