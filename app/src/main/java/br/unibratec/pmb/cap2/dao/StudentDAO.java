package br.unibratec.pmb.cap2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.unibratec.pmb.cap2.model.Student;

/**
 * Created by thiago.sousa on 09/02/2017.
 */

public class StudentDAO extends SQLiteOpenHelper{
    public StudentDAO(Context context) {
        super(context, "cap2", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Students( id INTEGER PRIMARY KEY, name TEXT NOT NULL, phone TEXT, address TEXT, email TEXT, age INTEGER, rate REAL, photo TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Students";
        db.execSQL(sql);
        onCreate(db);
    }


    public void save(Student student) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = getStudentData(student);

        db.insert("Students", null, data);

    }

    public List<Student> getStudents() {
        String sql = "SELECT * FROM Students";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Student> students = new ArrayList<Student>();
        while (c.moveToNext()){
            Student student = new Student();
            student.setId(c.getLong(c.getColumnIndex("id")));
            student.setName(c.getString(c.getColumnIndex("name")));
            student.setPhone(c.getString(c.getColumnIndex("phone")));
            student.setAddress(c.getString(c.getColumnIndex("address")));
            student.setEmail(c.getString(c.getColumnIndex("email")));
            student.setAge(c.getInt(c.getColumnIndex("age")));
            student.setRate(c.getFloat(c.getColumnIndex("rate")));
            student.setPhoto(c.getString(c.getColumnIndex("photo")));
            students.add(student);
        }
        c.close();
        return students;
    }

    public void delete(Student student) {
        SQLiteDatabase db = getWritableDatabase();

        String [] params = {student.getId().toString()};
        db.delete("Students", "id = ?", params);
    }

    public void update(Student student) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = getStudentData(student);

        String[] params = {student.getId().toString()};
        db.update("Students", data, "id = ?", params);
    }

    private ContentValues getStudentData(Student student) {
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        values.put("phone", student.getPhone());
        values.put("address", student.getAddress());
        values.put("email", student.getEmail());
        values.put("age", student.getAge());
        values.put("rate", student.getRate());
        values.put("photo", student.getPhoto());
        return values;
    }

    public boolean is_student(String phone) {
        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT phone FROM Students WHERE phone = ?";

        Cursor cursor = db.rawQuery(sql, new String[] {phone});
        return (cursor.getCount() > 0);
    }
}
