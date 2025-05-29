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

public class ViewModulesActivity extends AppCompatActivity {
    private ListView lvModules;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_modules);

        dbHelper = new DatabaseHelper(this);
        lvModules = findViewById(R.id.lvModules);
        loadModules();
    }

    private void loadModules() {
        ArrayList<String> modules = dbHelper.getAllModules();

        if (modules.isEmpty()) {
            Toast.makeText(this, "No modules found", Toast.LENGTH_SHORT).show();
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    modules);
            lvModules.setAdapter(adapter);
        }
    }
}