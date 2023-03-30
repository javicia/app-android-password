package com.javier.passlive.Detail;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.javier.passlive.BBDD.BBDD;
import com.javier.passlive.R;

public class Bank_Detail_Record {

    String B_Title, B_Bank_, B_Account_Name, B_number, B_Websites,B_Note, B_Image, B_RecordTime, B_UpdateTime;

    String id_record;
    BBDD helper;
    ImageView D_Image;
    Dialog dialog;
    EditText B_BANK;
    ImageButton Img_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No permite captura de pantalla
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_web_detail_record);

        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        id_record = intent.getStringExtra("Id_registro");
        Toast.makeText(this, "Id del registro " + id_record, Toast.LENGTH_SHORT).show();
        helper = new BBDD(this);
        Initialize_variables();
        Registration_info();

        //Visualizar el título de un registro
        String tittle_record = B_Title.getText().toString();
        assert actionBar != null;
        actionBar.setTitle(tittle_record);
        //Creamos la fecha de retroceso dentro del action Bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        private void Initialize_variables(){
            B_Title = findViewById(R.id.B_Title);
            B_BANK = findViewById(R.id.B_Bank);
            B_Account_Name = findViewById(R.id.B_Account_Name);
            B_number = findViewById(R.id.D_Password);
            B_Websites = findViewById(R.id.D_Websites);
            B_Note = findViewById(R.id.D_Note);
            B_RecordTime = findViewById(R.id.D_RecordTime);
            B_UpdateTime = findViewById(R.id.D_UpdateTime);
            B_Image = findViewById(R.id.D_image);

            dialog= new Dialog(this);
            Img_web = findViewById(R.id.Img_web);
        }
}
