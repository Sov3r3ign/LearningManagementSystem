package com.example.lmsapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.content.DialogInterface;
public class ViewInstructorsActivity extends AppCompatActivity {

    private ListView lvInstructors;
    private Button btnUpdateInstructor, btnDeleteInstructor;
    private DatabaseHelper dbHelper;
    private ArrayList<InstructorItem> instructors;
    private int selectedPosition = -1;

    public static class InstructorItem {
        int id;
        String instructorId;
        String name;

        public InstructorItem(int id, String instructorId, String name) {
            this.id = id;
            this.instructorId = instructorId;
            this.name = name;
        }

        @Override
        public String toString() {
            return instructorId + " - " + name;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_instructors);

        lvInstructors = findViewById(R.id.lvInstructors);
        btnUpdateInstructor = findViewById(R.id.btnUpdateInstructor);
        btnDeleteInstructor = findViewById(R.id.btnDeleteInstructor);
        dbHelper = new DatabaseHelper(this);

        loadInstructors();

        lvInstructors.setOnItemClickListener((parent, view, position, id) -> selectedPosition = position);

        btnUpdateInstructor.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Please select an instructor to update", Toast.LENGTH_SHORT).show();
            } else {
                InstructorItem selected = instructors.get(selectedPosition);
                Intent intent = new Intent(this, UpdateInstructorActivity.class);
                intent.putExtra("id", selected.id);
                intent.putExtra("instructorId", selected.instructorId);
                intent.putExtra("name", selected.name);
                startActivity(intent);
            }
        });

        btnDeleteInstructor.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Please select an instructor to delete", Toast.LENGTH_SHORT).show();
            } else {
                InstructorItem selected = instructors.get(selectedPosition);
                dbHelper.deleteInstructor(selected.id);
                Toast.makeText(this, "Instructor deleted", Toast.LENGTH_SHORT).show();
                loadInstructors();
                selectedPosition = -1;
            }
        });
    }

    private void loadInstructors() {
        instructors = dbHelper.getAllInstructorsDetailed();
        ArrayList<String> list = new ArrayList<>();
        for (InstructorItem i : instructors) {
            list.add(i.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lvInstructors.setAdapter(adapter);
    }
}