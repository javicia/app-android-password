package com.javier.passlive.Password_Option;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.javier.passlive.BBDD.BBDDHelper;
import com.javier.passlive.MainActivity;
import com.javier.passlive.R;

public class Add_Update_Record extends AppCompatActivity {

    EditText EtTittle,EtAccount,EtUsername,EtPassword, EtWebsites,EtNote;
    String id, tittle, account, username, password,websites,note, t_record, t_update;
    ImageView Image;
    Button Btn_Attach_image;

    private boolean EDITION_MODE= false;

    private BBDDHelper BDHelper;

    Uri imageUri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);
        ActionBar actionBar = getSupportActionBar();
        //assert actionBar !=null;
        //actionBar.setTitle("");
        Initial_Var();
        GetInformation();

        Btn_Attach_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePhoto();
            }
        });

    }

    private void Initial_Var(){
        EtTittle = findViewById(R.id.EtTittle);
        EtAccount = findViewById(R.id.EtAccount);
        EtUsername = findViewById(R.id.EtUsername);
        EtPassword = findViewById(R.id.EtPassword);
        EtWebsites = findViewById(R.id.EtWebsites);
        EtNote = findViewById(R.id.EtNote);

        Image = findViewById(R.id.Image);
        Btn_Attach_image = findViewById(R.id.Btn_Attach_image);

        BDHelper = new BBDDHelper(this);
    }

//Método para obtener información desde el adaptador
    private void GetInformation(){
        Intent intent = getIntent();
        EDITION_MODE = intent.getBooleanExtra("EDITION MODE", false);

        if(EDITION_MODE){
            //Verdadero
            id = intent.getStringExtra("ID");
            id = intent.getStringExtra("TITTLE");
            id = intent.getStringExtra("ACCOUNT");
            id = intent.getStringExtra("USERNAME");
            id = intent.getStringExtra("PASSWORD");
            id = intent.getStringExtra("WEBSITE");
            id = intent.getStringExtra("NOTE");
            id = intent.getStringExtra("T_RECORD");
            id = intent.getStringExtra("T_UPDATE");

            //Seteamos información en las vistas

            EtTittle.setText(tittle);
            EtAccount.setText(account);
            EtUsername.setText(username);
            EtPassword.setText(password);
            EtWebsites.setText(websites);
            EtNote.setText(note);

        }else{
            //falso, se agrega un nuevo registro

        }
    }
//Método para guardar password
    private void Add_Update_Record(){
//Obtener datos de entrada
        tittle= EtTittle.getText().toString().trim();
        account=EtAccount.getText().toString().trim();
        username=EtUsername.getText().toString().trim();
        password=EtPassword.getText().toString().trim();
        websites=EtWebsites.getText().toString().trim();
        note=EtNote.getText().toString().trim();

        if(EDITION_MODE){
            //Si es verdadero actualizamos el registro
            //Obtenemos el tiempo del dispositivo

            String current_time = ""+ System.currentTimeMillis();
            BDHelper.updateRecord(""+id, ""+tittle,""+account, ""+username,
                    ""+ password, ""+ websites, ""+ note,""+t_record,
                    ""+current_time);
            Toast.makeText(this,"Actualizado con éxito",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Add_Update_Record.this, MainActivity.class));
            finish();



        }else {
            //Si es falsa se agrega un nuevo registro

            if(!tittle.equals("")){
                //Obtenemos el tiempo del dispositovo
                String time = ""+System.currentTimeMillis();
                long id = BDHelper.insertRecord(
                        "" +tittle, "" + account, "" + username,
                        "" + password,"" + websites,   "" + note,
                        ""+ time, ""+ time);
                Toast.makeText(this, "Se ha guardado con éxito: "+id, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Add_Update_Record.this, MainActivity.class));
                finish();
            }
            else {
                EtTittle.setError("Campo Obligatorio");
                EtTittle.setFocusable(true);
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_password){
           Add_Update_Record();
        }
        return super.onOptionsItemSelected(item);
    }

    private void TakePhoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Nueva imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
    }
}