package com.example.lmsapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewStudentsActivity extends AppCompatActivity {

    private ListView lvStudents;
    private Button btnUpdateStudent, btnDeleteStudent;
    private DatabaseHelper dbHelper;

    private StudentItem selectedStudent = null;
    private ArrayList<StudentItem> studentList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        dbHelper = new DatabaseHelper(this);
        lvStudents = findViewById(R.id.lvStudents);
        btnUpdateStudent = findViewById(R.id.btnUpdateStudent);
        btnDeleteStudent = findViewById(R.id.btnDeleteStudent);

        loadStudentsDetailed();

        lvStudents.setOnItemClickListener((parent, view, position, id) -> {
            selectedStudent = studentList.get(position);
            Toast.makeText(ViewStudentsActivity.this,
                    "Selected: " + selectedStudent.name + " " + selectedStudent.surname,
                    Toast.LENGTH_SHORT).show();
        });

        btnUpdateStudent.setOnClickListener(v -> {
            if (selectedStudent == null) {
                Toast.makeText(ViewStudentsActivity.this, "Select a student first", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(ViewStudentsActivity.this, UpdateStudentActivity.class);
            intent.putExtra("id", selectedStudent.id);
            intent.putExtra("studentId", selectedStudent.studentId);
            intent.putExtra("name", selectedStudent.name);
            intent.putExtra("surname", selectedStudent.surname);
            intent.putExtra("dob", selectedStudent.dob);
            startActivity(intent);
        });

        btnDeleteStudent.setOnClickListener(v -> {
            if (selectedStudent == null) {
                Toast.makeText(ViewStudentsActivity.this, "Select a student first", Toast.LENGTH_SHORT).show();
                return;
            }
            new AlertDialog.Builder(ViewStudentsActivity.this)
                    .setTitle("Delete Student")
                    .setMessage("Are you sure you want to delete " + selectedStudent.name + " " + selectedStudent.surname + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        boolean deleted = dbHelper.deleteStudent(selectedStudent.id);
                        if (deleted) {
                            Toast.makeText(ViewStudentsActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                            selectedStudent = null;
                            loadStudentsDetailed();
                        } else {
                            Toast.makeText(ViewStudentsActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    private void loadStudentsDetailed() {
        studentList = dbHelper.getAllStudentsDetailed(); // returns ArrayList<StudentItem>
        if (studentList.isEmpty()) {
            Toast.makeText(this, "No students found", Toast.LENGTH_SHORT).show();
            lvStudents.setAdapter(null);
            return;
        }

        // Create a simple list of formatted strings for display
        ArrayList<String> displayList = new ArrayList<>();
        for (StudentItem s : studentList) {
            displayList.add(s.studentId + " - " + s.name + " " + s.surname + " (DOB: " + s.dob + ")");
        }

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                displayList);
        lvStudents.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh list in case data changed in update activity
        loadStudentsDetailed();
        selectedStudent = null;
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    // Inner class to hold student details
    public static class StudentItem {
        public int id;
        public String studentId;
        public String name;
        public String surname;
        public String dob;

        public StudentItem(int id, String studentId, String name, String surname, String dob) {
            this.id = id;
            this.studentId = studentId;
            this.name = name;
            this.surname = surname;
            this.dob = dob;
        }
    }
}