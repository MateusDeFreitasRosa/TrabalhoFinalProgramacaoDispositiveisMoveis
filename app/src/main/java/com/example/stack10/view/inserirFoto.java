package com.example.stack10.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.stack10.R;
import com.example.stack10.control.Base64Custom;
import com.example.stack10.control.ConfiguracaoFirebase;
import com.example.stack10.control.Preferencias;
import com.example.stack10.model.User;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class inserirFoto extends AppCompatActivity {

    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerMensagem;
    private User userForModified;
    private String uid = "";
    private FirebaseStorage storage;
    private StorageReference storageRef;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserirfoto);

        ImageView btn = findViewById(R.id.btn_selectPhoto);
        int img = R.drawable.ic_launcher_background;
        btn.setBackground(getDrawable(img));

    }

    public void nextPage(View view) {
        Toast.makeText(this, userForModified.getId(), Toast.LENGTH_SHORT).show();
        if (userForModified.getHasPhoto() && uid != "" ) {
            userForModified.salvar();
            Intent intent = new Intent(this, Timeline.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(inserirFoto.this, "Ops!!\nAlgo errado ocorreu!!", Toast.LENGTH_SHORT).show();
        }
        
    }

    public void GalleryAccess(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 12345);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //storageRef = FirebaseStorage.getInstance().getReference().child("foto_usuario");
        if(requestCode == 12345 && resultCode == Activity.RESULT_OK && data != null) {


            Preferencias pref = new Preferencias(inserirFoto.this);
            uid = pref.getIdentificador();

            //makeText(inserirFoto.this, uid , Toast.LENGTH_SHORT).show();

            firebase = ConfiguracaoFirebase.getFirebase()
                    .child("User")
                    .child( uid );
            final User user;
            ValueEventListener valueEventListenerUsuario = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final User usuarioRecuperado = dataSnapshot.getValue(User.class);

                    Toast.makeText(inserirFoto.this, "ToAqui", Toast.LENGTH_SHORT).show();
                    if (usuarioRecuperado != null) {
                        // Recuperar local do recurso.
                        Uri localImagemSelecionada = data.getData();
                    /*
                        StorageReference photoRef =  storageRef.child(localImagemSelecionada.getLastPathSegment());

                        photoRef.putFile(localImagemSelecionada).addOnSuccessListener
                                (inserirFoto.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                usuarioRecuperado.setImg(downloadUrl.toString());
                                usuarioRecuperado.salvar();
                            }
                        });
                        */
                        // Recuperar imagem do local que foi selecionada.
                        try {
                            Bitmap imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);

                            float aspectRatio = imagem.getWidth() /
                                    (float) imagem.getHeight();
                            int width = 340;
                            int height = Math.round(width / aspectRatio);

                            //Redimensionar imagem.
                            imagem = Bitmap.createScaledBitmap( imagem, width, height, false );
                            //Toast.makeText(inserirFoto.this, "largura: "+width+"\nAltura: "+height, Toast.LENGTH_LONG).show();

                            // Comprimir no formato PNG.
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            imagem.compress(Bitmap.CompressFormat.PNG, 100, stream);

                            //Transformar stream em um array de bytes.
                            byte[] data = stream.toByteArray();
                            
                            
                            ImageView img = findViewById(R.id.img);
                            img.setImageBitmap(imagem);

                            Button prox = findViewById(R.id.prox);
                            prox.setVisibility(View.VISIBLE);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    //Toast.makeText(inserirFoto.this, "Right: "+usuarioRecuperado.getPassword(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(inserirFoto.this, "Ops", Toast.LENGTH_SHORT).show();
                }
            };
            firebase.addListenerForSingleValueEvent( valueEventListenerUsuario );

        }
        else {
            Toast.makeText(inserirFoto.this, "Algo de errado aconteceu!", Toast.LENGTH_SHORT).show();
        }
    }
}
