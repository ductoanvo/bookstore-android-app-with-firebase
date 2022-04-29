package vn.edu.iuh.fit.se.android.dhktpm15ctt.nhom10.toannhat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView txtRegisterLink;
    EditText edtEmail, edtPassword;
    Button btnLogin;
    ImageButton btnLoginGoogle;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mapping();

        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> login());

        txtRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void login() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "All of fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, ProductActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Email or Password is not valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void mapping() {
        txtRegisterLink = findViewById(R.id.txtRegisterLink_LoginScreen);
        edtEmail = findViewById(R.id.edtEmail_LoginScreen);
        edtPassword = findViewById(R.id.edtPassword_LoginScreen);
        btnLogin = findViewById(R.id.btnAddToCart_ProductDetailScreen);
        btnLoginGoogle = findViewById(R.id.btnLoginGoogle_LoginScreen);
    }
}