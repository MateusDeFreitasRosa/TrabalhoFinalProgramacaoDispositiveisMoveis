package com.example.stack10.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stack10.R;

public class question extends AppCompatActivity {


    private String nomeUser;
    private String emailUser;
    private String titlePost;
    private String posPost;
    private String keyPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nomeUser = extras.getString("nome");
            emailUser = extras.getString("email");
            titlePost = extras.getString("titulo");
            posPost = extras.getString("post");
            keyPost = extras.getString("key");
            TextView textNome = findViewById(R.id.usuarioPublicacao);
            TextView textTitulo = findViewById(R.id.tituloPublicacao);
            TextView textPublicacao = findViewById(R.id.publicacao);
            Toast.makeText(this, "key: "+keyPost, Toast.LENGTH_LONG).show();

            textNome.setText("Postado por: "+nomeUser);
            textTitulo.setText(titlePost);
            textPublicacao.setText(posPost);

        }
    }
}
