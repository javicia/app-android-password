package com.javier.passlive.Detail;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.javier.passlive.BBDD.BBDD_Helper;
import com.javier.passlive.BBDD.Constans;
import com.javier.passlive.R;

import java.util.Calendar;
import java.util.Locale;

public class Card_Detail_Record extends AppCompatActivity {

    TextView C_Title, C_Name, C_Date, C_Note, C_RecordTime, C_UpdateTime;
    String id_record;
    BBDD_Helper helper;
    ImageView C_Image;
    Dialog dialog;
    EditText C_Number,C_CVC ;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //No permite captura de pantalla
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
            setContentView(R.layout.activity_card_detail_record);

            ActionBar actionBar = getSupportActionBar();

            Intent intent = getIntent();
            id_record = intent.getStringExtra("Id_registro");
            Toast.makeText(this, "Id del registro " + id_record, Toast.LENGTH_SHORT).show();
            helper = new BBDD_Helper(this);
            Initialize_variables();
            Registration_info();

            //Visualizar el título de un registro
            String title_record = C_Title.getText().toString();
            assert actionBar != null;
            actionBar.setTitle(title_record);
            //Creamos la fecha de retroceso dentro del action Bar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        private void Initialize_variables(){
            C_Title = findViewById(R.id.C_Title);
            C_Name = findViewById(R.id.C_Name);
            C_Number = findViewById(R.id.C_Number);
            C_Date = findViewById(R.id.C_Date);
            C_CVC = findViewById(R.id.C_CVC);
            C_Note = findViewById(R.id.C_Note);
            C_RecordTime = findViewById(R.id.C_RecordTime);
            C_UpdateTime = findViewById(R.id.C_UpdateTime);
            C_Image = findViewById(R.id.C_image);

            dialog= new Dialog(this);
        }
        private void Registration_info(){
            String query ="SELECT * FROM " + Constans.TABLE_CARD+ " WHERE " + Constans.ID_CARD + " =\"" +
                    id_record + "\"";

            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

//Buscar en la BBDD el registro seleccionado
            if (cursor.moveToFirst()){
                do{
                    @SuppressLint("Range") String id = "" +cursor.getInt(cursor.getColumnIndex(Constans.ID_CARD));
                    @SuppressLint("Range") String title_card = "" +cursor.getString(cursor.getColumnIndex(Constans.C_TITLE_CARD));
                    @SuppressLint("Range") String name_card = "" +cursor.getString(cursor.getColumnIndex(Constans.C_USERNAME));
                    @SuppressLint("Range") String number = "" +cursor.getString(cursor.getColumnIndex(Constans.C_NUMBER));
                    @SuppressLint("Range") String date = "" +cursor.getString(cursor.getColumnIndex(Constans.C_DATE));
                    @SuppressLint("Range") String cvc = "" +cursor.getString(cursor.getColumnIndex(Constans.C_CVC));
                    @SuppressLint("Range") String note = "" +cursor.getString(cursor.getColumnIndex(Constans.C_NOTES));
                    @SuppressLint("Range") String image = "" +cursor.getString(cursor.getColumnIndex(Constans.C_IMAGE));
                    @SuppressLint("Range") String recordTime = "" + cursor.getString(cursor.getColumnIndex(Constans.C_RECORD_TIME));
                    @SuppressLint("Range") String updateTime = "" + cursor.getString(cursor.getColumnIndex(Constans.C_UPDATE_TIME));

                    //Convertimos tiempo a dia/mes/año
                    //Tiempo registro
                    Calendar calendar_recordTime = Calendar.getInstance(Locale.getDefault());
                    calendar_recordTime.setTimeInMillis(Long.parseLong(recordTime));
                    String record_time = "" + DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar_recordTime);

                    //Tiempo de actualización
                    Calendar calendar_updateTime = Calendar.getInstance(Locale.getDefault());
                    calendar_updateTime.setTimeInMillis(Long.parseLong(updateTime));
                    String update_time = "" + DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar_updateTime);

                    //Fecha de caducidad tarjeta
                    Calendar calendar_date = Calendar.getInstance(Locale.getDefault());
                    calendar_date.setTimeInMillis(Long.parseLong(date));
                    int month = calendar_date.get(Calendar.MONTH) + 1; // Sumar 1 porque Calendar.MONTH comienza en 0
                    int year = calendar_date.get(Calendar.YEAR);
                    String expiration_date = String.format("%02d/%04d", month, year);

                    //Setear información en las vistas

                    C_Title.setText(title_card);
                    C_Name.setText(name_card);
                    C_Number.setText(number);
                    C_Date.setText(expiration_date);
                    C_CVC.setText(cvc);
                    C_CVC.setBackgroundColor(Color.TRANSPARENT);
                    C_CVC.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    C_Note.setText(note);
                    C_RecordTime.setText(record_time);
                    C_UpdateTime.setText(update_time);
                    //Si la imagen no existe que se setee dentro
                    if(image.equals("null")){
                        C_Image.setImageResource(R.drawable.logo_image);
                    }else {
                        //Si la imagen existe pasamos la imagen
                        C_Image.setImageURI(Uri.parse(image));
                    }

                }while (cursor.moveToNext());
            }
            db.close();
        }

        //Método para abrir página web
        private void openBank(String url_web) {
            Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" +url_web));
            startActivity(navigation);
        }
        @Override
        public boolean onSupportNavigateUp() {
            //cuando presionamos la fecha de retroceso nos mandará a la actividad anterior
            onBackPressed();
            return super.onSupportNavigateUp();
        }
}
