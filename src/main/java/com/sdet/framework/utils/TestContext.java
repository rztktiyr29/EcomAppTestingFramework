package com.sdet.framework.utils;

public class TestContext {
    private static double expectedPrice;
    private static String authToken;

    // Getter and Setter for Price
    public static void setExpectedPrice(double price) {
        expectedPrice = price;
    }

    public static double getExpectedPrice() {
        return expectedPrice;
    }

    // Getter and Setter for Auth Token
    public static void setAuthToken(String token) {
        authToken = token;
    }

    public static String getAuthToken() {
        return authToken;
    }
}