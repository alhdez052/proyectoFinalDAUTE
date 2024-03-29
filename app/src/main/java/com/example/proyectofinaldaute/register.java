package com.example.proyectofinaldaute;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {

    public Button signup, gotoLogin;
    EditText emailInput, passInput;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        emailInput = findViewById(R.id.emailInput);
        passInput = findViewById(R.id.passInput);

        firebaseAuth = FirebaseAuth.getInstance();

        signup = findViewById(R.id.btnRegistrarme);
        gotoLogin = findViewById(R.id.btnCuenta);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });


        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeView();
            }
        });

    }

    public void changeView() {
        Intent intent = new Intent(register.this, Login.class);
        startActivity(intent);
    }

    public void register() {
        
        String email = emailInput.getText().toString().trim();
        String password = passInput.getText().toString();

        boolean val = validarDatosRegistro(email, password);

        if (val) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(register.this, "Tu Registro fue exitoso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(register.this, Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String e = task.getException().toString();
                        Toast.makeText(register.this, e, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public boolean validarDatosRegistro(String email, String password) {
        if (email.length() == 0) {
            emailInput.setError("Ingrese un correo");
            return false;

        } else if( password.length() == 0) {
            passInput.setError("Ingrese su contraseña");
            return false;
        }
        return true;
    }


}