package com.javier.passlive;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.javier.passlive.Fragment.F_About;
import com.javier.passlive.Fragment.F_All;
import com.javier.passlive.Fragment.F_Setting;
import com.javier.passlive.Login.Login_user;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    boolean double_tap_exit= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        //Fragmento al iniciar la aplicación

        if (savedInstanceState == null){
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
            new F_All()).commit();
    navigationView.setCheckedItem(R.id.Option_all);
}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Option_all){
        getSupportFragmentManager().beginTransaction().
        replace(R.id.fragment_container, new F_All()).commit();
        }
        if (id == R.id.Option_setting){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container, new F_Setting()).commit();
        }
        if (id == R.id.option_about){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container, new F_About()).commit();
        }
        if (id == R.id.Option_exit){
            CloseSession();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void CloseSession() {
        startActivity(new Intent(MainActivity.this, Login_user.class));
        Toast.makeText(this, "Cerraste sesión", Toast.LENGTH_SHORT).show();
        finish();
    }
    @Override
    public void onBackPressed() {
        //Comprobar que se ha presionado 2 veces
        if (double_tap_exit){
            super.onBackPressed();
            Toast.makeText(this, "Saliste con éxito de la aplicación", Toast.LENGTH_LONG).show();
        return;
        }
        //Cuando presionamos una vez el botón para volver

        this.double_tap_exit = true;
        Toast.makeText(this, "Presiona 2 veces si deseas salir de la aplicación", Toast.LENGTH_LONG).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
    @Override
    public void run() {
  double_tap_exit = false;
    }
},2000);
    }
}