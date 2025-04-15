package com.jn.airlinemobileapp.activity;

public final class UrlCollection {

    // Base URL for the API
    private static final String BASE_URL = "http://192.168.1.68:8080/api/v1/";

    // API endpoints
    public static final String REGISTER_URL = BASE_URL + "user/add";
    public static final String LOGIN_URL = BASE_URL + "auth/login";

    // Private constructor to prevent instantiation
    private UrlCollection() {
        throw new UnsupportedOperationException("Cannot instantiate utility class.");
    }
}