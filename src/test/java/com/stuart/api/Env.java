package com.stuart.api;

import java.util.HashMap;
import java.util.Map;

public class Env {

    private static final String API_URI_KEY = "apiUri";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String CLIENT_ID_KEY = "clientId";
    private static final String CLIENT_API_ID_KEY = "clientApiId";
    private static final String CLIENT_API_SECRET_KEY = "clientSecret";

    private static final String API_URI_SANDBOX = "http://sandbox-api.stuart.com";
    private static final String USERNAME_SANDBOX = "r.visa@stuart.com";
    private static final String PASSWORD_SANDBOX = "test1234";
    private static final int CLIENT_ID_SANDBOX = 2642;
    private static final String CLIENT_API_ID_SANDBOX =
            "81_87aa5725abf59ee5b8fbbf84540786c8a5eff6c89475dbd2f7";
    private static final String CLIENT_API_SECRET_SANDBOX =
            "5667150462818adad05dbbc0b41fe512fd1e4b692d1111dffe";

    private static final String API_URI_QA = "http://qa-api.stuart.com";
    private static final String USERNAME_QA = "r.visa@stuart.com";
    private static final String PASSWORD_QA = "test1234";
    private static final int CLIENT_ID_QA = 39888;
    private static final String CLIENT_API_ID_QA =
            "18e8707235651b7648bb48c647976bb922c05411c57f828e4d64164e44e12331";
    private static final String CLIENT_API_SECRET_QA =
            "1c672b969a43f46ab0a2b8b0cfb1128750d863296cfba91fe255c3932b7a4052";

    private static final String API_URI_BETA = "http://beta-api.stuart.com";
    private static final String USERNAME_BETA = "r.visa@stuart.com";
    private static final String PASSWORD_BETA = "test1234";
    private static final int CLIENT_ID_BETA = 2889;
    private static final String CLIENT_API_ID_BETA =
            "25_f683b7fab4e6d46ea2bd10740e3ffcc4160a5e288f10cb3453";
    private static final String CLIENT_API_SECRET_BETA =
            "52a85a8aba752eb0a82ad03a969b373b3a6dc5a86b9bf64054";

    private Map<String, String> env = new HashMap<>();

    public Env() {
        // Run with -Denvironment=beta
        String environmentProperty = System.getProperty("environment");
        // Sandbox by default
        String apiEnvironment = environmentProperty == null ? "qa" : environmentProperty;
        switch (apiEnvironment) {
            case "sandbox":
                env.put(API_URI_KEY, API_URI_SANDBOX);
                env.put(USERNAME_KEY, USERNAME_SANDBOX);
                env.put(PASSWORD_KEY, PASSWORD_SANDBOX);
                env.put(CLIENT_ID_KEY, String.valueOf(CLIENT_ID_SANDBOX));
                env.put(CLIENT_API_ID_KEY, CLIENT_API_ID_SANDBOX);
                env.put(CLIENT_API_SECRET_KEY, CLIENT_API_SECRET_SANDBOX);
                break;
            case "qa":
                env.put(API_URI_KEY, API_URI_QA);
                env.put(USERNAME_KEY, USERNAME_QA);
                env.put(PASSWORD_KEY, PASSWORD_QA);
                env.put(CLIENT_ID_KEY, String.valueOf(CLIENT_ID_QA));
                env.put(CLIENT_API_ID_KEY, CLIENT_API_ID_QA);
                env.put(CLIENT_API_SECRET_KEY, CLIENT_API_SECRET_QA);
                break;
            case "beta":
                env.put(API_URI_KEY, API_URI_BETA);
                env.put(USERNAME_KEY, USERNAME_BETA);
                env.put(PASSWORD_KEY, PASSWORD_BETA);
                env.put(CLIENT_ID_KEY, String.valueOf(CLIENT_ID_BETA));
                env.put(CLIENT_API_ID_KEY, CLIENT_API_ID_BETA);
                env.put(CLIENT_API_SECRET_KEY, CLIENT_API_SECRET_BETA);
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

    public String getClientApiId() {
        return env.get(CLIENT_API_ID_KEY);
    }

    public String getClientApiSecret() {
        return env.get(CLIENT_API_SECRET_KEY);
    }
}
