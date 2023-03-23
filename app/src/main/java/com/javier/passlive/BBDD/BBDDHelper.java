package com.javier.passlive.BBDD;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.javier.passlive.Model.Password;

import java.util.ArrayList;

public class BBDDHelper extends SQLiteOpenHelper {


    public BBDDHelper(@Nullable Context context) {
        super(context, Constans.BD_NAME, null, Constans.BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creamos tabla en BBDD
        db.execSQL(Constans.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constans.TABLE_NAME);
        onCreate(db);

    }

    //Método para ingresar registro en BBDD

    public long insertRecord(String tittle, String account, String usename, String password,
                             String websites, String notes, String recordTime, String updateTime) {

        //Indicamos que la BBDD va a ser editable

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //Insertamos los datos
        values.put(Constans.C_TITTLE, tittle);
        values.put(Constans.C_ACCOUNT, account);
        values.put(Constans.C_USERNAME, usename);
        values.put(Constans.C_PASSWORD, password);
        values.put(Constans.C_WEBSITES, websites);
        values.put(Constans.C_NOTES, notes);
        values.put(Constans.C_RECORD_TIME, recordTime);
        values.put(Constans.C_UPDATE_TIME, updateTime);

        //Insertamos la fila
        long id = db.insert(Constans.TABLE_NAME, null, values);

        //Cerramos conexión de BBDD

        db.close();

        //Devuelve id de registro insertado
        return id;

    }
//Método para ordenar los registros por el más nuevo, el más antiguo, por el nombre del título asc, desc
 //Método regresa la lista de registros
    public ArrayList<Password> GetAllrecord(String orderby){
    ArrayList<Password> passwordList = new ArrayList<>();
    //Creamos consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Constans.TABLE_NAME + " ORDER BY " + orderby;

        SQLiteDatabase db = this.getWritableDatabase();
        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
           do {
            @SuppressLint("Range") Password model_password = new Password(
                    "" + cursor.getInt(cursor.getColumnIndex(Constans.C_ID)),
                    ""+ cursor.getString(cursor.getColumnIndex(Constans.C_TITTLE)),
                    ""+ cursor.getString(cursor.getColumnIndex(Constans.C_ACCOUNT)),
                    ""+ cursor.getString(cursor.getColumnIndex(Constans.C_USERNAME)),
                    ""+ cursor.getString(cursor.getColumnIndex(Constans.C_PASSWORD)),
                    ""+ cursor.getString(cursor.getColumnIndex(Constans.C_WEBSITES)),
                    ""+ cursor.getString(cursor.getColumnIndex(Constans.C_NOTES)),
                    ""+ cursor.getString(cursor.getColumnIndex(Constans.C_RECORD_TIME)),
                    ""+ cursor.getString(cursor.getColumnIndex(Constans.C_UPDATE_TIME)));

            passwordList.add(model_password);
           }while (cursor.moveToNext());
        }
        db.close();
        return passwordList;
    }
    //Método para buscar registros
    public ArrayList<Password> search_Records(String consultation){
        ArrayList<Password> passwordList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Constans.TABLE_NAME + " WHERE " + Constans.C_TITTLE +
                " LIKE '%" + consultation + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                @SuppressLint("Range") Password model_password = new Password(
                        "" + cursor.getInt(cursor.getColumnIndex(Constans.C_ID)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_TITTLE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_ACCOUNT)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_USERNAME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_PASSWORD)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_WEBSITES)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_NOTES)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_RECORD_TIME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_UPDATE_TIME)));

                passwordList.add(model_password);
            }while (cursor.moveToNext());
        }
        db.close();
        return passwordList;
    }
//Obtenemos el total de registros de la BBDD
    public int GetRecordNumber(){
        String countquery = "SELECT * FROM " + Constans.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countquery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    }
