package com.example.stack10.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stack10.R;
import com.example.stack10.control.Preferencias;
import com.example.stack10.model.Post;
import com.example.stack10.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class createPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_post);

    }


    public void salvarPost(View view) {
        final String titlePost = ((EditText) findViewById(R.id.titlePost)).getText().toString();
        final String postPost = ((EditText) findViewById(R.id.postPost)).getText().toString();

        if (!titlePost.isEmpty() && !postPost.isEmpty()) {
            Preferencias pref = new Preferencias(createPost.this);
            final String uid = pref.getIdentificador();

            DatabaseReference firebase;
            firebase = FirebaseDatabase.getInstance().getReference().child("User").child(uid);


            //Pegar o nome do usuario atraves do email.

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User u = dataSnapshot.getValue(User.class);

                    Post post = new Post();
                    post.setIdUser(uid);
                    post.setPost(postPost);
                    post.setTitulo(titlePost);
                    post.setNomeUser(u.getName());
                    post.salvar();

                    Toast.makeText(createPost.this, "Publicação realizada com sucesso!!!", Toast.LENGTH_SHORT).show();
                    Intent intent =  new Intent(createPost.this, Timeline.class);
                    startActivity(intent);

                    ((EditText) findViewById(R.id.titlePost)).setText("");
                    ((EditText) findViewById(R.id.postPost)).setText("");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            firebase.addListenerForSingleValueEvent(valueEventListener);

        }
        else {
            Toast.makeText(this, "Escreva nos campos para publicar.", Toast.LENGTH_SHORT).show();
        }
    }


    public void POST(View view) {
        Intent intent = new Intent(this, createPost.class);
        startActivity(intent);
    }

    public void MAIN(View view) {
        Intent intent = new Intent(this, Timeline.class);
        startActivity(intent);
    }

    public void MINE(View view) {
        Intent intent = new Intent(this, myQuestions.class);
        startActivity(intent);
    }

}
