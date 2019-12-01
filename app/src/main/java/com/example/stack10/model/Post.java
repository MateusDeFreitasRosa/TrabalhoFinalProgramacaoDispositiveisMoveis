package com.example.stack10.model;

import com.example.stack10.control.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.util.Random;
import java.util.RandomAccess;

public class Post {

    private String titulo;
    private String post;
    private String idUser;
    private String nomeUser;



    public void salvar() {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("post").push().setValue(this);
    }

    public String getNomeUser() {
        return nomeUser;
    }

    public void setNomeUser(String nomeUser) {
        this.nomeUser = nomeUser;
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
