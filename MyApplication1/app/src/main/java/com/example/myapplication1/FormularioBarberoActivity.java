package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FormularioBarberoActivity extends AppCompatActivity {

    private static final int SELECT_FILE = 1;
    private EditText nombreBarberia;
    private EditText telefono;
    private EditText nombreBarbero;
    private ImageView imgBarberia;

    //Referencia al db
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //Referencia al storage
    FirebaseStorage storage = FirebaseStorage.getInstance();

    private static final String TAG = "prueba";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_barbero);
    }

    public void borrarFormulario(View v) {
        nombreBarberia = findViewById(R.id.textNombreBarberia);
        telefono= findViewById(R.id.textTelefonoBarberia);
        nombreBarbero = findViewById(R.id.textNombreBarbero);

        Bitmap bmp = Bitmap.createBitmap(1,1, Bitmap.Config.ALPHA_8);
        ImageView mImg = (ImageView) findViewById(R.id.imageBarberia);
        mImg.setImageBitmap(bmp);

        nombreBarberia.setText("");
        telefono.setText("");
        nombreBarbero.setText("");
    }

    public void guardarFormulario(View v) {
        nombreBarberia = findViewById(R.id.textNombreBarberia);
        telefono= findViewById(R.id.textTelefonoBarberia);
        nombreBarbero = findViewById(R.id.textNombreBarbero);
        imgBarberia = findViewById(R.id.imageBarberia);

        Map<String, Object> barberia = new HashMap<>();
        barberia.put("nombreBarberia", nombreBarberia.getText().toString());
        barberia.put("telefonoBarberia", Integer.parseInt(telefono.getText().toString()));
        barberia.put("nombreBarbero", nombreBarbero.getText().toString());
        //barberia.put("linkImagen", "http://sdfs.com");

        Bitmap bitmap = ((BitmapDrawable) imgBarberia.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();

        subirImagen(imageInByte,nombreBarberia.getText().toString());

// Add a new document with a generated ID
        db.collection("barberos")
                .add(barberia)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void cargarImagen(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"), SELECT_FILE);
    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImageUri = null;
        Uri selectedImage;

        String filePath = null;
        switch (requestCode) {
            case SELECT_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath=selectedImage.getPath();


                    if (requestCode == SELECT_FILE) {

                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                            Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                            // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                            ImageView mImg = (ImageView) findViewById(R.id.imageBarberia);
                            mImg.setImageBitmap(bmp);

                        }
                    }
                }
                break;
        }
    }

    private void subirImagen(byte [] bArray, String nombreImagen){
        StorageReference storageRef = storage.getReference();
        StorageReference mountainsRef = storageRef.child(nombreImagen);

        UploadTask uploadTask = mountainsRef.putBytes(bArray);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });

    }
}
