package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToLogin(View view){
        Intent intentLogin = new Intent (this, LoginActivity.class);
        startActivity(intentLogin);
    }
    public void goToPrueba(View view){
        Intent intentLogin = new Intent (this, FormularioBarberoActivity.class);
        startActivity(intentLogin);
    }
}