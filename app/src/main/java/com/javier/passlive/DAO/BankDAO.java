package com.javier.passlive.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.javier.passlive.BBDD.BBDD_Helper;

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







    }



