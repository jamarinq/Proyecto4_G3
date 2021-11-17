package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ServicesActivity extends AppCompatActivity {

    private String usuario;
    private TextView txUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        usuario = getIntent().getStringExtra("usuario");
        txUsuario=findViewById(R.id.textViewUsuario);
        txUsuario.setText("Bienvenido " + usuario);

    }
    public void goToMaps(View v){
        Intent intentAgenda = new Intent(this,AgendaActivity.class);
        startActivity(intentAgenda);
    }
}