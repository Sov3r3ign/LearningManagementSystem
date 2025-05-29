package com.example.lmsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LMS.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_MODULES = "modules";
    private static final String TABLE_STUDENTS = "students";
    private static final String TABLE_INSTRUCTORS = "instructors";
    private static final String TABLE_TASKS = "tasks";

    // Common column
    private static final String COLUMN_ID = "id";

    // Users table columns
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_ROLE = "role";

    // Modules table columns
    private static final String COLUMN_MODULE_ID = "module_id";
    private static final String COLUMN_MODULE_NAME = "module_name";
    private static final String COLUMN_DURATION = "duration";

    // Students table columns
    private static final String COLUMN_STUDENT_ID = "student_id";
    private static final String COLUMN_STUDENT_NAME = "student_name";
    private static final String COLUMN_STUDENT_SURNAME = "student_surname";
    private static final String COLUMN_DOB = "dob";

    // Instructors table columns
    private static final String COLUMN_INSTRUCTOR_ID = "instructor_id";
    private static final String COLUMN_INSTRUCTOR_NAME = "instructor_name";

    // Tasks table columns
    private static final String COLUMN_TASK_ID = "task_id";
    private static final String COLUMN_TASK_NAME = "task_name";
    private static final String COLUMN_DUE_DATE = "due_date";
    private static final String COLUMN_MODULE_FOR = "module_for";
    private static final String COLUMN_COMPLETED = "completed";

    // Create tables SQL
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_ID + " TEXT UNIQUE,"
            + COLUMN_ROLE + " TEXT"
            + ")";

    private static final String CREATE_MODULES_TABLE = "CREATE TABLE " + TABLE_MODULES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_MODULE_ID + " TEXT UNIQUE,"
            + COLUMN_MODULE_NAME + " TEXT,"
            + COLUMN_DURATION + " TEXT"
            + ")";

    private static final String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_STUDENT_ID + " TEXT UNIQUE,"
            + COLUMN_STUDENT_NAME + " TEXT,"
            + COLUMN_STUDENT_SURNAME + " TEXT,"
            + COLUMN_DOB + " TEXT"
            + ")";

    private static final String CREATE_INSTRUCTORS_TABLE = "CREATE TABLE " + TABLE_INSTRUCTORS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_INSTRUCTOR_ID + " TEXT UNIQUE,"
            + COLUMN_INSTRUCTOR_NAME + " TEXT"
            + ")";

    private static final String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TASK_ID + " TEXT UNIQUE,"
            + COLUMN_TASK_NAME + " TEXT,"
            + COLUMN_DUE_DATE + " TEXT,"
            + COLUMN_MODULE_FOR + " TEXT,"
            + COLUMN_COMPLETED + " INTEGER DEFAULT 0"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_MODULES_TABLE);
        db.execSQL(CREATE_STUDENTS_TABLE);
        db.execSQL(CREATE_INSTRUCTORS_TABLE);
        db.execSQL(CREATE_TASKS_TABLE);

        // Add default admin user
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, "A100000001");
        values.put(COLUMN_ROLE, "admin");
        db.insert(TABLE_USERS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODULES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // User methods
    public boolean addUser(String userId, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_ROLE, role);

        try {
            long result = db.insert(TABLE_USERS, null, values);
            return result != -1;
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error adding user", e);
            return false;
        }
    }

    public boolean checkUserExists(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_USER_ID},
                COLUMN_USER_ID + "=?",
                new String[]{userId},
                null, null, null);

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Student methods
    public boolean addStudent(String studentId, String name, String surname, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, studentId);
        values.put(COLUMN_STUDENT_NAME, name);
        values.put(COLUMN_STUDENT_SURNAME, surname);
        values.put(COLUMN_DOB, dob);

        try {
            long result = db.insert(TABLE_STUDENTS, null, values);
            return result != -1;
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error adding student", e);
            return false;
        }
    }

    public ArrayList<String> getAllStudents() {
        ArrayList<String> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENTS, null);

        if (cursor.moveToFirst()) {
            do {
                String student = "ID: " + cursor.getString(1) +
                        "\nName: " + cursor.getString(2) +
                        "\nSurname: " + cursor.getString(3) +
                        "\nDOB: " + cursor.getString(4);
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return studentList;
    }

    // Instructor methods
    public boolean addInstructor(String instructorId, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_INSTRUCTOR_ID, instructorId);
        values.put(COLUMN_INSTRUCTOR_NAME, name);

        try {
            long result = db.insert(TABLE_INSTRUCTORS, null, values);
            return result != -1;
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error adding instructor", e);
            return false;
        }
    }
    public boolean updateInstructor(int id, String newInstructorId, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_INSTRUCTOR_ID, newInstructorId);
        values.put(COLUMN_INSTRUCTOR_NAME, newName);
        int result = db.update(TABLE_INSTRUCTORS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Delete Instructor
    public boolean deleteInstructor(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_INSTRUCTORS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public ArrayList<String> getAllInstructors() {
        ArrayList<String> instructors = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT instructor_id, name FROM instructors", null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                instructors.add(id + " - " + name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return instructors;
    }
    public ArrayList<ViewInstructorsActivity.InstructorItem> getAllInstructorsDetailed() {
        ArrayList<ViewInstructorsActivity.InstructorItem> instructors = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + ", " + COLUMN_INSTRUCTOR_ID + ", " + COLUMN_INSTRUCTOR_NAME + " FROM " + TABLE_INSTRUCTORS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String instructorId = cursor.getString(1);
                String name = cursor.getString(2);
                instructors.add(new ViewInstructorsActivity.InstructorItem(id, instructorId, name));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return instructors;
    }


    // Module methods
    public boolean addModule(String moduleId, String moduleName, String duration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MODULE_ID, moduleId);
        values.put(COLUMN_MODULE_NAME, moduleName);
        values.put(COLUMN_DURATION, duration);

        try {
            long result = db.insert(TABLE_MODULES, null, values);
            return result != -1;
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error adding module", e);
            return false;
        }
    }

    public ArrayList<String> getAllModules() {
        ArrayList<String> moduleList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MODULES, null);

        if (cursor.moveToFirst()) {
            do {
                String module = "ID: " + cursor.getString(1) +
                        "\nName: " + cursor.getString(2) +
                        "\nDuration: " + cursor.getString(3) + " weeks";
                moduleList.add(module);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return moduleList;
    }
    // Module item fetch
    // Get modules
    public ArrayList<ViewModulesActivity.ModuleItem> getAllModulesDetailed() {
        ArrayList<ViewModulesActivity.ModuleItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM modules", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String moduleId = cursor.getString(cursor.getColumnIndexOrThrow("module_id"));
            String moduleName = cursor.getString(cursor.getColumnIndexOrThrow("module_name"));
            String duration = cursor.getString(cursor.getColumnIndexOrThrow("duration"));
            list.add(new ViewModulesActivity.ModuleItem(id, moduleId, moduleName, duration));
        }
        cursor.close();
        return list;
    }

    // Update module
    public void updateModule(int id, String moduleId, String name, String duration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("module_id", moduleId);
        values.put("module_name", name);
        values.put("duration", duration);
        db.update("modules", values, "id = ?", new String[]{String.valueOf(id)});
    }

    // Delete module
    public void deleteModule(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("modules", "id = ?", new String[]{String.valueOf(id)});
    }



    // Task methods
    public boolean addTask(String taskId, String taskName, String dueDate, String moduleFor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_ID, taskId);
        values.put(COLUMN_TASK_NAME, taskName);
        values.put(COLUMN_DUE_DATE, dueDate);
        values.put(COLUMN_MODULE_FOR, moduleFor);

        try {
            long result = db.insert(TABLE_TASKS, null, values);
            return result != -1;
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error adding task", e);
            return false;
        }
    }

    public boolean updateTaskCompletion(String taskId, boolean isCompleted) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPLETED, isCompleted ? 1 : 0);

        int rowsAffected = db.update(TABLE_TASKS, values,
                COLUMN_TASK_ID + " = ?",
                new String[]{taskId});
        return rowsAffected > 0;
    }

    public ArrayList<String> getAllTasks() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TASKS, null);

        if (cursor.moveToFirst()) {
            do {
                String task = "ID: " + cursor.getString(1) +
                        "\nName: " + cursor.getString(2) +
                        "\nDue: " + cursor.getString(3) +
                        "\nModule: " + cursor.getString(4) +
                        "\nCompleted: " + (cursor.getInt(5) == 1 ? "Yes" : "No");
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return taskList;
    }

    public ArrayList<String> getTasksByModule(String moduleId) {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS,
                new String[]{COLUMN_TASK_ID, COLUMN_TASK_NAME, COLUMN_DUE_DATE, COLUMN_COMPLETED},
                COLUMN_MODULE_FOR + " = ?",
                new String[]{moduleId},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String task = "ID: " + cursor.getString(0) +
                        "\nName: " + cursor.getString(1) +
                        "\nDue: " + cursor.getString(2) +
                        "\nCompleted: " + (cursor.getInt(3) == 1 ? "Yes" : "No");
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return taskList;
    }
    // Update student by internal row id (the primary key _id)


    // Delete student by internal row id
    public boolean deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_STUDENTS,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        return rowsDeleted > 0;
    }
    public ArrayList<ViewStudentsActivity.StudentItem> getAllStudentsDetailed() {
        ArrayList<ViewStudentsActivity.StudentItem> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + ", " + COLUMN_STUDENT_ID + ", " + COLUMN_STUDENT_NAME + ", " + COLUMN_STUDENT_SURNAME + ", " + COLUMN_DOB + " FROM " + TABLE_STUDENTS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String studentId = cursor.getString(1);
                String name = cursor.getString(2);
                String surname = cursor.getString(3);
                String dob = cursor.getString(4);

                students.add(new ViewStudentsActivity.StudentItem(id, studentId, name, surname, dob));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return students;
    }
    // Update student's name by id
    public boolean updateStudent(int id, String studentId, String name, String surname, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STUDENT_ID, studentId);
        cv.put(COLUMN_STUDENT_NAME, name);
        cv.put(COLUMN_STUDENT_SURNAME, surname);
        cv.put(COLUMN_DOB, dob);

        int rows = db.update(TABLE_STUDENTS, cv, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        return rows > 0;
    }


}