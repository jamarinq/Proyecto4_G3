package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private EditText etEmail;
    private EditText etPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

    }

    public void registrar(View view){
        etEmail=findViewById(R.id.emailEditText);
        etPassword=findViewById(R.id.passwordEditText);
        Toast.makeText(getApplicationContext(),
                etEmail.toString(), Toast.LENGTH_SHORT);
        if((etEmail.getText() != null) && etPassword.getText()!= null){
            mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(),
                    etPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Usuario Creado", Toast.LENGTH_SHORT).show();
                        Intent intentLogin = new Intent (getApplicationContext(), FormularioBarberoActivity.class);
                        startActivity(intentLogin);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void acceder(View view){
        etEmail=findViewById(R.id.emailEditText);
        etPassword=findViewById(R.id.passwordEditText);
        Toast.makeText(getApplicationContext(),
                etEmail.toString(), Toast.LENGTH_SHORT);
        if((etEmail.getText() != null) && etPassword.getText()!= null){
            mAuth.signInWithEmailAndPassword(etEmail.getText().toString(),
                    etPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        Toast.makeText(LoginActivity.this, "Acceso Correcto", Toast.LENGTH_SHORT).show();
                        Intent intentServices = new Intent (getApplicationContext(), ServicesActivity.class);
                        intentServices.putExtra("usuario",etEmail.getText().toString());
                        startActivity(intentServices);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}