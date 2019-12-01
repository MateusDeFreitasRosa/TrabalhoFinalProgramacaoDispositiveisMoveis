package com.example.stack10.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stack10.control.Base64Custom;
import com.example.stack10.control.ConfiguracaoFirebase;
import com.example.stack10.R;
import com.example.stack10.control.Preferencias;
import com.example.stack10.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class cadastro extends AppCompatActivity {
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerMensagem;
    private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);


        firebase = ConfiguracaoFirebase.getFirebase()
                .child("User");

        // Cria listener para mensagens
        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Recupera mensagens
                for ( DataSnapshot dados: dataSnapshot.getChildren() ){
                    Toast.makeText(getApplicationContext(),"Oie: "+ dados, Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

    }

    public void registerUser(View view) {

        String name = ( (EditText) findViewById(R.id.nameUser)).getText().toString();
        String email = ((EditText) findViewById(R.id.emailUser)).getText().toString();
        String confirmEmail = ((EditText) findViewById(R.id.confirmEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String confirmPassword = ((EditText) findViewById(R.id.confirmPassword)).getText().toString();


        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        int resp = confirmacoes(user, confirmPassword, confirmEmail);

        if (resp == -2 ){
            Toast.makeText(cadastro.this, "As senhas não batem", Toast.LENGTH_LONG).show();
        }
        else if (resp == -1) {
            Toast.makeText(cadastro.this, "Os email's não batem", Toast.LENGTH_LONG).show();
        }
        else if (resp == -3) {
            Toast.makeText(cadastro.this, "Há campos em branco\nPreencha-os para proceguir", Toast.LENGTH_LONG).show();
        }
        else {
            cadastrar(user);
        }
    }

    public int confirmacoes(User user, String confirmS, String confirmE) {
        if (!(user.getEmail().equals(confirmE))) {
            return -1;
        }
        else if (!(user.getPassword().equals(confirmS))) {
            return -2;
        }
        else if (user.getName().isEmpty() || user.getEmail().isEmpty() ||
                user.getPassword().isEmpty() || confirmE.isEmpty() || confirmS.isEmpty()) {
            return -3;
        }
        return 1;
    }

    public void cadastrar(final User user) {

        Toast.makeText(cadastro.this, "Entrou, pelomenosss!!!", Toast.LENGTH_LONG).show();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(cadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    String identificadorUsuario = Base64Custom.codificarBase64( user.getEmail() );
                    user.setId( identificadorUsuario );
                    user.salvar();

                    Toast.makeText(cadastro.this, "Sucesso ao cadastrar!!", Toast.LENGTH_LONG).show();
                    nextPage();

                    Preferencias pref = new Preferencias(cadastro.this);
                    pref.salvarDados(identificadorUsuario);
                }
                else{

                    String erro = "";
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Escolha uma senha que contenha, letras e números.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "Email indicado não é válido.";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Já existe uma conta com esse e-mail.";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(cadastro.this, "Erro ao cadastrar usuário: " + erro, Toast.LENGTH_LONG ).show();
                }
            }
        });
    }


    public void clearFields(View view) {
        ( (EditText) findViewById(R.id.nameUser)).setText("");
        ((EditText) findViewById(R.id.emailUser)).setText("");
        ((EditText) findViewById(R.id.confirmEmail)).setText("");
        ((EditText) findViewById(R.id.password)).setText("");
        ((EditText) findViewById(R.id.confirmPassword)).setText("");
    }

    public void nextPage() {
        Intent intent = new Intent(this, Timeline.class);
        startActivity(intent);
    }

    public void visibilityLayout(int b) {
        findViewById(R.id.constraintLayout3).setVisibility(b);
    }

}
