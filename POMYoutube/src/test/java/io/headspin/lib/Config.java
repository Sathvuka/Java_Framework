package io.headspin.lib;

import io.appium.java_client.AppiumDriver;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Config {

    public static Map<String, Map<String,String>> kpi_labels = new HashMap<>();
    public static Map<String, String> time_stamps1=new HashMap<>();
    public static Map<String, String> time_stamps2=new HashMap<>();
    public static Map<String, String> time_stamps3=new HashMap<>();
    public static Map<String, String> time_stamps4=new HashMap<>();
    public static String KPI_LABEL_CATEGORY="Youtube Kpi";

    public static String status;

    public static List<String> tags = new ArrayList<>();
    public static String sessionID;
    public static URL deviceurl;




}
