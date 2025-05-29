package com.example.lmsapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.util.Log;
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

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewStudentTasksActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_view_student_tasks);

        dbHelper = new DatabaseHelper(this);
        lvTasks = findViewById(R.id.lvTasks);



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
}