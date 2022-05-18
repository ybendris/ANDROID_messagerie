package com.example.chat_2022_eleves.object;

import java.util.ArrayList;

public class ListConversations {
    String version;
    String success;
    String status;
    ArrayList<Conversation> conversations;

    @Override
    public String toString() {
        return "ListConversations{" +
                "version='" + version + '\'' +
                ", success='" + success + '\'' +
                ", status='" + status + '\'' +
                ", conversations=" + conversations +
                '}';
    }


    public ArrayList<Conversation> getConversations() {
        return conversations;
    }
}
