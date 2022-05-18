package com.example.chat_2022_eleves.object;

public class Conversation {
    String id;
    String active;
    String theme;

    @Override
    public String toString() {
        return "Conversation{" +
                "id='" + id + '\'' +
                ", active='" + active + '\'' +
                ", theme='" + theme + '\'' +
                '}';
    }

    public boolean getActive() {
        return active.equals("1");
    }

    public String getTheme() {
        return theme;
    }

    public String getId() { return id; }
}
