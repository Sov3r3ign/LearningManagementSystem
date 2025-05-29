package com.example.lmsapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateInstructorActivity extends AppCompatActivity {

    private EditText etUpdateInstructorId, etUpdateInstructorName;
    private Button btnSaveUpdate;
    private DatabaseHelper dbHelper;
    private int instructorDbId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_instructor);

        etUpdateInstructorId = findViewById(R.id.etUpdateInstructorId);
        etUpdateInstructorName = findViewById(R.id.etUpdateInstructorName);
        btnSaveUpdate = findViewById(R.id.btnSaveUpdateInstructor);

        dbHelper = new DatabaseHelper(this);

        instructorDbId = getIntent().getIntExtra("id", -1);
        String instructorId = getIntent().getStringExtra("instructorId");
        String name = getIntent().getStringExtra("name");

        etUpdateInstructorId.setText(instructorId);
        etUpdateInstructorName.setText(name);

        btnSaveUpdate.setOnClickListener(v -> {
            String newInstructorId = etUpdateInstructorId.getText().toString().trim();
            String newName = etUpdateInstructorName.getText().toString().trim();

            if (newInstructorId.isEmpty() || newName.isEmpty()) {
                Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.updateInstructor(instructorDbId, newInstructorId, newName);
                Toast.makeText(this, "Instructor updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
