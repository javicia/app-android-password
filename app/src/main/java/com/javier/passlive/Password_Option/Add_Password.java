package com.javier.passlive.Password_Option;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.javier.passlive.BBDD.BBDDHelper;
import com.javier.passlive.MainActivity;
import com.javier.passlive.R;

public class Add_Password extends AppCompatActivity {

    EditText EtTittle,EtAccount,EtUsername,EtPassword, EtWebsites,EtNote;
    String tittle, account, username, password,websites,note;

    private BBDDHelper BDHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);
        ActionBar actionBar = getSupportActionBar();
        //assert actionBar !=null;
        //actionBar.setTitle("");
        initial_var();

    }
    private void initial_var(){
        EtTittle = findViewById(R.id.EtTittle);
        EtAccount = findViewById(R.id.EtAccount);
        EtUsername = findViewById(R.id.EtUsername);
        EtPassword = findViewById(R.id.EtPassword);
        EtWebsites = findViewById(R.id.EtWebsites);
        EtNote = findViewById(R.id.EtNote);

        BDHelper = new BBDDHelper(this);
    }
//Método para guardar password
    private void save_password(){
//Obtener datos de entrada
        tittle= EtTittle.getText().toString().trim();
        account=EtAccount.getText().toString().trim();
        username=EtUsername.getText().toString().trim();
        password=EtPassword.getText().toString().trim();
        websites=EtWebsites.getText().toString().trim();
        note=EtNote.getText().toString().trim();

        if(!tittle.equals("")){
            //Obtenemos el tiempo del dispositovo
            String time = ""+System.currentTimeMillis();
            long id = BDHelper.insertRecord(
                     "" +tittle, "" + account, "" + username,
                    "" + password,"" + websites,   "" + note,
                    ""+ time, ""+ time);
            Toast.makeText(this, "Se ha guardado con éxito: "+id, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Add_Password.this, MainActivity.class));
            finish();
        }
        else {
            EtTittle.setError("Campo Obligatorio");
            EtTittle.setFocusable(true);
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
            save_password();
        }
        return super.onOptionsItemSelected(item);
    }
}