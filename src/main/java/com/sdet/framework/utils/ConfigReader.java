package com.sdet.framework.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            // Tries to load local file if it exists
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            // File won't exist on Jenkins, which is expected! Initialize empty properties.
            properties = new Properties();
        }
    }

    public static String getProperty(String key) {
        // 1. First check if passed via command line/system property (e.g. from Jenkins)
        String value = System.getProperty(key);

        // 2. If not found, fall back to the local config.properties file
        if (value == null || value.isEmpty()) {
            value = properties.getProperty(key);
        }
        return value;
    }
}