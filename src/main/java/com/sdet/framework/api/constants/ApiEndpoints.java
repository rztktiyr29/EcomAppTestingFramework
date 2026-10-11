package com.sdet.framework.api.constants;

import com.sdet.framework.utils.ConfigReader;

public class ApiEndpoints {

    // Automatically reads base.url from config.properties
    public static final String BASE_URL = ConfigReader.getProperty("base.url") != null
            ? ConfigReader.getProperty("base.url")
            : "https://mern-ecommerce-api-9e9r.onrender.com";

    // Relative endpoint paths
    public static final String UPDATE_PRODUCT = "/api/product/{id}";
    public static final String GET_PRODUCT = "/api/product/{id}";
    public static final String LOGIN = "/api/user/login"; // Added based on your URL
}