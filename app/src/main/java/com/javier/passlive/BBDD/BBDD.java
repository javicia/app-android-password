package com.javier.passlive.BBDD;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.javier.passlive.Model.Web;

import java.util.ArrayList;

public class BBDD extends SQLiteOpenHelper {


    public BBDD(@Nullable Context context) {
        super(context, Constans.BD_NAME, null, Constans.BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constans.CREATE_TABLE_CATEGORY);
        db.execSQL(Constans.CREATE_TABLE_ACCOUNT_WEB);
        db.execSQL(Constans.CREATE_TABLE_ACCOUNT_BANK);
        db.execSQL(Constans.CREATE_TABLE_CARD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constans.TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + Constans.TABLE_ACCOUNT_WEB);
        db.execSQL("DROP TABLE IF EXISTS " + Constans.TABLE_ACCOUNT_BANK);
        db.execSQL("DROP TABLE IF EXISTS " + Constans.TABLE_CARD);
        onCreate(db);
    }

    //Método para ingresar registro en BBDD
    public long insertRecordWeb(String tittle, String account, String username, String password,
                             String websites, String notes, String image, String recordTime, String updateTime) {

        //Indicamos que la BBDD va a ser editable
        SQLiteDatabase db = this.getWritableDatabase();

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

    //Método para actualizar registros en BBDD
    public void updateRecordWeb(String id, String tittle, String account, String username, String password,
                             String websites, String notes, String image, String recordTime, String updateTime) {

        //Indicamos que la BBDD va a ser editable
        SQLiteDatabase db = this.getWritableDatabase();
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

    public void updateRecordBank(String id_bank, String account_Bank, String b_websites, String b_notes,
                                 String b_image, String b_recordTime, String b_updateTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //Insertamos los datos
        values.put(Constans.B_ACCOUNT_BANK, account_Bank);
        values.put(Constans.B_WEBSITES, b_websites);
        values.put(Constans.B_NOTES, b_notes);
        values.put(Constans.B_IMAGE, b_image);
        values.put(Constans.B_RECORD_TIME, b_recordTime);
        values.put(Constans.B_UPDATE_TIME, b_updateTime);
        //Actualizamos la fila
        db.update(Constans.TABLE_ACCOUNT_BANK, values, Constans.W_ID + "=?", new String[]{id_bank});

        //Cerramos conexión de BBDD
        db.close();
    }
    public  void updateRecordCard(String id_card, String username, int number, String dates, int cvc,
                                  String c_notes, String c_image, String c_recordTime, String c_updateTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //Insertamos los datos
        values.put(Constans.ID_CARD, id_card);
        values.put(Constans.C_USERNAME, username);
        values.put(Constans.C_NUMBER, number);
        values.put(Constans.C_DATE, dates);
        values.put(Constans.C_CVC, cvc);
        values.put(Constans.C_NOTES, c_notes);
        values.put(Constans.C_IMAGE, c_image);
        values.put(Constans.C_RECORD_TIME, c_recordTime);
        values.put(Constans.C_UPDATE_TIME, c_updateTime);
        //Actualizamos la fila
        db.update(Constans.TABLE_CARD, values, Constans.W_ID + "=?", new String[]{id_card});

        //Cerramos conexión de BBDD
        db.close();
    }
//Método para ordenar los registros por el más nuevo, el más antiguo, por el nombre del título asc, desc
 //Método regresa la lista de registros
    public ArrayList<Web> GetAllrecord(String orderby){
    ArrayList<Web> passwordList = new ArrayList<>();
    //Creamos consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Constans.TABLE_ACCOUNT_WEB + Constans.TABLE_ACCOUNT_BANK +
                Constans.TABLE_CARD + " ORDER BY " + orderby;

        SQLiteDatabase db = this.getWritableDatabase();
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

            passwordList.add(model_password);
           }while (cursor.moveToNext());
        }
        db.close();
        return passwordList;
    }

    //Método para buscar registros
    public ArrayList<Web> search_Records(String consultation){
        ArrayList<Web> passwordList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Constans.TABLE_ACCOUNT_WEB + " WHERE " + Constans.W_TITTLE +
                " LIKE '%" + consultation + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
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

                passwordList.add(model_password);
            }while (cursor.moveToNext());
        }
        db.close();
        return passwordList;
    }

//Obtenemos el total de registros de la BBDD
    public int GetRecordNumber(){
        String countquery = "SELECT * FROM " + Constans.TABLE_ACCOUNT_WEB + Constans.TABLE_ACCOUNT_BANK + Constans.TABLE_CARD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countquery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //Eliminar registros
    public void deleteRecord(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constans.TABLE_ACCOUNT_WEB, Constans.W_ID+" = ?", new String[]{id});
        db.delete(Constans.TABLE_ACCOUNT_BANK, Constans.B_ID_BANK+" = ?", new String[]{id});
        db.delete(Constans.TABLE_CARD, Constans.ID_CARD+" = ?", new String[]{id});
        db.close();
    }

    //Método para eliminar todos los registros de la BBDD
    public void deleteAllRecord(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + Constans.TABLE_ACCOUNT_WEB);
        db.execSQL("DELETE FROM " + Constans.TABLE_ACCOUNT_BANK);
        db.execSQL("DELETE FROM " + Constans.TABLE_CARD);
        db.close();
    }
    }
