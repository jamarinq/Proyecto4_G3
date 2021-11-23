package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class FormularioBarberoActivity extends AppCompatActivity {

    private static final int SELECT_FILE = 1;
    private EditText nombreBarberia;
    private EditText telefono;
    private EditText nombreBarbero;

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
}
