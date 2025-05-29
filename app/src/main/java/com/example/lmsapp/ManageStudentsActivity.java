package com.example.lmsapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ManageStudentsActivity extends AppCompatActivity {
    private EditText etStudentId, etStudentName, etStudentSurname, etDob;
    private Button btnAddStudent, btnViewStudents;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);

        dbHelper = new DatabaseHelper(this);

        etStudentId = findViewById(R.id.etStudentId);
        etStudentName = findViewById(R.id.etStudentName);
        etStudentSurname = findViewById(R.id.etStudentSurname);
        etDob = findViewById(R.id.etDob);
        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnViewStudents = findViewById(R.id.btnViewStudents);

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentId = etStudentId.getText().toString().trim();
                String name = etStudentName.getText().toString().trim();
                String surname = etStudentSurname.getText().toString().trim();
                String dob = etDob.getText().toString().trim();

                if (studentId.isEmpty() || name.isEmpty() || surname.isEmpty() || dob.isEmpty()) {
                    Toast.makeText(ManageStudentsActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!studentId.matches("^S\\d+$")) {
                    Toast.makeText(ManageStudentsActivity.this, "Student ID must start with S followed by numbers", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isAdded = dbHelper.addStudent(studentId, name, surname, dob);
                if (isAdded) {
                    Toast.makeText(ManageStudentsActivity.this, "Student added successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(ManageStudentsActivity.this, "Failed to add student", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnViewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start activity to view students list
                startActivity(new Intent(ManageStudentsActivity.this, ViewStudentsActivity.class));
            }
        });
    }

    private void clearFields() {
        etStudentId.setText("");
        etStudentName.setText("");
        etStudentSurname.setText("");
        etDob.setText("");
    }
}
