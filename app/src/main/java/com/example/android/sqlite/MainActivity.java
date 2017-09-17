package com.example.android.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button delete, insert, show, deletebyid, update;
    EditText firstname, lastname, phone, Textid;
    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBase = new DataBase(this);
        delete = (Button) findViewById(R.id.deleteBtn);
        insert = (Button) findViewById(R.id.insertBtn);
        show = (Button) findViewById(R.id.showBtn);
        firstname = (EditText) findViewById(R.id.FirstName);
        lastname = (EditText) findViewById(R.id.LastName);
        phone = (EditText) findViewById(R.id.Phone);
        deletebyid = (Button) findViewById(R.id.deleteIdBtn);
        update = (Button) findViewById(R.id.updateBtn);
        Textid = (EditText) findViewById(R.id.idText);
        final SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentValues.put("firstname", firstname.getText().toString());
                contentValues.put("lastname", lastname.getText().toString());
                contentValues.put("phone", phone.getText().toString());
                sqLiteDatabase.insert("contact", null, contentValues);
                Log.d("log", "insert");
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor;
                cursor = sqLiteDatabase.query("Contact", null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do {
                        Log.d("log", "id " + cursor.getInt(cursor.getColumnIndex("id")) +
                                " First Name " + cursor.getString(cursor.getColumnIndex("firstname"))
                                + " Last Name " + cursor.getString(cursor.getColumnIndex("lastname")) + " phone "
                                + cursor.getInt(cursor.getColumnIndex("phone")));
                    } while (cursor.moveToNext());
                }else{
                    Log.d("log", "Table not found");
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        sqLiteDatabase.delete("contact", null, null);
        Log.d("log", "table deleted");
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentValues.put("firstname", firstname.getText().toString());
                contentValues.put("lastname", lastname.getText().toString());
                contentValues.put("phone", phone.getText().toString());
                sqLiteDatabase.update("contact", contentValues, "id = ?", new String[]{Textid.getText().toString()});
                Log.d("log", "updated!");
            }
        });
        deletebyid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            sqLiteDatabase.delete("contact", "id = ?", new String[]{Textid.getText().toString()});
                Log.d("log", "deleted");
            }
        });
    }
}
