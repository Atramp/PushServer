package com.teradata.common;

import java.io.*;
import java.util.*;

public class Configuration {
    private static Properties properties = null;

    static {
        load();
    }

    private static void load() {
        properties = new Properties();
        String path = Configuration.class.getClassLoader().getResource("/application.properties").getPath();
        if (path != null && !path.isEmpty())
            try (InputStream is = new FileInputStream(new File(path))) {
                properties.load(new InputStreamReader(is, "UTF-8"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static int getInt(String key) {
        String value = getString(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int getInt(String key, int defaultValue) {
        String value = getString(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static long getLong(String key) {
        try {
            return Long.parseLong(getString(key));
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public static long getLong(String key, Long defaultValue) {
        try {
            return Long.parseLong(getString(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static String getString(String key) {
        if (properties == null)
            return null;
        return properties.getProperty(key);
    }

    public static String getString(String key, String defaultValue) {
        String value = getString(key);
        return value == null ? defaultValue : value;
    }

    public static Map<String, String> get(String startWith) {
        Map map = new HashMap();
        Set<String> propertyNames = properties.stringPropertyNames();
        for (String property : propertyNames) {
            if (property.startsWith(startWith))
                map.put(property, properties.getProperty(property));
        }
        return map;
    }

}

