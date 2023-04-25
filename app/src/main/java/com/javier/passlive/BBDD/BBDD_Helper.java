package com.javier.passlive.BBDD;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;
import com.javier.passlive.Model.Bank;
import com.javier.passlive.Model.Card;
import com.javier.passlive.Model.Web;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;


import java.util.ArrayList;

        public class BBDD_Helper extends SQLiteOpenHelper {
        private static  BBDD_Helper instance;
        public static final String PASS_PHARSE = "DGFDFGFGFDG";

            private SQLiteDatabase db;
            public BBDD_Helper(@Nullable Context context) {
        super(context, Constans.BD_NAME, null, Constans.BD_VERSION);
                SQLiteDatabase.loadLibs(context);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constans.CREATE_TABLE_ACCOUNT_WEB);
        db.execSQL(Constans.CREATE_TABLE_CATEGORY);
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


    public long insertRecordWeb(String tittle, String account, String username, String password,
                                String websites, String notes, String image, String recordTime, String updateTime) {
        db = this.getWritableDatabase(PASS_PHARSE);
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

     public long insertRecordBank(String title_bank, String bank, String account_bank,
                                   String number, String websites, String notes, String image, String recordTime, String updateTime) {
         db = this.getWritableDatabase(PASS_PHARSE);
          ContentValues values = new ContentValues();

          //Insertamos los datos
          values.put(Constans.B_TITLE_BANK, title_bank);
          values.put(Constans.B_BANK, bank);
          values.put(Constans.B_ACCOUNT_BANK, account_bank);
          values.put(Constans.B_NUMBER, number);
          values.put(Constans.B_WEBSITES, websites);
          values.put(Constans.B_NOTES, notes);
          values.put(Constans.B_IMAGE, image);
          values.put(Constans.B_RECORD_TIME, recordTime);
          values.put(Constans.B_UPDATE_TIME, updateTime);

          //Insertamos la fila
          long id = db.insert(Constans.TABLE_ACCOUNT_BANK, null, values);

          //Cerramos conexión de BBDD

          db.close();
          //Devuelve id de registro insertado
          return id;
      }
      //Insertamos registros en Tarjeta
      public long insertRecordCard(String title, String account_name, String number, String date, String cvc,
                                   String notes, String image, String recordTime, String updateTime) {
          ContentValues values = new ContentValues();
          db = this.getWritableDatabase(PASS_PHARSE);
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
//Obtenemos el total de registros de la BBDD
    public int GetRecordNumber() {
        db = this.getWritableDatabase(PASS_PHARSE);
        String query = "SELECT COUNT(*) FROM (SELECT * FROM " + Constans.TABLE_ACCOUNT_WEB
                + " UNION ALL SELECT * FROM " + Constans.TABLE_ACCOUNT_BANK
                + " UNION ALL SELECT * FROM " + Constans.TABLE_CARD + ")";
        Cursor cursor = db.rawQuery(query, null);

        int count = 0;
        if (cursor.moveToFirst()){
            count= cursor.getInt(0);
        }
        cursor.close();
        return count;

    }
    //Método para actualizar registros web en BBDD


    //Método para ordenar los registros por el más nuevo, el más antiguo, por el nombre del título asc, desc
    //Método regresa la lista de registros
    public ArrayList<Web> GetAllrecordWeb(String orderby) {
        ArrayList<Web> webList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Constans.TABLE_ACCOUNT_WEB + " ORDER BY " + orderby;
        //Recorremos todos los registros de la BD para que se puedan añadir a la lista

     db = this.getWritableDatabase(PASS_PHARSE);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Web model_web = new Web(
                        "" + cursor.getInt(cursor.getColumnIndex(Constans.W_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.W_TITTLE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.W_ACCOUNT)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.W_USERNAME)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.W_PASSWORD)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.W_WEBSITES)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.W_NOTES)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.W_IMAGE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.W_RECORD_TIME)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.W_UPDATE_TIME))
                );
                webList.add(model_web);
            } while (cursor.moveToNext());
        }
        db.close();
        return webList;
    }
    public ArrayList<Bank> GetAllrecordBank(String orderby) {
        ArrayList<Bank> bankList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuerybank = "SELECT * FROM " + Constans.TABLE_ACCOUNT_BANK + " ORDER BY " + orderby;

        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        db = this.getWritableDatabase(PASS_PHARSE);
        Cursor cursor = null;
        try{
            cursor= db.rawQuery(selectQuerybank, null);

            while (cursor.moveToNext()){
                @SuppressLint("Range") Bank model_bank = new Bank(
                        "" + cursor.getInt(cursor.getColumnIndex(Constans.B_ID_BANK)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.B_TITLE_BANK)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.B_BANK)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.B_TITLE_BANK)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.B_ACCOUNT_BANK)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.B_NUMBER)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.B_WEBSITES)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.B_NOTES)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.B_IMAGE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.B_RECORD_TIME)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.B_UPDATE_TIME)));

                bankList.add(model_bank);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return bankList;
    }
    public ArrayList<Card> GetAllrecordCard(String orderby){
        ArrayList<Card> cardList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuerycard = "SELECT * FROM " + Constans.TABLE_CARD + " ORDER BY " + orderby;
        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        db = this.getWritableDatabase(PASS_PHARSE);
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuerycard, null);

            while (cursor.moveToNext()){
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
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return cardList;
    }

    public void updateRecordWeb(String id, String tittle, String account, String username, String password,
                                String websites, String notes, String image, String recordTime, String updateTime) {
        db = this.getWritableDatabase(PASS_PHARSE);

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

    public void updateRecordBank(String id_bank, String title, String bank, String account_Bank, String number, String b_websites, String b_notes,
                                 String b_image, String b_recordTime, String b_updateTime){
        db = this.getWritableDatabase(PASS_PHARSE);
        ContentValues values = new ContentValues();

        //Insertamos los datos
        values.put(Constans.B_ID_BANK, id_bank);
        values.put(Constans.B_TITLE_BANK, title);
        values.put(Constans.B_BANK, bank);
        values.put(Constans.B_ACCOUNT_BANK, account_Bank);
        values.put(Constans.B_NUMBER, number);
        values.put(Constans.B_WEBSITES, b_websites);
        values.put(Constans.B_NOTES, b_notes);
        values.put(Constans.B_IMAGE, b_image);
        values.put(Constans.B_RECORD_TIME, b_recordTime);
        values.put(Constans.B_UPDATE_TIME, b_updateTime);
        //Actualizamos la fila
        db.update(Constans.TABLE_ACCOUNT_BANK, values, Constans.B_ID_BANK + "=?", new String[]{id_bank});

        //Cerramos conexión de BBDD
        db.close();
    }

    public void updateRecordCard(String id_card, String title, String username, String number, String dates, String cvc,
                                 String c_notes, String c_image, String c_recordTime, String c_updateTime, String s){
        ContentValues values = new ContentValues();
        db = this.getWritableDatabase(PASS_PHARSE);
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

    //Método para buscar registros
    public ArrayList<Web> search_RecordsWeb(String consultation){
        ArrayList<Web> webList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Constans.TABLE_ACCOUNT_WEB + " WHERE " + Constans.W_TITTLE +
                " LIKE '%" + consultation + "%'";

        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        db = this.getWritableDatabase(PASS_PHARSE);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                @SuppressLint("Range") Web model_web = new Web(
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

                webList.add(model_web);
            }while (cursor.moveToNext());
        }
        db.close();
        return webList;
    }

    public ArrayList<Bank> search_RecordsBank(String consultation){
        ArrayList<Bank> bankList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Constans.TABLE_ACCOUNT_BANK + " WHERE " + Constans.B_TITLE_BANK +
                " LIKE '%" + consultation + "%'";

        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        db = this.getWritableDatabase(PASS_PHARSE);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                @SuppressLint("Range") Bank model_bank = new Bank(
                        "" + cursor.getInt(cursor.getColumnIndex(Constans.B_ID_BANK)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.B_TITLE_BANK)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.B_BANK)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.B_TITLE_BANK)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.B_ACCOUNT_BANK)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.B_NUMBER)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.B_WEBSITES)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.B_NOTES)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.B_IMAGE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constans.B_RECORD_TIME)),
                        "" + cursor.getString(cursor.getColumnIndex(Constans.B_UPDATE_TIME)));

                bankList.add(model_bank);
            }while (cursor.moveToNext());
        }
        db.close();
        return bankList;
    }
    public ArrayList<Card> search_RecordsCard(String consultation){
        ArrayList<Card> cardList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Constans.TABLE_CARD + " WHERE " + Constans.C_TITLE_CARD +
                " LIKE '%" + consultation + "%'";
        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        db = this.getWritableDatabase(PASS_PHARSE);
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

    public void deleteRecordWeb(String id) {
        db = this.getWritableDatabase(PASS_PHARSE);
        db.delete(Constans.TABLE_ACCOUNT_WEB, Constans.W_ID + " = ?", new String[]{id});
        db.close();
    }
    public void deleteRecordBank(String id){
        db = this.getWritableDatabase(PASS_PHARSE);
        db.delete(Constans.TABLE_ACCOUNT_BANK, Constans.B_ID_BANK+" = ?", new String[]{id});
        db.close();
    }
    public void deleteRecordCard(String id){
        db = this.getWritableDatabase(PASS_PHARSE);
        db.delete(Constans.TABLE_CARD, Constans.ID_CARD+" = ?", new String[]{id});
        db.close();
    }


    //Método para eliminar todos los registros de la BBDD
    public void deleteAllRecord(){
        db = this.getWritableDatabase(PASS_PHARSE);
        db.execSQL("DELETE FROM " + Constans.TABLE_ACCOUNT_WEB);
        db.execSQL("DELETE FROM " + Constans.TABLE_ACCOUNT_BANK);
        db.execSQL("DELETE FROM " + Constans.TABLE_CARD);
        db.close();
    }
    }

