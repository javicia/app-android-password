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
import android.widget.ImageButton;
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

public class Web_Record extends AppCompatActivity {
    private static Helper instance;
    static public synchronized Helper getInstance(Context context){
        if (instance == null)
            instance = new Helper(context);
        return instance;
    }

    TextView D_Tittle, D_Account, D_Username, D_Websites,D_Note, D_RecordTime, D_UpdateTime;
    String id_record;
    Helper helper;
    ImageView D_Image;
    ImageView Img_copy_usename_web, Img_copy_password_web;
    Dialog dialog;
    EditText D_Password;
    ImageButton Img_web;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No permite captura de pantalla
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_web_record);
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
        String tittle_record = D_Tittle.getText().toString();
        assert actionBar != null;
        actionBar.setTitle(tittle_record);
        //Creamos la fecha de retroceso dentro del action Bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

         D_Image.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                Dialog_Visualize();
            }
        });

        Img_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url_web = D_Websites.getText().toString().trim();
                //Si contiene la url
                if(!url_web.equals("")){
                    openWeb(url_web);
                  //No contiene la url
                }else {
                    Toast.makeText(Web_Record.this, "No existe una url", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Img_copy_usename_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyTextUsename(v);
            }
        });

        Img_copy_password_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyTextPassword(v);
            }
        });

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
        D_Image = findViewById(R.id.D_image);

        dialog= new Dialog(this);
        Img_web = findViewById(R.id.Img_web);
        Img_copy_usename_web = findViewById(R.id.Img_copy_usename_web);
        Img_copy_password_web = findViewById(R.id.Img_copy_password_web);
    }

    //Método para visualizar información de los registros
    private void Registration_info() throws Exception {
        String query ="SELECT * FROM " + Query.TABLE_ACCOUNT_WEB + " WHERE " + Query.W_ID + " =\"" +
                id_record + "\"";

        SQLiteDatabase db = helper.getWritableDatabase(SQLKeyGenerator.getSecretKey().getEncoded());
        Cursor cursor = db.rawQuery(query, null);

//Buscar en la BBDD el registro seleccionado
        if (cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String id = "" +cursor.getInt(cursor.getColumnIndex(Query.W_ID));
                @SuppressLint("Range") String tittle = "" +cursor.getString(cursor.getColumnIndex(Query.W_TITTLE));
                @SuppressLint("Range") String account = "" +cursor.getString(cursor.getColumnIndex(Query.W_ACCOUNT));
                @SuppressLint("Range") String username = "" +cursor.getString(cursor.getColumnIndex(Query.W_USERNAME));
                @SuppressLint("Range") String password = "" +cursor.getString(cursor.getColumnIndex(Query.W_PASSWORD));
                @SuppressLint("Range") String websites = "" +cursor.getString(cursor.getColumnIndex(Query.W_WEBSITES));
                @SuppressLint("Range") String note = "" +cursor.getString(cursor.getColumnIndex(Query.W_NOTES));
                @SuppressLint("Range") String image = "" +cursor.getString(cursor.getColumnIndex(Query.W_IMAGE));
                @SuppressLint("Range") String recordTime = "" + cursor.getString(cursor.getColumnIndex(Query.W_RECORD_TIME));
                @SuppressLint("Range") String updateTime = "" + cursor.getString(cursor.getColumnIndex(Query.W_UPDATE_TIME));

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
                D_Password.setEnabled(false);
                D_Password.setBackgroundColor(Color.TRANSPARENT);
                D_Password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                D_Websites.setText(websites);
                D_Note.setText(note);
                D_RecordTime.setText(record_time);
                D_UpdateTime.setText(update_time);
                //Si la imagen no existe que se setee dentro
                if(image.equals("null")){
                    D_Image.setImageResource(R.drawable.logo_image);
                }else {
                    //Si la imagen existe pasamos la imagen
                    D_Image.setImageURI(Uri.parse(image));
                }

            }while (cursor.moveToNext());
        }
        db.close();
    }
    //Método para ampliar imagen
    private void Dialog_Visualize(){
    PhotoView Visualize_image;
    Button Btn_close_image;
    dialog.setContentView(R.layout.box_dialog_image_visualize);
    Visualize_image = dialog.findViewById(R.id.Visualize_image);
    Btn_close_image = dialog.findViewById(R.id.Btn_close_image);
    String query ="SELECT * FROM " + Query.TABLE_ACCOUNT_WEB + " WHERE " + Query.W_ID + " =\"" + id_record+ "\"";

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
    //Método para copiar el textView del Nombre de Usuario
    public void copyTextUsename(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", D_Username.getText().toString());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Texto copiado al portapapeles", Toast.LENGTH_SHORT).show();
        // Pegar el texto del portapapeles
        ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
        String clipboardText = item.getText().toString();
    }
    //Método para copiar el textView de la contraseña
    private void copyTextPassword(View v) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        //Copiar el texto en el portapapeles
        ClipData clipData = ClipData.newPlainText("text", D_Password.getText().toString());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Texto copiado al portapapeles", Toast.LENGTH_SHORT).show();
        // Pegar el texto del portapapeles
        ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
        String clipboardText = item.getText().toString();

    }
    //Método para abrir página web
    private void openWeb(String url_web) {
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