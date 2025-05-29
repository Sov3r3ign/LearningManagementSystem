package com.example.lmsapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewStudentTasksActivity extends AppCompatActivity {
    private ListView lvTasks;
    private DatabaseHelper dbHelper;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_tasks);

        dbHelper = new DatabaseHelper(this);
        studentId = getIntent().getStringExtra("STUDENT_ID");

        lvTasks = findViewById(R.id.lvTasks);
        loadTasks();
    }

    private void loadTasks() {
        // Get all tasks (in a real app, you might filter by student's modules)
        ArrayList<String> tasks = dbHelper.getAllTasks();

        if (tasks.isEmpty()) {
            Toast.makeText(this, "No tasks found", Toast.LENGTH_SHORT).show();
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    tasks);
            lvTasks.setAdapter(adapter);
        }
    }
}