package com.javier.passlive.Password_Option;

import android.app.Activity;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
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
            tittle = intent.getStringExtra("TITTLE");
            account = intent.getStringExtra("ACCOUNT");
            username = intent.getStringExtra("USERNAME");
            password = intent.getStringExtra("PASSWORD");
            websites = intent.getStringExtra("WEBSITE");
            note = intent.getStringExtra("NOTE");
            imageUri = Uri.parse(intent.getStringExtra("IMAGE"));
            t_record = intent.getStringExtra("T_RECORD");
            t_update = intent.getStringExtra("T_UPDATE");

            //Seteamos información en las vistas

            EtTittle.setText(tittle);
            EtAccount.setText(account);
            EtUsername.setText(username);
            EtPassword.setText(password);
            EtWebsites.setText(websites);
            EtNote.setText(note);

            //Si la imagen no existe que se setee dentro del ImageView
            if (imageUri.toString().equals("null")) {
                Image.setImageResource(R.drawable.logo_image);
            }
            //Si la imagen existe
            else {

            }
        }else{
            //falso, se agrega un nuevo registro
        Image.setImageURI(imageUri);
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
            BDHelper.updateRecord("" + id, "" + tittle,"" + account, "" + username,
                    "" + password, ""+ websites, ""+ note,"" + imageUri,"" + t_record,
                    "" + current_time);
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
                        ""+ imageUri,""+ time, ""+ time);
                Toast.makeText(this, "Se ha guardado con éxito: ", Toast.LENGTH_SHORT).show();
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
//Método para realizar foto
    private void TakePhoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Nueva imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        camaraActivytyResultLauncher.launch(intent);
    }

private ActivityResultLauncher<Intent> camaraActivytyResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //Comprobamos si la fotografía ha sido guardada correctamente
                if (result.getResultCode() == Activity.RESULT_OK){
                    Image.setImageURI(imageUri);
                }else{
                    Toast.makeText(Add_Update_Record.this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        }
);
}