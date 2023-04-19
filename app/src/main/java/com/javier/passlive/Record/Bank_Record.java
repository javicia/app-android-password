package com.javier.passlive.Record;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.net.Uri;
import android.text.InputType;

import com.github.chrisbanes.photoview.PhotoView;
import com.javier.passlive.BBDD.BBDD_Helper;
import com.javier.passlive.BBDD.Constans;
import com.javier.passlive.R;
import java.util.Calendar;
import java.util.Locale;

public class Bank_Record extends AppCompatActivity {

    TextView B_Title, B_Bank, B_Account_Name, B_Websites,B_Note, B_RecordTime, B_UpdateTime;
    String id_record;
    BBDD_Helper helper;
    ImageView B_Image;
    ImageView Img_copy_account_bank, Img_copy_number_bank;
    ImageButton Img_bank;
    Dialog dialog;
    EditText B_Number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No permite captura de pantalla
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_bank_record);

        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        id_record = intent.getStringExtra("Id_registro");
        Toast.makeText(this, "Id del registro " + id_record, Toast.LENGTH_SHORT).show();
        helper = new BBDD_Helper(this);

        Initialize_variables();
        Registration_info();

        //Visualizar el título de un registro
        String title_record = B_Title.getText().toString();
        assert actionBar != null;
        actionBar.setTitle(title_record);
        //Creamos la fecha de retroceso dentro del action Bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

     B_Image.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Dialog_Visualize();
         }
     });

        Img_bank.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String url_web = B_Websites.getText().toString().trim();
            //Si contiene la url
            if(!url_web.equals("")){
                openBank(url_web);
                //No contiene la url
            }else {
                Toast.makeText(Bank_Record.this, "No existe una url", Toast.LENGTH_SHORT).show();
            }
        }
    });
        Img_copy_account_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyTextAccountName(v);
            }
        });

        Img_copy_number_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyTextNumber(v);
            }
        });
    }
        private void Initialize_variables(){
            B_Title = findViewById(R.id.B_Title);
            B_Bank = findViewById(R.id.B_Bank);
            B_Account_Name = findViewById(R.id.B_Account_Name);
            B_Number = findViewById(R.id.B_Number);
            B_Websites = findViewById(R.id.B_Websites);
            B_Note = findViewById(R.id.B_Note);
            B_RecordTime = findViewById(R.id.B_RecordTime);
            B_UpdateTime = findViewById(R.id.B_UpdateTime);
            B_Image = findViewById(R.id.B_image);

            dialog= new Dialog(this);
            Img_bank = findViewById(R.id.Img_bank);
            Img_copy_account_bank = findViewById(R.id.Img_copy_account_bank);
            Img_copy_number_bank = findViewById(R.id.Img_copy_number_bank);

        }
    private void Dialog_Visualize(){
        PhotoView Visualize_image;
        Button Btn_close_image;
        dialog.setContentView(R.layout.box_dialog_image_visualize);
        Visualize_image = dialog.findViewById(R.id.Visualize_image);
        Btn_close_image = dialog.findViewById(R.id.Btn_close_image);
        String query ="SELECT * FROM " + Constans.TABLE_ACCOUNT_BANK + " WHERE " + Constans.B_ID_BANK + " =\"" + id_record+ "\"";

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        //Buscar en la BBDD el registro seleccionado
        if (cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String image = "" + cursor.getString(cursor.getColumnIndex(Constans.W_IMAGE));

                if(image.equals("null")){
                    Visualize_image.setImageResource(R.drawable.logo_image);
                }else {
                    Visualize_image.setImageResource(R.drawable.logo_image);
                }
            }while (cursor.moveToNext());
        }
        db.close();

        Btn_close_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(false);
    }

    private void Registration_info(){
        String query ="SELECT * FROM " + Constans.TABLE_ACCOUNT_BANK + " WHERE " + Constans.B_ID_BANK + " =\"" +
                id_record + "\"";

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

//Buscar en la BBDD el registro seleccionado
        if (cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String id = "" +cursor.getInt(cursor.getColumnIndex(Constans.B_ID_BANK));
                @SuppressLint("Range") String title_bank = "" +cursor.getString(cursor.getColumnIndex(Constans.B_TITLE_BANK));
                @SuppressLint("Range") String name_bank = "" +cursor.getString(cursor.getColumnIndex(Constans.B_BANK));
                @SuppressLint("Range") String account_name = "" +cursor.getString(cursor.getColumnIndex(Constans.B_ACCOUNT_BANK));
                @SuppressLint("Range") String number = "" +cursor.getString(cursor.getColumnIndex(Constans.B_NUMBER));
                @SuppressLint("Range") String websites = "" +cursor.getString(cursor.getColumnIndex(Constans.B_WEBSITES));
                @SuppressLint("Range") String note = "" +cursor.getString(cursor.getColumnIndex(Constans.B_NOTES));
                @SuppressLint("Range") String image = "" +cursor.getString(cursor.getColumnIndex(Constans.B_IMAGE));
                @SuppressLint("Range") String recordTime = "" + cursor.getString(cursor.getColumnIndex(Constans.B_RECORD_TIME));
                @SuppressLint("Range") String updateTime = "" + cursor.getString(cursor.getColumnIndex(Constans.B_UPDATE_TIME));

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

                B_Title.setText(title_bank);
                B_Bank.setText(name_bank);
                B_Account_Name.setText(account_name);
                B_Number.setText(number);
                B_Number.setEnabled(false);
                B_Number.setBackgroundColor(Color.TRANSPARENT);
                B_Number.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                B_Websites.setText(websites);
                B_Note.setText(note);
                B_RecordTime.setText(record_time);
                B_UpdateTime.setText(update_time);
                //Si la imagen no existe que se setee dentro
                if(image.equals("null")){
                    B_Image.setImageResource(R.drawable.logo_image);
                }else {
                    //Si la imagen existe pasamos la imagen
                    B_Image.setImageURI(Uri.parse(image));
                }

            }while (cursor.moveToNext());
        }
        db.close();
    }
    //Método para copiar el textView del Nombre de Usuario
    public void copyTextAccountName(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", B_Account_Name.getText().toString());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Texto copiado al portapapeles", Toast.LENGTH_SHORT).show();
    }
    //Método para copiar el textView de la contraseña
    private void copyTextNumber(View v) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", B_Number.getText().toString());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Texto copiado al portapapeles", Toast.LENGTH_SHORT).show();
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


