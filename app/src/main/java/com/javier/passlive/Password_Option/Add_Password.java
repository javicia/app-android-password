package com.javier.passlive.Password_Option;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.javier.passlive.R;

public class Add_Password extends AppCompatActivity {

    EditText EtTittle,EtAccount,EtUsername,EtPassword, EtWebsites,EtNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");
        initial_var();

    }
    private void initial_var(){
        EtTittle = findViewById(R.id.EtTittle);
        EtAccount = findViewById(R.id.EtAccount);
        EtUsername = findViewById(R.id.EtUsername);
        EtPassword = findViewById(R.id.EtPassword);
        EtWebsites = findViewById(R.id.EtWebsites);
        EtNote = findViewById(R.id.EtNote);
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
            Toast.makeText(this,"Guardar contraseña", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}