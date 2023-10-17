package com.rickross;

import java.util.HashMap;
import java.util.Map;

public class ServerInfo {

    public static String getCertPath() {
        return System.getenv("TEMPORAL_CERT_PATH") != null ? System.getenv("TEMPORAL_CERT_PATH") : "";
    }

    public static String getKeyPath() {
        return System.getenv("TEMPORAL_KEY_PATH") != null ? System.getenv("TEMPORAL_KEY_PATH") : "";
    }

    public static String getNamespace() {
        String namespace = System.getenv("TEMPORAL_NAMESPACE");
        return namespace != null && !namespace.isEmpty() ? namespace : "default";
    }

    public static String getAddress() {
        String address = System.getenv("TEMPORAL_ADDRESS");
        return address != null && !address.isEmpty() ? address : "localhost:7233";
    }

    public static Map<String, String> getServerInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("certPath", getCertPath());
        info.put("keyPath", getKeyPath());
        info.put("namespace", getNamespace());
        info.put("address", getAddress());
        return info;
    }
}

