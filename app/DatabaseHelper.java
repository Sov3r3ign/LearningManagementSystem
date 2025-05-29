import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LMS.db";
    private static final int DATABASE_VERSION = 1;

    // Tables
    private static final String TABLE_USERS = "users";
    private static final String TABLE_MODULES = "modules";
    private static final String TABLE_STUDENTS = "students";
    private static final String TABLE_INSTRUCTORS = "instructors";
    private static final String TABLE_TASKS = "tasks";

    // Common columns
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

    // Add user methods
    public boolean addUser(String userId, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_ROLE, role);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // Add other CRUD methods for all tables...
    // Similar methods for modules, students, instructors, tasks
    // Example for adding a student:
    public boolean addStudent(String studentId, String name, String surname, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, studentId);
        values.put(COLUMN_STUDENT_NAME, name);
        values.put(COLUMN_STUDENT_SURNAME, surname);
        values.put(COLUMN_DOB, dob);

        long result = db.insert(TABLE_STUDENTS, null, values);
        return result != -1;
    }

    // Add similar methods for other operations (update, delete, get)
}