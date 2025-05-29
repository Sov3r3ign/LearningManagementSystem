package com.example.lmsapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class UpdateModuleActivity extends AppCompatActivity {

    private EditText etUpdateModuleId, etUpdateModuleName, etUpdateDuration;
    private Button btnUpdate;
    private DatabaseHelper dbHelper;
    private int moduleDbId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_module);

        etUpdateModuleId = findViewById(R.id.etUpdateModuleId);
        etUpdateModuleName = findViewById(R.id.etUpdateModuleName);
        etUpdateDuration = findViewById(R.id.etUpdateDuration);
        btnUpdate = findViewById(R.id.btnUpdate);

        dbHelper = new DatabaseHelper(this);

        moduleDbId = getIntent().getIntExtra("id", -1);
        etUpdateModuleId.setText(getIntent().getStringExtra("moduleId"));
        etUpdateModuleName.setText(getIntent().getStringExtra("moduleName"));
        etUpdateDuration.setText(getIntent().getStringExtra("duration"));

        btnUpdate.setOnClickListener(v -> {
            String newId = etUpdateModuleId.getText().toString().trim();
            String newName = etUpdateModuleName.getText().toString().trim();
            String newDuration = etUpdateDuration.getText().toString().trim();

            if (newId.isEmpty() || newName.isEmpty() || newDuration.isEmpty()) {
                Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.updateModule(moduleDbId, newId, newName, newDuration);
                Toast.makeText(this, "Module updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}