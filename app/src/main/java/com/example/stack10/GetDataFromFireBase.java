package com.example.stack10;

import android.widget.Toast;

import com.example.stack10.control.ConfiguracaoFirebase;
import com.example.stack10.model.User;
import com.example.stack10.view.inserirFoto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class GetDataFromFireBase {
    private DatabaseReference firebase;
    public String out;

    public String get(String uid) {
        firebase = ConfiguracaoFirebase.getFirebase()
                .child("User")
                .child(uid);
        out = "";
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User newUser = dataSnapshot.getValue(User.class);
                out = newUser.getName();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                out = "The read failed: " + databaseError.getCode();
            }
        });

        return out;
    }
}
