package com.javier.passlive.Detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.javier.passlive.BBDD.BBDDHelper;
import com.javier.passlive.BBDD.Constans;
import com.javier.passlive.R;

import java.util.Calendar;
import java.util.Locale;

public class Detail_record extends AppCompatActivity {
    TextView D_Tittle, D_Account, D_Username, D_Password, D_Websites,D_Note, D_RecordTime, D_UpdateTime;
    String id_record;
    BBDDHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_record);

        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        id_record = intent.getStringExtra("Id_registro");
        Toast.makeText(this, "Id del registro " + id_record, Toast.LENGTH_SHORT).show();
        helper = new BBDDHelper(this);

        //Visualizar el título de un registro
        String tittle_record = D_Tittle.getText().toString();
        assert actionBar != null;
        actionBar.setTitle(tittle_record);
        //creamos la fecha de retroceso dentro del action Bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        Initialize_variables();
        Registration_info();
    }
    //Método para inicializar variables
    private void Initialize_variables(){
        D_Tittle = findViewById(R.id.D_Tittle);
        D_Account = findViewById(R.id.D_Account);
        D_Username = findViewById(R.id.D_Username);
        D_Password = findViewById(R.id.D_Password);
        D_Websites = findViewById(R.id.D_Websites);
        D_Note = findViewById(R.id.D_Note);
        D_RecordTime = findViewById(R.id.D_RecordTime);
        D_UpdateTime = findViewById(R.id.D_UpdateTime);
    }

//Método para visualizar información de los registros
    private void Registration_info(){
        String query ="SELECT * FROM " + Constans.TABLE_NAME + " WHERE " + Constans.C_ID + " =\"" +
                id_record + "\"";

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

//Buscar en la BBDD el registro seleccionado
        if (cursor.moveToFirst()){
            do{
    @SuppressLint("Range") String id = "" + cursor.getInt(cursor.getColumnIndex(Constans.C_ID));
                @SuppressLint("Range") String tittle = "" + cursor.getInt(cursor.getColumnIndex(Constans.C_TITTLE));
                @SuppressLint("Range") String account = "" + cursor.getInt(cursor.getColumnIndex(Constans.C_ACCOUNT));
                @SuppressLint("Range") String username = "" + cursor.getInt(cursor.getColumnIndex(Constans.C_USERNAME));
                @SuppressLint("Range") String password = "" + cursor.getInt(cursor.getColumnIndex(Constans.C_PASSWORD));
                @SuppressLint("Range") String websites = "" + cursor.getInt(cursor.getColumnIndex(Constans.C_WEBSITES));
                @SuppressLint("Range") String note = "" + cursor.getInt(cursor.getColumnIndex(Constans.C_NOTES));
                @SuppressLint("Range") String recordTime = "" + cursor.getInt(cursor.getColumnIndex(Constans.C_RECORD_TIME));
                @SuppressLint("Range") String updateTime = "" + cursor.getInt(cursor.getColumnIndex(Constans.C_UPDATE_TIME));

                //Convertimos tiempo a dia/mes/año

                //Tiempo registro

                Calendar calendar_recordTime = Calendar.getInstance(Locale.getDefault());
                calendar_recordTime.setTimeInMillis(Long.parseLong(recordTime));
                String record_time = "" + DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar_recordTime);

                //Tiempo de actualización

                Calendar calendar_updateTime = Calendar.getInstance(Locale.getDefault());
                calendar_updateTime.setTimeInMillis(Long.parseLong(updateTime));
                String update_time = "" + DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar_updateTime);

                //Setear información en las vistas

                D_Tittle.setText(tittle);
                D_Account.setText(account);
                D_Username.setText(username);
                D_Password.setText(password);
                D_Websites.setText(websites);
                D_Note.setText(note);
                D_RecordTime.setText(record_time);
                D_UpdateTime.setText(update_time);

            }while (cursor.moveToNext());
        }

        db.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //cuando presionamos la fecha de retroceso nos mandará a la actividad anterior
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}