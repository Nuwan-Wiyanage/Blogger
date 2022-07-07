package com.villvay.blogger.config;

public class SecurityConstants {
    public static final String SECRET = "VILLVAY_ASSIGNMENT";
    public static final long EXPIRATION_TIME = 900_000; // 15 mins
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/auth/signup";
    public static final String SIGN_IN_URL = "/api/auth/signin";
}
