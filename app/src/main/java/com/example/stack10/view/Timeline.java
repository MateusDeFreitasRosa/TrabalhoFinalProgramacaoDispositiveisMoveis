package com.example.stack10.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stack10.R;
import com.example.stack10.control.Base64Custom;
import com.example.stack10.control.ConfiguracaoFirebase;
import com.example.stack10.model.Post;
import com.example.stack10.model.PostAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Timeline extends AppCompatActivity {

    private ArrayList<Post> posts;
    private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        posts = new ArrayList<>();

        final ListView lista = findViewById(R.id.listPosts);
        //arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titlePost);
        adapter = new PostAdapter(this, posts);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post p = posts.get(position);
                String emailUser = Base64Custom.decodificarBase64(p.getIdUser());
                Intent intent = new Intent(Timeline.this, question.class);
                intent.putExtra("nome", p.getNomeUser());
                intent.putExtra("titulo", p.getTitulo());
                intent.putExtra("post", p.getPost());
                intent.putExtra("email", emailUser);
                startActivity(intent);
            }
        });

        getData();
    }

    private void getData() {

        DatabaseReference firebase;
        firebase = FirebaseDatabase.getInstance().getReference().child("post");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnap: dataSnapshot.getChildren()) {

                    Post post = postSnap.getValue(Post.class);
                    if(post != null) {
                        posts.add(post);
                        //arrayAdapter.add("Postado por: "+post.getNomeUser()+"\n"+post.getTitulo());
                       // arrayAdapter.notifyDataSetChanged();
                        adapter.notifyDataSetChanged();
                    }

                    //Toast.makeText(Timeline.this, "Test: "+ post.getTitulo() , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        firebase.addListenerForSingleValueEvent(valueEventListener);

    }

    //Repassar entre paginas.
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
