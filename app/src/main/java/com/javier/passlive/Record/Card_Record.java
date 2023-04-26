package com.javier.passlive.Record;

import static com.javier.passlive.BBDD.Helper.PASS_PHARSE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;
import com.javier.passlive.BBDD.Helper;
import com.javier.passlive.BBDD.Query;
import com.javier.passlive.BBDD.SQLKeyGenerator;
import com.javier.passlive.R;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.Calendar;
import java.util.Locale;

public class Card_Record extends AppCompatActivity {
    private static Helper instance;
    static public synchronized Helper getInstance(Context context){
        if (instance == null)
            instance = new Helper(context);
        return instance;
    }
    TextView C_Title, C_Name, C_Date, C_Note, C_RecordTime, C_UpdateTime;
    String id_record;
    Helper helper;
    ImageView C_Image;
    ImageView Img_copy_number_card, Img_copy_number_cvc;
    Dialog dialog;
    EditText C_Number,C_CVC ;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //No permite captura de pantalla
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
            setContentView(R.layout.activity_card_record);

            ActionBar actionBar = getSupportActionBar();

            Intent intent = getIntent();
            id_record = intent.getStringExtra("Id_registro");
            Toast.makeText(this, "Id del registro " + id_record, Toast.LENGTH_SHORT).show();
            helper = new Helper(this);

            Initialize_variables();
        try {
            Registration_info();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Visualizar el título de un registro
            String title_record = C_Title.getText().toString();
            assert actionBar != null;
            actionBar.setTitle(title_record);
            //Creamos la fecha de retroceso dentro del action Bar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            C_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog_Visualize();
                }
            });

        Img_copy_number_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                copyTextNumber(v);
            }
        });

        Img_copy_number_cvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                copyTextCVC(v);
            }
        });
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
            Img_copy_number_card = findViewById(R.id.Img_copy_number_card);
            Img_copy_number_cvc = findViewById(R.id.Img_copy_number_cvc);
        }
        //Método para visualizar registros en el Recycleview
        private void Registration_info() throws Exception {
            String query ="SELECT * FROM " + Query.TABLE_CARD+ " WHERE " + Query.ID_CARD + " =\"" +
                    id_record + "\"";

            SQLiteDatabase db = helper.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
            Cursor cursor = db.rawQuery(query, null);

            //Buscar en la BBDD el registro seleccionado
            if (cursor.moveToFirst()){
                do{
                    @SuppressLint("Range") String id = "" +cursor.getInt(cursor.getColumnIndex(Query.ID_CARD));
                    @SuppressLint("Range") String title_card = "" +cursor.getString(cursor.getColumnIndex(Query.C_TITLE_CARD));
                    @SuppressLint("Range") String name_card = "" +cursor.getString(cursor.getColumnIndex(Query.C_USERNAME));
                    @SuppressLint("Range") String number = "" +cursor.getString(cursor.getColumnIndex(Query.C_NUMBER));
                    @SuppressLint("Range") String date = "" +cursor.getString(cursor.getColumnIndex(Query.C_DATE));
                    @SuppressLint("Range") String cvc = "" +cursor.getString(cursor.getColumnIndex(Query.C_CVC));
                    @SuppressLint("Range") String note = "" +cursor.getString(cursor.getColumnIndex(Query.C_NOTES));
                    @SuppressLint("Range") String image = "" +cursor.getString(cursor.getColumnIndex(Query.C_IMAGE));
                    @SuppressLint("Range") String recordTime = "" + cursor.getString(cursor.getColumnIndex(Query.C_RECORD_TIME));
                    @SuppressLint("Range") String updateTime = "" + cursor.getString(cursor.getColumnIndex(Query.C_UPDATE_TIME));

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

                    C_Title.setText(title_card);
                    C_Name.setText(name_card);
                    C_Number.setText(number);
                    C_Number.setBackgroundColor(Color.TRANSPARENT);
                    C_Number.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    C_Date.setText(date);
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
        //Método para visualizar imagen
        private void Dialog_Visualize(){
        PhotoView Visualize_image;
        Button Btn_close_image;
        dialog.setContentView(R.layout.box_dialog_image_visualize);
        Visualize_image = dialog.findViewById(R.id.Visualize_image);
        Btn_close_image = dialog.findViewById(R.id.Btn_close_image);
        String query ="SELECT * FROM " + Query.TABLE_CARD + " WHERE " + Query.ID_CARD + " =\"" + id_record+ "\"";

        SQLiteDatabase db = instance.getWritableDatabase(PASS_PHARSE);
        Cursor cursor = db.rawQuery(query,null);

        //Buscar en la BBDD el registro seleccionado
        if (cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String image = "" + cursor.getString(cursor.getColumnIndex(Query.W_IMAGE));

                if(image.equals("null")){
                    Visualize_image.setImageResource(R.drawable.logo_image);
                }else {
                    Visualize_image.setImageURI(Uri.parse(image));
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

        //Método para copiar el número de tarjeta
         public void copyTextNumber(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", C_Number.getText().toString());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Texto copiado al portapapeles", Toast.LENGTH_SHORT).show();
    }
        //Método para copiar código de seguridad
        public void copyTextCVC(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", C_CVC.getText().toString());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Texto copiado al portapapeles", Toast.LENGTH_SHORT).show();
    }
        @Override
        public boolean onSupportNavigateUp() {
            //cuando presionamos la fecha de retroceso nos mandará a la actividad anterior
            onBackPressed();
            return super.onSupportNavigateUp();
        }
}


