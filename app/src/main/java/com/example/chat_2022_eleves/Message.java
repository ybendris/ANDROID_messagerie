package com.example.chat_2022_eleves;

public class Message {
    String id;
    String contenu;
    String auteur;
    String couleur;


    @Override
    public String toString() {
        return "Conversation{" +
                "id='" + id + '\'' +
                ", contenu='" + contenu + '\'' +
                ", auteur='" + auteur + '\'' +
                ", couleur='" + couleur + '\'' +
                '}';
    }


}
