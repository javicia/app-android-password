package com.javier.passlive.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.javier.passlive.BBDD.BBDD_Helper;
import com.javier.passlive.BBDD.Constans;
import com.javier.passlive.Model.Web;
import java.util.ArrayList;


public class WebDAO {

    private static SQLiteDatabase db;

    public WebDAO(Context context) {
        BBDD_Helper dbHelper = new BBDD_Helper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    //Método para ingresar registro en BBDD
    public static long insertRecordWeb(String tittle, String account, String username, String password,
                                       String websites, String notes, String image, String recordTime, String updateTime) {

        ContentValues values = new ContentValues();

        //Insertamos los datos
        values.put(Constans.W_TITTLE, tittle);
        values.put(Constans.W_ACCOUNT, account);
        values.put(Constans.W_USERNAME, username);
        values.put(Constans.W_PASSWORD, password);
        values.put(Constans.W_WEBSITES, websites);
        values.put(Constans.W_NOTES, notes);
        values.put(Constans.W_IMAGE, image);
        values.put(Constans.W_RECORD_TIME, recordTime);
        values.put(Constans.W_UPDATE_TIME, updateTime);

        //Insertamos la fila
        long id = db.insert(Constans.TABLE_ACCOUNT_WEB, null, values);

        //Cerramos conexión de BBDD

        db.close();

        //Devuelve id de registro insertado
        return id;
    }

    //Método para actualizar registros web en BBDD
    public static void updateRecordWeb(String id, String tittle, String account, String username, String password,
                                       String websites, String notes, String image, String recordTime, String updateTime) {
        ContentValues values = new ContentValues();

        //Insertamos los datos
        values.put(Constans.W_TITTLE, tittle);
        values.put(Constans.W_ACCOUNT, account);
        values.put(Constans.W_USERNAME, username);
        values.put(Constans.W_PASSWORD, password);
        values.put(Constans.W_WEBSITES, websites);
        values.put(Constans.W_NOTES, notes);
        values.put(Constans.W_IMAGE, image);
        values.put(Constans.W_RECORD_TIME, recordTime);
        values.put(Constans.W_UPDATE_TIME, updateTime);

        //Actualizamos la fila
        db.update(Constans.TABLE_ACCOUNT_WEB, values, Constans.W_ID + "=?", new String[]{id});

        //Cerramos conexión de BBDD
        db.close();
    }

    public int deleteRecordWeb(Web web) {
        int rowsAffected = db.delete(Constans.TABLE_ACCOUNT_WEB,
                Constans.W_ID + "=?",
                new String[]{web.getId()});
        return rowsAffected;
    }

    //Método para ordenar los registros por el más nuevo, el más antiguo, por el nombre del título asc, desc
    //Método regresa la lista de registros
    public static ArrayList<Web> GetAllrecordWeb(String orderby){
        ArrayList<Web> webList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Constans.TABLE_ACCOUNT_WEB + " ORDER BY " + orderby;
        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                @SuppressLint("Range") Web model_password = new Web(
                        "" + cursor.getInt(cursor.getColumnIndex(Constans.W_ID)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_TITTLE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_ACCOUNT)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_USERNAME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_PASSWORD)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_WEBSITES)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_NOTES)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_IMAGE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_RECORD_TIME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_UPDATE_TIME)));

                webList.add(model_password);
            }while (cursor.moveToNext());
        }
        db.close();
        return webList;
    }

    //Método para buscar registros
    public static ArrayList<Web> search_RecordsWeb(String consultation){
        ArrayList<Web> webList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Constans.TABLE_ACCOUNT_WEB + " WHERE " + Constans.W_TITTLE +
                " LIKE '%" + consultation + "%'";

        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                @SuppressLint("Range") Web model_password = new Web(
                        "" + cursor.getInt(cursor.getColumnIndex(Constans.W_ID)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_TITTLE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_ACCOUNT)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_USERNAME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_PASSWORD)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_WEBSITES)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_NOTES)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_IMAGE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_RECORD_TIME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.W_UPDATE_TIME)));

                webList.add(model_password);
            }while (cursor.moveToNext());
        }
        db.close();
        return webList;
    }

    public static void deleteRecordWeb(String id) {
        db.execSQL("DELETE FROM " + Constans.TABLE_ACCOUNT_WEB);
        db.close();
    }
}