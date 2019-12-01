package com.example.stack10.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stack10.GetDataFromFireBase;
import com.example.stack10.control.Base64Custom;
import com.example.stack10.control.ConfiguracaoFirebase;
import com.example.stack10.R;
import com.example.stack10.control.Preferencias;
import com.example.stack10.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
    }

    public void logar(View view) {

        String email = ((EditText) findViewById(R.id.in_email)).getText().toString();
        String pass = ((EditText) findViewById(R.id.in_senha)).getText().toString();

        if (!(email.isEmpty()) && !(pass.isEmpty())) {
            final User user = new User();
            user.setEmail(email);
            user.setPassword(pass);


            Preferencias pref = new Preferencias(login.this);

            final String uid = Base64Custom.codificarBase64( user.getEmail() );

            pref.salvarDados(uid);


            FirebaseAuth aut = ConfiguracaoFirebase.getFirebaseAutenticacao();
            aut.signInWithEmailAndPassword(user.getEmail(),
                    user.getPassword()
            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        GetDataFromFireBase data = new GetDataFromFireBase();
                        Toast.makeText(getApplicationContext(), "Seja bem vindo: "+ data.get(uid), Toast.LENGTH_LONG).show();
                        nextPag();
                    } else {
                        Toast.makeText(getApplicationContext(), "Requisição recusada!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }



    public void cadastrarUsuario(View view) {
        Intent intent = new Intent(this, cadastro.class);
        startActivity(intent);
    }

    public void nextPag() {
        Intent intent = new Intent(this, Timeline.class);
        startActivity(intent);
    }
}
