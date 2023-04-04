package com.javier.passlive.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.javier.passlive.BBDD.BBDD_Helper;
import com.javier.passlive.BBDD.Constans;
import com.javier.passlive.Model.Bank;

import java.util.ArrayList;

public class BankDAO {
    private static SQLiteDatabase db;

    public BankDAO(Context context) {
        BBDD_Helper dbHelper = new BBDD_Helper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public static long insertRecordBank(String title_bank, String bank, String account_bank,
                                        String number, String websites, String notes, String image, String recordTime, String updateTime) {
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

    //Método para actualizar registros de cuentas bancarias en BBDD
    public static void updateRecordBank(String id_bank, String title, String bank, String account_Bank, String number, String b_websites, String b_notes,
                                        String b_image, String b_recordTime, String b_updateTime){
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

    public static ArrayList<Bank> GetAllrecordBank(String orderby) {
        ArrayList<Bank> bankList = new ArrayList<>();
        //Creamos consulta para seleccionar el registro
        String selectQuerybank = "SELECT * FROM " + Constans.TABLE_ACCOUNT_BANK + " ORDER BY " + orderby;

        //Recorremos todos los registros de la BD para que se puedan añadir a la lista
        Cursor cursor = db.rawQuery(selectQuerybank, null);

        if (cursor.moveToFirst()) {
            do {
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
            } while (cursor.moveToNext());
        }
        db.close();
        return bankList;
    }

        public static ArrayList<Bank> search_RecordsBank(String consultation){
            ArrayList<Bank> bankList = new ArrayList<>();
            //Creamos consulta para seleccionar el registro
            String selectQuery = "SELECT * FROM " + Constans.TABLE_ACCOUNT_BANK + " WHERE " + Constans.B_TITLE_BANK +
                    " LIKE '%" + consultation + "%'";

            //Recorremos todos los registros de la BD para que se puedan añadir a la lista
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

    public static void deleteRecordBank(String id){
        db.delete(Constans.TABLE_CARD, Constans.ID_CARD+" = ?", new String[]{id});
        db.close();
    }
    }

