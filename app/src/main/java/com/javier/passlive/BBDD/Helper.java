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

public class Helper extends SQLiteOpenHelper {

        public SQLKeyGenerator sqlCipherKeyGenerator;
            private SQLiteDatabase db;
            public Helper(@Nullable Context context) {
        super(context, Query.BD_NAME, null, Query.BD_VERSION);
                SQLiteDatabase.loadLibs(context);
            }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Query.CREATE_TABLE_ACCOUNT_WEB);
        db.execSQL(Query.CREATE_TABLE_CATEGORY);
        db.execSQL(Query.CREATE_TABLE_ACCOUNT_BANK);
        db.execSQL(Query.CREATE_TABLE_CARD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Query.TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + Query.TABLE_ACCOUNT_WEB);
        db.execSQL("DROP TABLE IF EXISTS " + Query.TABLE_ACCOUNT_BANK);
        db.execSQL("DROP TABLE IF EXISTS " + Query.TABLE_CARD);
        onCreate(db);
    }

    //Método para insertar registros de aplicaciones web
    public long insertRecordWeb(String tittle, String account, String username, String password,
                                String websites, String notes, String image, String recordTime, String updateTime) throws Exception {
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        ContentValues values = new ContentValues();

        //Insertamos los datos
        values.put(Query.TITLE, tittle);
        values.put(Query.W_ACCOUNT, account);
        values.put(Query.W_USERNAME, username);
        values.put(Query.W_PASSWORD, password);
        values.put(Query.W_WEBSITES, websites);
        values.put(Query.W_NOTES, notes);
        values.put(Query.W_IMAGE, image);
        values.put(Query.RECORD_TIME, recordTime);
        values.put(Query.UPDATE_TIME, updateTime);

        //Insertamos la fila
        long id = db.insert(Query.TABLE_ACCOUNT_WEB, null, values);

        //Cerramos conexión de BBDD

        db.close();

        //Devuelve id de registro insertado
        return id;
    }
    //Método para insertar registros de cuentas bancarias
     public long insertRecordBank(String title_bank, String bank, String account_bank,
                                   String number, String websites, String notes, String image, String recordTime, String updateTime) throws Exception {
         db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
          ContentValues values = new ContentValues();

          //Insertamos los datos
          values.put(Query.TITLE, title_bank);
          values.put(Query.B_BANK, bank);
          values.put(Query.B_ACCOUNT_BANK, account_bank);
          values.put(Query.B_NUMBER, number);
          values.put(Query.B_WEBSITES, websites);
          values.put(Query.B_NOTES, notes);
          values.put(Query.B_IMAGE, image);
          values.put(Query.RECORD_TIME, recordTime);
          values.put(Query.UPDATE_TIME, updateTime);

          //Insertamos la fila
          long id = db.insert(Query.TABLE_ACCOUNT_BANK, null, values);

          //Cerramos conexión de BBDD

          db.close();
          //Devuelve id de registro insertado
          return id;
      }
      //Método para insertar registros de tarjetas
      public long insertRecordCard(String title,
                                   String account_name, String number,
                                   String date, String cvc,
                                   String notes, String image,
                                   String recordTime,
                                   String updateTime) throws Exception {
          ContentValues values = new ContentValues();
          db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
          //Insertamos los datos
          values.put(Query.TITLE, title);
          values.put(Query.C_USERNAME, account_name);
          values.put(Query.C_NUMBER, number);
          values.put(Query.C_DATE, date);
          values.put(Query.C_CVC, cvc);
          values.put(Query.C_NOTES, notes);
          values.put(Query.C_IMAGE, image);
          values.put(Query.RECORD_TIME, recordTime);
          values.put(Query.UPDATE_TIME, updateTime);

          //Insertamos la fila
          long id = db.insert(Query.TABLE_CARD, null, values);

          //Cerramos conexión de BBDD

          db.close();

          //Devuelve id de registro insertado
          return id;
      }
    //Método para obtener el total de registros de la BBDD
    public int GetRecordNumber() throws Exception {
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        String query = "SELECT COUNT(*) FROM (SELECT * FROM " + Query.TABLE_ACCOUNT_WEB
                + " UNION ALL SELECT * FROM " + Query.TABLE_ACCOUNT_BANK
                + " UNION ALL SELECT * FROM " + Query.TABLE_CARD + ")";
        Cursor cursor = db.rawQuery(query, null);

        int count = 0;
        if (cursor.moveToFirst()){
            count= cursor.getInt(0);
        }
        cursor.close();
        return count;

    }

    public int GetRecordNumberWeb() throws Exception {
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        String query = "SELECT COUNT(*) FROM " + Query.TABLE_ACCOUNT_WEB;
        Cursor cursor = db.rawQuery(query, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int GetRecordNumberBank() throws Exception {
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        String query = "SELECT COUNT(*) FROM " + Query.TABLE_ACCOUNT_BANK;
        Cursor cursor = db.rawQuery(query, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int GetRecordNumberCard() throws Exception {
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        String query = "SELECT COUNT(*) FROM " + Query.TABLE_CARD;
        Cursor cursor = db.rawQuery(query, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    //Método para obetener registros de tarjetas
    public ArrayList<Web> GetAllrecordWeb(String orderby) throws Exception {
        ArrayList<Web> webList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Query.TABLE_ACCOUNT_WEB + " ORDER BY " + orderby;
        //Recorremos todos los registros de la BD para que se puedan añadir a la lista

     db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Web model_web = new Web(
                        "" + cursor.getInt(cursor.getColumnIndex(Query.W_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.TITLE)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.W_ACCOUNT)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.W_USERNAME)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.W_PASSWORD)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.W_WEBSITES)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.W_NOTES)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.W_IMAGE)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.RECORD_TIME)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.UPDATE_TIME))
                );
                webList.add(model_web);
            } while (cursor.moveToNext());
        }
        db.close();
        return webList;
    }
    //Método para obetener registros de cuentas bancarias
    public ArrayList<Bank> GetAllrecordBank(String orderby) throws Exception {
        ArrayList<Bank> bankList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuerybank = "SELECT * FROM " + Query.TABLE_ACCOUNT_BANK + " ORDER BY " + orderby;

        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        Cursor cursor = null;
        try{
            cursor= db.rawQuery(selectQuerybank, null);

            while (cursor.moveToNext()){
                @SuppressLint("Range") Bank model_bank = new Bank(
                        "" + cursor.getInt(cursor.getColumnIndex(Query.B_ID_BANK)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.TITLE)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.B_BANK)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.TITLE)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.B_ACCOUNT_BANK)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.B_NUMBER)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.B_WEBSITES)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.B_NOTES)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.B_IMAGE)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.RECORD_TIME)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.UPDATE_TIME)));

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
    //Método para obetener registros de tarjetas
    public ArrayList<Card> GetAllrecordCard(String orderby) throws Exception {
        ArrayList<Card> cardList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuerycard = "SELECT * FROM " + Query.TABLE_CARD + " ORDER BY " + orderby;
        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuerycard, null);

            while (cursor.moveToNext()){
                @SuppressLint("Range") Card model_card = new Card(
                        "" + cursor.getInt(cursor.getColumnIndex(Query.ID_CARD)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.TITLE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.C_USERNAME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.C_NUMBER)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.C_DATE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.C_DATE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.C_NOTES)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.C_IMAGE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.RECORD_TIME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.UPDATE_TIME)));

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
    //Método para actualizar registros de aplicaciones web
    public void updateRecordWeb(String id, String tittle, String account, String username, String password,
                                String websites, String notes, String image, String recordTime, String updateTime) throws Exception {
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());

        ContentValues values = new ContentValues();

        //Insertamos los datos
        values.put(Query.TITLE, tittle);
        values.put(Query.W_ACCOUNT, account);
        values.put(Query.W_USERNAME, username);
        values.put(Query.W_PASSWORD, password);
        values.put(Query.W_WEBSITES, websites);
        values.put(Query.W_NOTES, notes);
        values.put(Query.W_IMAGE, image);
        values.put(Query.RECORD_TIME, recordTime);
        values.put(Query.UPDATE_TIME, updateTime);

        //Actualizamos la fila
        db.update(Query.TABLE_ACCOUNT_WEB, values, Query.W_ID + "=?", new String[]{id});

        //Cerramos conexión de BBDD
        db.close();
    }
    //Método para actualizar registros de cuentas bancarias
    public void updateRecordBank(String id_bank, String title, String bank, String account_Bank, String number, String b_websites, String b_notes,
                                 String b_image, String b_recordTime, String b_updateTime) throws Exception {
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        ContentValues values = new ContentValues();

        //Insertamos los datos
        values.put(Query.B_ID_BANK, id_bank);
        values.put(Query.TITLE, title);
        values.put(Query.B_BANK, bank);
        values.put(Query.B_ACCOUNT_BANK, account_Bank);
        values.put(Query.B_NUMBER, number);
        values.put(Query.B_WEBSITES, b_websites);
        values.put(Query.B_NOTES, b_notes);
        values.put(Query.B_IMAGE, b_image);
        values.put(Query.RECORD_TIME, b_recordTime);
        values.put(Query.UPDATE_TIME, b_updateTime);
        //Actualizamos la fila
        db.update(Query.TABLE_ACCOUNT_BANK, values, Query.B_ID_BANK + "=?", new String[]{id_bank});

        //Cerramos conexión de BBDD
        db.close();
    }
    //Método para actualizar registros de tarjetas
    public void updateRecordCard(String id_card, String title, String username, String number, String dates, String cvc,
                                 String c_notes, String c_image, String c_recordTime, String c_updateTime, String s) throws Exception {
        ContentValues values = new ContentValues();
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        //Insertamos los datos
        values.put(Query.ID_CARD, id_card);
        values.put(Query.TITLE, title);
        values.put(Query.C_USERNAME, username);
        values.put(Query.C_NUMBER, number);
        values.put(Query.C_DATE, dates);
        values.put(Query.C_CVC, cvc);
        values.put(Query.C_NOTES, c_notes);
        values.put(Query.C_IMAGE, c_image);
        values.put(Query.RECORD_TIME, c_recordTime);
        values.put(Query.UPDATE_TIME, c_updateTime);
        //Actualizamos la fila
        db.update(Query.TABLE_CARD, values, Query.ID_CARD + "=?", new String[]{id_card});

        //Cerramos conexión de BBDD
        db.close();
    }

    //Método para buscar registros de aplicaciones web
    public ArrayList<Web> search_RecordsWeb(String consultation) throws Exception {
        ArrayList<Web> webList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Query.TABLE_ACCOUNT_WEB + " WHERE " + Query.TITLE +
                " LIKE '%" + consultation + "%'";

        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                @SuppressLint("Range") Web model_web = new Web(
                        "" + cursor.getInt(cursor.getColumnIndex(Query.W_ID)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.TITLE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.W_ACCOUNT)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.W_USERNAME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.W_PASSWORD)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.W_WEBSITES)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.W_NOTES)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.W_IMAGE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.RECORD_TIME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.UPDATE_TIME)));

                webList.add(model_web);
            }while (cursor.moveToNext());
        }
        db.close();
        return webList;
    }
    //Método para buscar registros de cuentas bancarias
    public ArrayList<Bank> search_RecordsBank(String consultation) throws Exception {
        ArrayList<Bank> bankList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Query.TABLE_ACCOUNT_BANK + " WHERE " + Query.TITLE +
                " LIKE '%" + consultation + "%'";

        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                @SuppressLint("Range") Bank model_bank = new Bank(
                        "" + cursor.getInt(cursor.getColumnIndex(Query.B_ID_BANK)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.TITLE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.B_BANK)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.TITLE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.B_ACCOUNT_BANK)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.B_NUMBER)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.B_WEBSITES)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.B_NOTES)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.B_IMAGE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.RECORD_TIME)),
                        "" + cursor.getString(cursor.getColumnIndex(Query.UPDATE_TIME)));

                bankList.add(model_bank);
            }while (cursor.moveToNext());
        }
        db.close();
        return bankList;
    }
    //Método para buscar registros de tarjetas
    public ArrayList<Card> search_RecordsCard(String consultation) throws Exception {
        ArrayList<Card> cardList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Query.TABLE_CARD + " WHERE " + Query.TITLE +
                " LIKE '%" + consultation + "%'";
        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                @SuppressLint("Range") Card model_card = new Card(
                        "" + cursor.getInt(cursor.getColumnIndex(Query.ID_CARD)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.TITLE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.C_USERNAME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.C_NUMBER)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.C_DATE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.C_CVC)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.C_NOTES)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.C_IMAGE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.RECORD_TIME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Query.UPDATE_TIME)));

                cardList.add(model_card);
            }while (cursor.moveToNext());
        }
        db.close();
        return cardList;
    }
    //Método para eliminar registros de de aplicaciones web
    public void deleteRecordWeb(String id) throws Exception {
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        db.delete(Query.TABLE_ACCOUNT_WEB, Query.W_ID + " = ?", new String[]{id});
        db.close();
    }
    //Método para eliminar registros de cuentas bancarias
    public void deleteRecordBank(String id) throws Exception {
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        db.delete(Query.TABLE_ACCOUNT_BANK, Query.B_ID_BANK+" = ?", new String[]{id});
        db.close();
    }
    //Método para eliminar registros de tarjetas
    public void deleteRecordCard(String id) throws Exception {
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        db.delete(Query.TABLE_CARD, Query.ID_CARD+" = ?", new String[]{id});
        db.close();
    }
    //Método para eliminar todos los registros de la BBDD
    public void deleteAllRecord() throws Exception {
        db = this.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        db.execSQL("DELETE FROM " + Query.TABLE_ACCOUNT_WEB);
        db.execSQL("DELETE FROM " + Query.TABLE_ACCOUNT_BANK);
        db.execSQL("DELETE FROM " + Query.TABLE_CARD);
        db.close();
    }
    }

