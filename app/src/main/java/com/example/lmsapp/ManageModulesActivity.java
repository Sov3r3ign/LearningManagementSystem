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

public class ManageModulesActivity extends AppCompatActivity {
    private EditText etModuleId, etModuleName, etDuration;
    private Button btnAddModule, btnViewModules;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_modules);

        dbHelper = new DatabaseHelper(this);

        etModuleId = findViewById(R.id.etModuleId);
        etModuleName = findViewById(R.id.etModuleName);
        etDuration = findViewById(R.id.etDuration);
        btnAddModule = findViewById(R.id.btnAddModule);
        btnViewModules = findViewById(R.id.btnViewModules);

        btnAddModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moduleId = etModuleId.getText().toString().trim();
                String moduleName = etModuleName.getText().toString().trim();
                String duration = etDuration.getText().toString().trim();

                if (moduleId.isEmpty() || moduleName.isEmpty() || duration.isEmpty()) {
                    Toast.makeText(ManageModulesActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isAdded = dbHelper.addModule(moduleId, moduleName, duration);
                if (isAdded) {
                    Toast.makeText(ManageModulesActivity.this, "Module added successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(ManageModulesActivity.this, "Failed to add module", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnViewModules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start activity to view modules list
                startActivity(new Intent(ManageModulesActivity.this, ViewModulesActivity.class));
            }
        });
    }

    private void clearFields() {
        etModuleId.setText("");
        etModuleName.setText("");
        etDuration.setText("");
    }
}