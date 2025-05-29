package com.example.lmsapp;

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

public class ViewInstructorsActivity extends AppCompatActivity {
    private ListView lvInstructors;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_instructors);

        dbHelper = new DatabaseHelper(this);
        lvInstructors = findViewById(R.id.lvInstructors);
        loadInstructors();
    }

    private void loadInstructors() {
        ArrayList<String> instructors = dbHelper.getAllInstructors();

        if (instructors.isEmpty()) {
            Toast.makeText(this, "No instructors found", Toast.LENGTH_SHORT).show();
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    instructors);
            lvInstructors.setAdapter(adapter);
        }
    }
}