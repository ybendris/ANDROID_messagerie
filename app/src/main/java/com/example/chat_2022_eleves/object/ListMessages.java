package com.example.chat_2022_eleves.object;

import java.util.ArrayList;

public class ListMessages {
    String version;
    String success;
    String status;
    ArrayList<Message> messages;

    @Override
    public String toString() {
        return "ListMessages{" +
                "version='" + version + '\'' +
                ", success='" + success + '\'' +
                ", status='" + status + '\'' +
                ", messages=" + messages +
                '}';
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public boolean addMessage(Message messageToAdd){
        return this.messages.add(messageToAdd);
    }
}
