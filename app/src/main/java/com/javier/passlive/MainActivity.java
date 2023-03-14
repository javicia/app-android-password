package com.javier.passlive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.javier.passlive.Fragment.Fragment_All;
import com.javier.passlive.Fragment.Fragment_Setting;

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

        //Fragmento de inicio al iniciar la aplicación

        if (savedInstanceState == null){
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
            new Fragment_All());
}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.option_all){
        getSupportFragmentManager().beginTransaction().
        replace(R.id.fragment_container, new Fragment_All()).commit();
        }
        if (id == R.id.option_setting){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container, new Fragment_Setting()).commit();
        }
        if (id == R.id.option_exit){
            Toast.makeText(this,"Cerraste la sesión", Toast.LENGTH_LONG).show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
},1500);
        super.onBackPressed();
    }
}