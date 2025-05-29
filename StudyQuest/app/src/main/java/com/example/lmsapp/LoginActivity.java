package com.example.lmsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText etUserId;
    private Button btnLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        etUserId = findViewById(R.id.etUserId);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = etUserId.getText().toString().trim();

                if (userId.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check role based on ID pattern
                String role = "";
                if (userId.matches("^A\\d+$")) {
                    role = "admin";
                } else if (userId.matches("^I\\d+$")) {
                    role = "instructor";
                } else if (userId.matches("^S\\d+$")) {
                    role = "student";
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid ID format", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if user exists in database
                if (dbHelper.checkUserExists(userId) || role.equals("admin")) {
                    // Redirect based on role
                    Intent intent;
                    switch (role) {
                        case "admin":
                            intent = new Intent(LoginActivity.this, AdminActivity.class);
                            break;
                        case "instructor":
                            intent = new Intent(LoginActivity.this, InstructorActivity.class);
                            break;
                        case "student":
                            intent = new Intent(LoginActivity.this, StudentActivity.class);
                            break;
                        default:
                            return;
                    }
                    intent.putExtra("USER_ID", userId);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}