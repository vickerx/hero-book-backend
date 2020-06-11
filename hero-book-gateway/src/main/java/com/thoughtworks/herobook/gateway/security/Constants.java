package com.thoughtworks.herobook.gateway.security;

public class Constants {
    public static final String[] AUTHENTICATED_URLS = {
        "/authservice/user/info"
    };
    public static final String[] NOT_AUTHENTICATED_URLS = {
        "/authservice/user/registration"
    };
}
