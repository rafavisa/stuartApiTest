package com.stuart.api;

import java.util.HashMap;
import java.util.Map;

public class Env {

    public static final String API_URI_KEY = "apiUri";
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String CLIENT_ID_KEY = "clientId";
    public static final String CLIENT_SECRET_KEY = "clientSecret";
    public static final String API_URI_SANDBOX = "http://sandbox-api.stuart.com";
    public static final String USERNAME_SANDBOX = "r.visa@stuart.com";
    public static final String PASSWORD_SANDBOX = "test1234";
    public static final String CLIENT_ID_SANDBOX =
            "81_87aa5725abf59ee5b8fbbf84540786c8a5eff6c89475dbd2f7";
    public static final String CLIENT_SECRET_SANDBOX =
            "5667150462818adad05dbbc0b41fe512fd1e4b692d1111dffe";
    public static final String API_URI_BETA = "http://beta-api.stuart.com";
    public static final String USERNAME_BETA = "r.visa@stuart.com";
    public static final String PASSWORD_BETA = "test1234";
    public static final String CLIENT_ID_BETA =
            "25_f683b7fab4e6d46ea2bd10740e3ffcc4160a5e288f10cb3453";
    public static final String CLIENT_SECRET_BETA =
            "52a85a8aba752eb0a82ad03a969b373b3a6dc5a86b9bf64054";

    Map<String, String> env = new HashMap<>();

    public Env() {
        // Run with -Denvironment=beta
        String environmentProperty = System.getProperty("environment");
        // Sandbox by default
        String apiEnvironment = environmentProperty == null ? "sandbox" : environmentProperty;
        switch (apiEnvironment) {
            case "sandbox":
                env.put(API_URI_KEY, API_URI_SANDBOX);
                env.put(USERNAME_KEY, USERNAME_SANDBOX);
                env.put(PASSWORD_KEY, PASSWORD_SANDBOX);
                env.put(CLIENT_ID_KEY, CLIENT_ID_SANDBOX);
                env.put(CLIENT_SECRET_KEY, CLIENT_SECRET_SANDBOX);
                break;
            case "beta":
                env.put(API_URI_KEY, API_URI_BETA);
                env.put(USERNAME_KEY, USERNAME_BETA);
                env.put(PASSWORD_KEY, PASSWORD_BETA);
                env.put(CLIENT_ID_KEY, CLIENT_ID_BETA);
                env.put(CLIENT_SECRET_KEY, CLIENT_SECRET_BETA);
                break;
        }
    }

    public String getApiUri() {
        return env.get(API_URI_KEY);
    }

    public String getUsername() {
        return env.get(USERNAME_KEY);
    }

    public String getPassword() {
        return env.get(PASSWORD_KEY);
    }

    public String getClientId() {
        return env.get(CLIENT_ID_KEY);
    }

    public String getClientSecret() {
        return env.get(CLIENT_SECRET_KEY);
    }
}
