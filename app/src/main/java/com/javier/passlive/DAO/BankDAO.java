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





    public static void deleteRecordBank(String id){
        db.delete(Constans.TABLE_CARD, Constans.ID_CARD+" = ?", new String[]{id});
        db.close();
    }
    }



