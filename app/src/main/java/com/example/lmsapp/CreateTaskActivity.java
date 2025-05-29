package com.example.lmsapp;

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

public class CreateTaskActivity extends AppCompatActivity {
    private EditText etTaskId, etTaskName, etDueDate, etModuleFor;
    private Button btnAddTask;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        dbHelper = new DatabaseHelper(this);

        etTaskId = findViewById(R.id.etTaskId);
        etTaskName = findViewById(R.id.etTaskName);
        etDueDate = findViewById(R.id.etDueDate);
        etModuleFor = findViewById(R.id.etModuleFor);
        btnAddTask = findViewById(R.id.btnAddTask);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskId = etTaskId.getText().toString().trim();
                String taskName = etTaskName.getText().toString().trim();
                String dueDate = etDueDate.getText().toString().trim();
                String moduleFor = etModuleFor.getText().toString().trim();

                if (taskId.isEmpty() || taskName.isEmpty() || dueDate.isEmpty() || moduleFor.isEmpty()) {
                    Toast.makeText(CreateTaskActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isAdded = dbHelper.addTask(taskId, taskName, dueDate, moduleFor);
                if (isAdded) {
                    Toast.makeText(CreateTaskActivity.this, "Task created successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(CreateTaskActivity.this, "Failed to create task", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearFields() {
        etTaskId.setText("");
        etTaskName.setText("");
        etDueDate.setText("");
        etModuleFor.setText("");
    }
}