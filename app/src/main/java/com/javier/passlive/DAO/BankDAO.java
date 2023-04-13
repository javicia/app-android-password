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







    }



