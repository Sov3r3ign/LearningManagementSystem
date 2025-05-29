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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewModulesActivity extends AppCompatActivity {

    private ListView lvModules;
    private Button btnUpdateModule, btnDeleteModule;
    private DatabaseHelper dbHelper;
    private ArrayList<ModuleItem> modules;
    private int selectedPosition = -1;

    public static class ModuleItem {
        int id;
        String moduleId;
        String moduleName;
        String duration;

        public ModuleItem(int id, String moduleId, String moduleName, String duration) {
            this.id = id;
            this.moduleId = moduleId;
            this.moduleName = moduleName;
            this.duration = duration;
        }

        @Override
        public String toString() {
            return moduleId + " - " + moduleName + " (" + duration + ")";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_modules);

        lvModules = findViewById(R.id.lvModules);
        btnUpdateModule = findViewById(R.id.btnUpdateModule);
        btnDeleteModule = findViewById(R.id.btnDeleteModule);
        dbHelper = new DatabaseHelper(this);

        loadModules();

        lvModules.setOnItemClickListener((parent, view, position, id) -> selectedPosition = position);

        btnUpdateModule.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Please select a module to update", Toast.LENGTH_SHORT).show();
            } else {
                ModuleItem selected = modules.get(selectedPosition);
                Intent intent = new Intent(this, UpdateModuleActivity.class);
                intent.putExtra("id", selected.id);
                intent.putExtra("moduleId", selected.moduleId);
                intent.putExtra("moduleName", selected.moduleName);
                intent.putExtra("duration", selected.duration);
                startActivity(intent);
            }
        });

        btnDeleteModule.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Please select a module to delete", Toast.LENGTH_SHORT).show();
            } else {
                ModuleItem selected = modules.get(selectedPosition);
                dbHelper.deleteModule(selected.id);
                Toast.makeText(this, "Module deleted", Toast.LENGTH_SHORT).show();
                loadModules();
                selectedPosition = -1;
            }
        });
    }

    private void loadModules() {
        modules = dbHelper.getAllModulesDetailed();
        ArrayList<String> list = new ArrayList<>();
        for (ModuleItem m : modules) {
            list.add(m.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lvModules.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadModules();
    }
}