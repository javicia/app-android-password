package com.javier.passlive.Util;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
//import com.javier.passlive.DAO.BankDAO;
import com.javier.passlive.DAO.BankDAO;
import com.javier.passlive.MainActivity;
import com.javier.passlive.R;

public class Util_Bank extends AppCompatActivity {

    EditText Et_B_Title,Et_B_Bank_name, Et_B_Account_name,Et_B_Number_Bank,Et_B_Websites, Et_B_Note, Et_B_tRecord, Et_B_Update;
    String id, title, bank_name, account_name, number,websites,note, t_record, t_update;
    ImageView Image;
    Button Btn_B_Image;

    private boolean EDITION_MODE= false;

    private BBDD_Helper BDHelper;

    Uri imageUri = null;

    ImageView ImageView_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No permite captura de pantalla
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_add_bank);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar !=null;
        actionBar.setTitle("");

        Initial_Var();
        GetInformation();

        Btn_B_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si el permiso de cámara ha sido concedido entonces que se ejecute el método TakePhoto
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)==
                        PackageManager.PERMISSION_GRANTED){
                    TakePhoto();
                    //En caso contrario llamamos a la solicitur de permiso de cámara
                }else {
                    Camera_Permission_Request.launch(Manifest.permission.CAMERA);
                }

            }
        });

    }
    private void Initial_Var(){
        Et_B_Title = findViewById(R.id.Et_B_Title);
        Et_B_Bank_name = findViewById(R.id.Et_B_Bank_name);
        Et_B_Account_name = findViewById(R.id.Et_B_Account_name);
        Et_B_Number_Bank = findViewById(R.id.Et_B_Number_Bank);
        Et_B_Websites = findViewById(R.id.Et_B_Websites);
        Et_B_Note = findViewById(R.id.Et_B_Note);


        Image = findViewById(R.id.Image);
        Btn_B_Image = findViewById(R.id.Btn_B_Image);

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
            title = intent.getStringExtra("TITLE");
            bank_name = intent.getStringExtra("BANK_NAME");
            account_name = intent.getStringExtra("ACCOUNT_NAME");
            number = intent.getStringExtra("NUMBER");
            websites = intent.getStringExtra("WEBSITE");
            note = intent.getStringExtra("NOTE");
            imageUri = Uri.parse(intent.getStringExtra("IMAGE"));
            t_record = intent.getStringExtra("T_RECORD");
            t_update = intent.getStringExtra("T_UPDATE");

            //Seteamos información en las vistas
            Et_B_Title.setText(title);
            Et_B_Bank_name.setText(bank_name);
            Et_B_Account_name.setText(account_name);
            Et_B_Number_Bank.setText(number);
            Et_B_Websites.setText(websites);
            Et_B_Note.setText(note);
            Et_B_tRecord.setText(t_record);

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
                    Toast.makeText(Util_Bank.this, "Imagen eliminada", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Si es falso se agrega un registro
        }
    }
    //Método para guardar password
    private void Add_Update_Record_Bank(){
//Obtener datos de entrada
        title= Et_B_Title.getText().toString().trim();
        bank_name=Et_B_Bank_name.getText().toString().trim();
        account_name=Et_B_Account_name.getText().toString().trim();
        number=Et_B_Number_Bank.getText().toString().trim();
        websites=Et_B_Websites.getText().toString().trim();
        note=Et_B_Note.getText().toString().trim();

        if(EDITION_MODE){
            //Si es verdadero actualizamos el registro
            //Obtenemos el tiempo del dispositivo
            String current_time = ""+ System.currentTimeMillis();
            BDHelper.updateRecordBank(
                    "" + id,
                    ""+ title,
                    "" + bank_name,
                    "" + account_name,
                    "" + number,
                    ""+ websites,
                    ""+ note,
                    "" + imageUri,
                    "" + t_record,
                    "" + current_time);
            Toast.makeText(this,"Actualizado con éxito",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Util_Bank.this, MainActivity.class));
            finish();
        }else {
            //Si es falsa se agrega un nuevo registro

            if(!title.equals("")){
                //Obtenemos el tiempo del dispositovo
                String time = ""+System.currentTimeMillis();
                long id = BDHelper.insertRecordBank (
                        "" +title,
                        "" + bank_name,
                        "" + account_name,
                        "" + number,
                        "" + websites,
                        "" + note,
                        ""+ imageUri,
                        ""+ time,
                        ""+ time
                );
                Toast.makeText(this, "Se ha guardado con éxito: ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Util_Bank.this, MainActivity.class));
                finish();
            } else {
                Et_B_Title.setError("Campo Obligatorio");
                Et_B_Title.setFocusable(true);
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
            Add_Update_Record_Bank();
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
                        Toast.makeText(Util_Bank.this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
    //Método que permite comprobar si el permiso ha sido concedido por el usuario
    private ActivityResultLauncher<String> Camera_Permission_Request = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), Grant_permission ->{
                if(Grant_permission){
                    TakePhoto();
                }else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            });
}


