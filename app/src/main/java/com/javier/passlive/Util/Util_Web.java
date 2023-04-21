package com.javier.passlive.Util;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.javier.passlive.BBDD.BBDD_Helper;
import com.javier.passlive.DAO.WebDAO;
import com.javier.passlive.MainActivity;
import com.javier.passlive.R;

public class Util_Web extends AppCompatActivity {

    EditText EtTittle,EtAccount,EtUsername,EtPassword, EtWebsites,EtNote;
    String id, tittle, account, username, password,websites,note, t_record, t_update;
    ImageView Image;
    Button Btn_W_Image;

    private boolean EDITION_MODE= false;

    private BBDD_Helper BDHelper;

    Uri imageUri = null;

    ImageView ImageView_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No permite captura de pantalla
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_add_web);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        Initial_Var();
        GetInformation();

        Btn_W_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Comprobamos si tenemos permiso para usar la cámara
                if (ContextCompat.checkSelfPermission(Util_Web.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    // Si tenemos permiso, llamamos al método TakePhoto() para tomar la foto
                    takePhoto();
                } else {
                    // Si no tenemos permiso, pedimos al usuario que lo conceda
                    Camera_Permission_Request.launch(Manifest.permission.CAMERA);
                }
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
        Btn_W_Image = findViewById(R.id.Btn_W_Image);

        ImageView_delete = findViewById(R.id.ImageView_delete);

        BDHelper = new BBDD_Helper(this);

    }
//Método para obtener información desde el adaptador
    private void GetInformation() {
        Intent intent = getIntent();
        EDITION_MODE = intent.getBooleanExtra("EDITION MODE", false);

        if (EDITION_MODE) {
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
                ImageView_delete.setVisibility(View.VISIBLE);
            }
            //Si la imagen existe
            else {
                Image.setImageURI(imageUri);
                ImageView_delete.setVisibility(View.VISIBLE);
            }

            ImageView_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageUri = null;
                    Image.setImageResource(R.drawable.logo_image);
                    Toast.makeText(Util_Web.this, "Imagen eliminada", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
        // Si es falso se agrega un registro
        }
    }
//Método para guardar password
    private void Add_Update_Record_Web(){
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
           BDHelper.updateRecordWeb(
                   "" + id,
                   "" + tittle,
                   "" + account,
                   "" + username,
                    "" + password,
                   ""+ websites,
                   ""+ note,
                   "" + imageUri,
                   "" + t_record,
                    "" + current_time
           );
            Toast.makeText(this,"Actualizado con éxito",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Util_Web.this, MainActivity.class));
            finish();
        }
        else {
            //Si es falsa se agrega un nuevo registro
            if(!tittle.equals("")){
                //Obtenemos el tiempo del dispositovo
                String time = ""+System.currentTimeMillis();
                long id = BDHelper.insertRecordWeb(
                        "" +tittle,
                        "" + account,
                        "" + username,
                        "" + password,
                        "" + websites,
                        "" + note,
                        ""+ imageUri,
                        ""+ time,
                        ""+ time
                );
                Toast.makeText(this, "Se ha guardado con éxito: ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Util_Web.this, MainActivity.class));
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
           Add_Update_Record_Web();
        }
        return super.onOptionsItemSelected(item);
    }


    private void takePhoto() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Pedir permiso al usuario para acceder a la cámara
            Camera_Permission_Request.launch(Manifest.permission.CAMERA);
        } else {
            // El permiso ya ha sido concedido, tomar la foto
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "Nueva imagen");
            values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraActivityResultLauncher.launch(intent);
        }
    }

    // Resultado de la acción de tomar una foto
    private ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Mostrar la imagen en un ImageView
                    Image.setImageURI(imageUri);
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "Acción cancelada por el usuario", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error al tomar la foto", Toast.LENGTH_SHORT).show();
                }
            }
    );


    // Pedir permiso al usuario para acceder a la cámara
    private ActivityResultLauncher<String> Camera_Permission_Request = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    takePhoto();
                } else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            }
    );



}

