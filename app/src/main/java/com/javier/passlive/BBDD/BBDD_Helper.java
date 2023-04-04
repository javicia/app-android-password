package com.javier.passlive.BBDD;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.javier.passlive.Model.Bank;
import com.javier.passlive.Model.Card;
import com.javier.passlive.Model.Web;
import java.util.ArrayList;

public class BBDD_Helper extends SQLiteOpenHelper {


    public BBDD_Helper(@Nullable Context context) {
        super(context, Constans.BD_NAME, null, Constans.BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(Constans.CREATE_TABLE_CATEGORY);
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



//Obtenemos el total de registros de la BBDD
    public int GetRecordNumber(){
        String query = "SELECT COUNT(*) FROM "
                + Constans.TABLE_ACCOUNT_WEB + ", "
                + Constans.TABLE_ACCOUNT_BANK + ", "
                + Constans.TABLE_CARD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int count = 0;
        if (cursor.moveToFirst()){
            count= cursor.getInt(0);
        }
        cursor.close();
        return count;
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
