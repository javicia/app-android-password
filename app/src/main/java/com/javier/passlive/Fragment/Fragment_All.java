package com.javier.passlive.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.javier.passlive.Adapter.Adapter_password;
import com.javier.passlive.BBDD.BBDDHelper;
import com.javier.passlive.BBDD.Constans;
import com.javier.passlive.Password_Option.Add_Password;
import com.javier.passlive.R;


public class Fragment_All extends Fragment {

   Dialog dialog;
 BBDDHelper helper;
    RecyclerView RView_record;
    FloatingActionButton btnadd_password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        RView_record = view.findViewById(R.id.RView_record);
        btnadd_password= view.findViewById(R.id.btnadd_password);
        helper = new BBDDHelper(getActivity());
        dialog = new Dialog(getActivity());

//Listar registros
        LoadRecord();
                btnadd_password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), Add_Password.class));

                        //Intent intent = new Intent(getActivity(), Add_Password.class);
                        //startActivity(intent);
                    }
                });
        return view;
    }
//Método para cargar registros
    private void LoadRecord() {
        Adapter_password adapter_password = new Adapter_password(getActivity(), helper.GetAllrecord(
                Constans.C_TITTLE + " ASC"));
        RView_record.setAdapter(adapter_password);
    }
    //Buscar registro en base de datos
        private void Record_seach(String consultation){
        Adapter_password adapter_password = new Adapter_password(getActivity(),
                helper.search_Records(consultation));

        RView_record.setAdapter(adapter_password);
 }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_all, menu);

        MenuItem item = menu.findItem(R.id.menu_Record_seach);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //Método que se ejecuta cuando los usuarios presionan el botón de búsqueda del teclado
            @Override
            public boolean onQueryTextSubmit(String query) {
                Record_seach(query);
                return true;
            }
            //Método que permite realizar la búsqueda mientras el usuario está escribiendo en el teclado
            @Override
            public boolean onQueryTextChange(String newText) {
                Record_seach(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_Record_number){
            Display_total_records();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    //Método para visualizar total de registros
    public void Display_total_records(){
        TextView Total;
        Button BtnFully_Undertood;

        dialog.setContentView(R.layout.total_records_dialog);
        Total = dialog.findViewById(R.id.Total);
        BtnFully_Undertood = dialog.findViewById(R.id.BtnFully_Undertood);

        //Obtenemos valor entero de registro

        int total = helper.GetRecordNumber();

        //Convertir a String el número total de registros

        String total_String = String.valueOf(total);
        Total.setText(total_String);

        BtnFully_Undertood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cuando presionamos boton se cierra el cuadro de dialogo
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(false);
    }
}