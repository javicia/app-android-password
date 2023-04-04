package com.javier.passlive.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.javier.passlive.BBDD.BBDD_Helper;
import com.javier.passlive.BBDD.Constans;
import com.javier.passlive.Model.Card;
import java.util.ArrayList;


public class CardDAO {

    private static SQLiteDatabase db;

    public CardDAO(Context context) {
        BBDD_Helper dbHelper = new BBDD_Helper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }
    //Insertamos registros en Tarjeta
    public static long insertRecordCard(String title, String account_name, String number, String date, String cvc,
                                        String notes, String image, String recordTime, String updateTime) {
        ContentValues values = new ContentValues();

        //Insertamos los datos
        values.put(Constans.C_TITLE_CARD, title);
        values.put(Constans.C_USERNAME, account_name);
        values.put(Constans.C_NUMBER, number);
        values.put(Constans.C_DATE, date);
        values.put(Constans.C_CVC, cvc);
        values.put(Constans.C_NOTES, notes);
        values.put(Constans.C_IMAGE, image);
        values.put(Constans.C_RECORD_TIME, recordTime);
        values.put(Constans.C_UPDATE_TIME, updateTime);

        //Insertamos la fila
        long id = db.insert(Constans.TABLE_CARD, null, values);

        //Cerramos conexión de BBDD

        db.close();

        //Devuelve id de registro insertado
        return id;

    }
    public static void updateRecordCard(String id_card, String title, String username, String number, String dates, String cvc,
                                        String c_notes, String c_image, String c_recordTime, String c_updateTime, String s){
        ContentValues values = new ContentValues();

        //Insertamos los datos
        values.put(Constans.ID_CARD, id_card);
        values.put(Constans.C_TITLE_CARD, title);
        values.put(Constans.C_USERNAME, username);
        values.put(Constans.C_NUMBER, number);
        values.put(Constans.C_DATE, dates);
        values.put(Constans.C_CVC, cvc);
        values.put(Constans.C_NOTES, c_notes);
        values.put(Constans.C_IMAGE, c_image);
        values.put(Constans.C_RECORD_TIME, c_recordTime);
        values.put(Constans.C_UPDATE_TIME, c_updateTime);
        //Actualizamos la fila
        db.update(Constans.TABLE_CARD, values, Constans.ID_CARD + "=?", new String[]{id_card});

        //Cerramos conexión de BBDD
        db.close();
    }
    public static ArrayList<Card> GetAllrecordCard(String orderby){
        ArrayList<Card> cardList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuerycard = "SELECT * FROM " + Constans.TABLE_CARD + " ORDER BY " + orderby;
        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        Cursor cursor = db.rawQuery(selectQuerycard, null);

        if(cursor.moveToFirst()){
            do {
                @SuppressLint("Range") Card model_card = new Card(
                        "" + cursor.getInt(cursor.getColumnIndex(Constans.ID_CARD)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_TITLE_CARD)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_USERNAME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_NUMBER)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_DATE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_DATE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_NOTES)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_IMAGE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_RECORD_TIME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_UPDATE_TIME)));

                cardList.add(model_card);
            }while (cursor.moveToNext());
        }
        db.close();
        return cardList;
    }
    public static ArrayList<Card> search_RecordsCard(String consultation){
        ArrayList<Card> cardList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Constans.TABLE_CARD + " WHERE " + Constans.C_TITLE_CARD +
                " LIKE '%" + consultation + "%'";
        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                @SuppressLint("Range") Card model_card = new Card(
                        "" + cursor.getInt(cursor.getColumnIndex(Constans.ID_CARD)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_TITLE_CARD)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_USERNAME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_NUMBER)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_DATE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_CVC)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_NOTES)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_IMAGE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_RECORD_TIME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.C_UPDATE_TIME)));

                cardList.add(model_card);
            }while (cursor.moveToNext());
        }
        db.close();
        return cardList;
    }
    public static void deleteRecordCard(String id){
        db.delete(Constans.TABLE_CARD, Constans.ID_CARD+" = ?", new String[]{id});
        db.close();
    }
}