package com.javier.passlive.BBDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BBDDHelper extends SQLiteOpenHelper {


    public BBDDHelper(@Nullable Context context) {
        super(context,Constans.BD_NAME, null, Constans.BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    //Creamos tabla en BBDD
        db.execSQL(Constans.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Constans.TABLE_NAME);
        onCreate(db);

    }

    //Método para ingresar registro en BBDD

    public long insertRecord (String tittle, String account, String usename, String password,
                              String websites, String notes, String recordTime, String updateTime){

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
}
