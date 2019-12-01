package com.example.stack10.model;

import android.graphics.Bitmap;

import com.example.stack10.control.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.sql.Blob;

public class User {
    String name;
    String email;
    String password;
    String id;
    boolean hasPhoto = false;
    String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean getHasPhoto() {
        return hasPhoto;
    }

    public void setHasPhoto(boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("User").child(getId()).setValue( this );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }
}

