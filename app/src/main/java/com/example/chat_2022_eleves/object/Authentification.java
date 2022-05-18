package com.example.chat_2022_eleves.object;

import java.util.ArrayList;

public class Authentification {
    String version;
    String success;
    String status;
    String hash;

    @Override
    public String toString() {
        return "Authentification{" +
                "version='" + version + '\'' +
                ", success='" + success + '\'' +
                ", status='" + status + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }

    public String getHash() {
        return hash;
    }
}
