package com.example.librarydatabase.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.librarydatabase.model.Books;
import com.example.librarydatabase.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class booksdatabase extends SQLiteOpenHelper {


    private final Context context;

    public booksdatabase(@Nullable Context context) {
        super(context, Constants.TABLE_NAME,null,Constants.DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKSTABLE=" CREATE TABLE "+Constants.TABLE_NAME+"("+Constants.KEY_ID+" INTEGER PRIMARY KEY ,"+Constants.KEY_BOOKNAME+
                " TEXT ,"+Constants.KEY_AUTHOR+" TEXT ,"+Constants.KEY_STOCK+" INTEGER ,"+Constants.KEY_DATEADDED+" STRING "+")";
            db.execSQL(CREATE_BOOKSTABLE);
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(db);
    }

    //CRUD OPERATIONS

    public void onbooksadded(Books books)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constants.KEY_BOOKNAME,books.getBook_name());
        values.put(Constants.KEY_AUTHOR,books.getAuthor());
        values.put(Constants.KEY_STOCK,books.getStock());
        values.put(Constants.KEY_DATEADDED,new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
        db.insert(Constants.TABLE_NAME,null,values);
        db.close();
    }

    public Books getbook(int id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(Constants.TABLE_NAME,new String[]{Constants.KEY_ID,Constants.KEY_BOOKNAME,Constants.KEY_AUTHOR,Constants.KEY_STOCK,
        Constants.KEY_DATEADDED},Constants.KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        Books books=new Books();
        if(cursor!=null)
        {
            books.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
            books.setBook_name(cursor.getString(cursor.getColumnIndex(Constants.KEY_BOOKNAME)));
            books.setAuthor(cursor.getString(cursor.getColumnIndex(Constants.KEY_AUTHOR)));
            books.setStock(cursor.getInt(cursor.getColumnIndex(Constants.KEY_STOCK)));
            books.setDateitemadded(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATEADDED)));
        }
        return books;
    }

    public List<Books> getallbooks()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(Constants.TABLE_NAME,new String[]{Constants.KEY_ID,Constants.KEY_BOOKNAME,Constants.KEY_AUTHOR,Constants.KEY_STOCK,
                Constants.KEY_DATEADDED},null,null,null,null,Constants.KEY_DATEADDED+" DESC ");


        List<Books>list=new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do {
                Books books=new Books();
                books.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
                books.setBook_name(cursor.getString(cursor.getColumnIndex(Constants.KEY_BOOKNAME)));
                books.setAuthor(cursor.getString(cursor.getColumnIndex(Constants.KEY_AUTHOR)));
                books.setStock(cursor.getInt(cursor.getColumnIndex(Constants.KEY_STOCK)));
                books.setDateitemadded(cursor.getString(cursor.getColumnIndex(Constants.KEY_DATEADDED)));
                list.add(books);
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    public int update(Books books)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constants.KEY_BOOKNAME,books.getBook_name());
        values.put(Constants.KEY_AUTHOR,books.getAuthor());
        values.put(Constants.KEY_STOCK,books.getStock());
        values.put(Constants.KEY_DATEADDED,new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
        return db.update(Constants.TABLE_NAME,values,Constants.KEY_ID+"=?",new String[]{String.valueOf(books.getId())});
    }

    public void deletebook(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
         db.delete(Constants.TABLE_NAME,Constants.KEY_ID+"=?",new String[]{String.valueOf(id)});
         db.close();
    }

    public int getcount()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String query= String.format(" SELECT * FROM %s", Constants.TABLE_NAME);
        Cursor cursor=db.rawQuery(query,null);
        return cursor.getCount();
    }

}
