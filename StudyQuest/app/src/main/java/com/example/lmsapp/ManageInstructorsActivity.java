package com.example.lmsapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ManageInstructorsActivity extends AppCompatActivity {
    private EditText etInstructorId, etInstructorName;
    private Button btnAddInstructor, btnViewInstructors;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_instructors);

        dbHelper = new DatabaseHelper(this);

        etInstructorId = findViewById(R.id.etInstructorId);
        etInstructorName = findViewById(R.id.etInstructorName);
        btnAddInstructor = findViewById(R.id.btnAddInstructor);
        btnViewInstructors = findViewById(R.id.btnViewInstructors);

        btnAddInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String instructorId = etInstructorId.getText().toString().trim();
                String name = etInstructorName.getText().toString().trim();

                if (instructorId.isEmpty() || name.isEmpty()) {
                    Toast.makeText(ManageInstructorsActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!instructorId.matches("^I\\d+$")) {
                    Toast.makeText(ManageInstructorsActivity.this, "Instructor ID must start with I followed by numbers", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isAdded = dbHelper.addInstructor(instructorId, name);
                if (isAdded) {
                    Toast.makeText(ManageInstructorsActivity.this, "Instructor added successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                    // Also add to users table for login
                    dbHelper.addUser(instructorId, "instructor");
                } else {
                    Toast.makeText(ManageInstructorsActivity.this, "Failed to add instructor", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnViewInstructors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageInstructorsActivity.this, ViewInstructorsActivity.class));
            }
        });

    }


    private void clearFields() {
        etInstructorId.setText("");
        etInstructorName.setText("");
    }
}