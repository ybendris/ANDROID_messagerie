package com.example.chat_2022_eleves.object;

public class Message {
    private String id;
    private String contenu;
    private String auteur;
    private String couleur;


    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", contenu='" + contenu + '\'' +
                ", auteur='" + auteur + '\'' +
                ", couleur='" + couleur + '\'' +
                '}';
    }

    public String getContenu() {
        return contenu;
    }

    public String getAuteur() {
        return auteur;
    }
}
