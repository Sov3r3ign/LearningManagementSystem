package com.example.lmsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmsapp.DatabaseHelper;
import com.example.lmsapp.R;
import com.example.lmsapp.ViewStudentTasksActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class StudentActivity extends AppCompatActivity {
    private Button btnViewMyTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        btnViewMyTasks = findViewById(R.id.btnViewMyTasks);

        btnViewMyTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, ViewTasksActivity.class);
                intent.putExtra("STUDENT_ID", getIntent().getStringExtra("USER_ID"));
                startActivity(intent);
            }
        });
    }
}