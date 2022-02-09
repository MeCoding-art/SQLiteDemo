package com.machine.sqlitedemo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.machine.sqlitedemo.model.Contact;
import com.machine.sqlitedemo.params.Params;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Contacts.SettingsColumns.KEY;

public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context){
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE " + Params.TABLE_NAME + "("
                + Params.KEY_ID + " INTEGER PRIMARY KEY, " + Params.KEY_NAME
                + " TEXT, " + Params.KEY_PHONE + " TEXT" + ")";

        Log.d("dbLucky", "Query being run is :"+ create);
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Params.KEY_NAME, contact.getName());
        contentValues.put(Params.KEY_PHONE, contact.getPhone());

        db.insert(Params.TABLE_NAME, null, contentValues);
        Log.d("dbLucky", "Successfully Inserted");
        db.close();

    }

    public List<Contact> getAllContacts(){
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //generate query to read from database
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);

        if(cursor.moveToFirst()){
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
                contactList.add(contact);
            }while (cursor.moveToNext());
        }


        return contactList;
    }

    public void deleteOne(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(Params.TABLE_NAME, Params.KEY_ID + "=?" , new String[]{String.valueOf(id)});

    }

    public int updateContact(Contact contact){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_NAME, contact.getName());
        values.put(Params.KEY_PHONE, contact.getPhone());

        return sqLiteDatabase.update(Params.TABLE_NAME, values, Params.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});

    }


}
