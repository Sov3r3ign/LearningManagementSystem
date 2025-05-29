package com.example.lmsapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateStudentActivity extends AppCompatActivity {

    private EditText etStudentId, etName, etSurname, etDob;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private int studentDbId; // DB primary key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        etStudentId = findViewById(R.id.etStudentId);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etDob = findViewById(R.id.etDob);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new DatabaseHelper(this);

        // Get data from Intent
        Intent intent = getIntent();
        studentDbId = intent.getIntExtra("id", -1);
        String studentId = intent.getStringExtra("studentId");
        String name = intent.getStringExtra("name");
        String surname = intent.getStringExtra("surname");
        String dob = intent.getStringExtra("dob");

        // Set values in EditTexts
        etStudentId.setText(studentId);
        etName.setText(name);
        etSurname.setText(surname);
        etDob.setText(dob);

        btnSave.setOnClickListener(v -> {
            String newStudentId = etStudentId.getText().toString().trim();
            String newName = etName.getText().toString().trim();
            String newSurname = etSurname.getText().toString().trim();
            String newDob = etDob.getText().toString().trim();

            if(newStudentId.isEmpty() || newName.isEmpty() || newSurname.isEmpty() || newDob.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean updated = dbHelper.updateStudent(studentDbId, newStudentId, newName, newSurname, newDob);
            if(updated) {
                Toast.makeText(this, "Student updated", Toast.LENGTH_SHORT).show();
                finish(); // close this activity and go back
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}