package com.example.librarydatabase.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.librarydatabase.model.Books;
import com.example.librarydatabase.model.Students;
import com.example.librarydatabase.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class studentsdatabase extends SQLiteOpenHelper {


    private final Context context;

    public studentsdatabase(@Nullable Context context) {
        super(context, Constants.Student_TABLE_NAME,null, Constants.DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTSTABLE=" CREATE TABLE "+Constants.Student_TABLE_NAME+"("+Constants.Student_KEY_ID+" INTEGER PRIMARY KEY ,"+Constants.Studentsname+
                " TEXT ,"+Constants.Booksname+" TEXT ,"+Constants.Student_id+" INTEGER ,"+Constants.Debt+" INTEGER ,"+
                Constants.KEY_DATEADDED+" STRING "+")";
        db.execSQL(CREATE_STUDENTSTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+Constants.Student_TABLE_NAME);
        onCreate(db);

    }

    public void onstudentsadded(Students students)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constants.Studentsname,students.getStudent_name());
        values.put(Constants.Booksname,students.getBook_name());
        values.put(Constants.Student_id,students.getStudent_id());
        values.put(Constants.Debt,students.getDebt());
        values.put(Constants.KEY_DATEADDED,new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
        db.insert(Constants.Student_TABLE_NAME,null,values);
        db.close();
    }

    public Students getbook(int id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(Constants.Student_TABLE_NAME,new String[]{Constants.Student_KEY_ID,Constants.Studentsname,Constants.Booksname,Constants.Student_id,
                Constants.Debt,Constants.KEY_DATEADDED},Constants.Student_KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        Students students=new Students();
        if(cursor!=null)
        {
            students.setId(cursor.getInt(cursor.getColumnIndex(Constants.Student_KEY_ID)));
            students.setStudent_id(cursor.getInt(cursor.getColumnIndex(Constants.Student_id)));
            students.setBook_name(cursor.getString(cursor.getColumnIndex(Constants.Booksname)));
            students.setStudent_name(cursor.getString(cursor.getColumnIndex(Constants.Studentsname)));
            students.setDebt(cursor.getInt(cursor.getColumnIndex(Constants.Debt)));
            students.setDate_issued(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATEADDED)));
        }
        return students;
    }

    public List<Students> getallstudents()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(Constants.Student_TABLE_NAME,new String[]{Constants.Student_KEY_ID,Constants.Studentsname,Constants.Booksname,Constants.Student_id,
                Constants.Debt,Constants.KEY_DATEADDED},null,null,null,null,Constants.KEY_DATEADDED+" DESC");


        List<Students>slist=new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do {
                Students students=new Students();
                students.setId(cursor.getInt(cursor.getColumnIndex(Constants.Student_KEY_ID)));
                students.setStudent_id(cursor.getInt(cursor.getColumnIndex(Constants.Student_id)));
                students.setBook_name(cursor.getString(cursor.getColumnIndex(Constants.Booksname)));
                students.setStudent_name(cursor.getString(cursor.getColumnIndex(Constants.Studentsname)));
                students.setDebt(cursor.getInt(cursor.getColumnIndex(Constants.Debt)));
                students.setDate_issued(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATEADDED)));
                slist.add(students);
            }
            while (cursor.moveToNext());
        }
        return slist;
    }

    public int update(Students students)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constants.Studentsname,students.getStudent_name());
        values.put(Constants.Booksname,students.getBook_name());
        values.put(Constants.Student_id,students.getStudent_id());
        values.put(Constants.Debt,students.getDebt());
        values.put(Constants.KEY_DATEADDED,new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
        return db.update(Constants.Student_TABLE_NAME,values,Constants.Student_KEY_ID+"=?",new String[]{String.valueOf(students.getId())});
    }

    public void deletestudent(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Constants.Student_TABLE_NAME,Constants.Student_KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public int getcount()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String query= String.format(" SELECT * FROM %s", Constants.Student_TABLE_NAME);
        Cursor cursor=db.rawQuery(query,null);
        return cursor.getCount();
    }



}
