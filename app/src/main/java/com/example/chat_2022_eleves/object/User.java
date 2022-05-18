package com.example.chat_2022_eleves.object;

import org.jetbrains.annotations.NotNull;

public class User {

    private String id;
    private String pseudo;
    private String passe;
    private String couleur;



    public User(String id, String pseudo, String passe, String couleur) {
        this.id = id;
        this.pseudo = pseudo;
        this.passe = passe;
        this.couleur = couleur;
    }

    public User(String id, String pseudo, String couleur) {
        this.id = id;
        this.pseudo = pseudo;
        this.couleur = couleur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPasse() {
        return passe;
    }

    public void setPasse(String passe) {
        this.passe = passe;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    @Override
    public @NotNull
    String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", passe='" + passe + '\'' +
                ", couleur='" + couleur + '\'' +
                '}';
    }
}
