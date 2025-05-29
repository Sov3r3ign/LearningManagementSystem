package com.example.lmsapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewTasksActivity extends AppCompatActivity {
    private ListView lvTasks;
    private EditText etTaskId;
    private CheckBox cbTaskCompleted;
    private Button btnUpdateTask;
    private DatabaseHelper dbHelper;
    private ArrayList<String> taskList;
    private String selectedTaskId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);

        dbHelper = new DatabaseHelper(this);
        lvTasks = findViewById(R.id.lvTasks);
        etTaskId = findViewById(R.id.etTaskId);
        cbTaskCompleted = findViewById(R.id.cbTaskCompleted);
        btnUpdateTask = findViewById(R.id.btnUpdateTask);

        loadAllTasks();

        // Task selection handler
        lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTask = taskList.get(position);
                // Extract task ID from the string (format: "ID: T001\nName:...")
                selectedTaskId = selectedTask.split("\n")[0].replace("ID: ", "").trim();
                etTaskId.setText(selectedTaskId);

                // Extract completion status
                boolean isCompleted = selectedTask.contains("Status: Completed");
                cbTaskCompleted.setChecked(isCompleted);
            }
        });

        // Task update handler
        btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTaskStatus();
            }
        });
    }

    private void loadAllTasks() {
        taskList = dbHelper.getAllTasks();

        if (taskList.isEmpty()) {
            Toast.makeText(this, "No tasks found", Toast.LENGTH_SHORT).show();
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    taskList);
            lvTasks.setAdapter(adapter);
        }
    }

    private void updateTaskStatus() {
        String taskId = etTaskId.getText().toString().trim();
        if (taskId.isEmpty()) {
            taskId = selectedTaskId; // Use the selected task if field is empty
        }

        if (taskId.isEmpty()) {
            Toast.makeText(this, "Please select or enter a task ID", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isCompleted = cbTaskCompleted.isChecked();
        boolean success = dbHelper.updateTaskCompletion(taskId, isCompleted);

        if (success) {
            Toast.makeText(this, "Task status updated", Toast.LENGTH_SHORT).show();
            loadAllTasks(); // Refresh the list
        } else {
            Toast.makeText(this, "Failed to update task", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}