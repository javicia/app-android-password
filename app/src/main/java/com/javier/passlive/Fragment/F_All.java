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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.javier.passlive.Adapter.Adapter_bank;
import com.javier.passlive.Adapter.Adapter_card;
import com.javier.passlive.Adapter.Adapter_web;
import com.javier.passlive.BBDD.BBDD_Helper;
import com.javier.passlive.BBDD.Constans;
import com.javier.passlive.Category.Category;
import com.javier.passlive.Model.Bank;
import com.javier.passlive.Model.Card;
import com.javier.passlive.Model.Web;
import com.javier.passlive.R;

import java.util.ArrayList;
import java.util.List;


public class F_All extends Fragment {
    String category = Constans.ACCOUNT_WEB + Constans.ACCOUNT_BANK + Constans.CARD + " CATE";
    String newOrder= Constans.W_RECORD_TIME + Constans.B_RECORD_TIME + Constans.C_RECORD_TIME + " DESC";
    String sortPast= Constans.W_RECORD_TIME + " ASC";
    String orderTittleAsc = Constans.W_TITTLE + " ASC";
    String orderTittleDesc = Constans.W_TITTLE + " DESC";
    String statusOrder = newOrder;
   Dialog dialog, dialog_order;
 BBDD_Helper helper;
    RecyclerView RView_record;
    FloatingActionButton btnadd_password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //No permite captura de pantalla
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        RView_record = view.findViewById(R.id.RView_record);
        btnadd_password= view.findViewById(R.id.btnadd_password);
        helper = new BBDD_Helper(getActivity());
        dialog = new Dialog(getActivity());
        dialog_order = new Dialog(getActivity());

//Listar registros
        LoadRecord(newOrder);
                btnadd_password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(getActivity(), Category.class);
                        startActivity(intent);
                    }
                });
        return view;
    }
//Método para cargar registros
    private void LoadRecord(String orderby) {
        String currentRecordType = orderby;
        List<Object> recordList = new ArrayList<>();
        switch (currentRecordType){
            case "web":
                recordList.addAll(helper.GetAllrecordWeb(orderby));
                Adapter_web adapter_web= new Adapter_web(getActivity(),recordList);
                RView_record.setAdapter(adapter_web);
                break;
            case "bank":
                recordList.addAll(helper.GetAllrecordBank(orderby));
                Adapter_bank adapter_bank = new Adapter_bank(getActivity(),recordList);
                RView_record.setAdapter(adapter_bank);
                break;
            case "card":
                recordList.addAll(helper.GetAllrecordCard(orderby));
                Adapter_card adapter_card = new Adapter_card(getActivity(),recordList);
                RView_record.setAdapter(adapter_card);
                break;
        }

    }
    //Buscar registro en base de datos
        private void Record_seach(String consultation){
            List<Object> seachResults = new ArrayList<>();
            seachResults.addAll(helper.search_RecordsWeb(consultation));
            seachResults.addAll(helper.search_RecordsBank(consultation));
            seachResults.addAll(helper.search_RecordsCard(consultation));

            RecyclerView.Adapter adapter = null;
            if (!seachResults.isEmpty()) {
                //Si se producen resultados, crear el adaptador cortrespontiente
                if (seachResults.get(0) instanceof Web) {
                    adapter = new Adapter_web(getActivity(), seachResults);
                } else if (seachResults.get(0) instanceof Bank) {
                    adapter = new Adapter_bank(getActivity(), seachResults);
                } else if (seachResults.get(0) instanceof Card) {
                    adapter = new Adapter_card(getActivity(), seachResults);
                }
            }
            if (adapter !=null){
                //Si se creó el adaptador, establecerlo en el RecyclerView
                RView_record.setAdapter(adapter);
            }else {
                //Si no se encuentra resultado
                Toast.makeText(getActivity(), "No se encuentran resultados", Toast.LENGTH_SHORT).show();
            }

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
        if(id == R.id.menu_Order_record){
            Order_Record();
            return true;
        }
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
//Para refrescar la lista cuando estemos en el fragmento
    @Override
    public void onResume() {
        LoadRecord(statusOrder);
        super.onResume();
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

    private void Order_Record(){
        Button Btn_Category, Btn_New, Btn_Past, Btn_Asc, Btn_Desc;
        //Conexión hacia el diseño
        dialog_order.setContentView(R.layout.box_dialog_order_record);

        Btn_Category = dialog_order.findViewById(R.id.Btn_Category);
        Btn_New = dialog_order.findViewById(R.id.Btn_New);
        Btn_Past = dialog_order.findViewById(R.id.Btn_Past);
        Btn_Asc = dialog_order.findViewById(R.id.Btn_Asc);
        Btn_Desc = dialog_order.findViewById(R.id.Btn_Desc);

        Btn_New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadRecord(newOrder);
                dialog_order.dismiss();
            }
        });

        Btn_Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadRecord(sortPast);
                dialog_order.dismiss();
            }
        });
        Btn_Past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadRecord(sortPast);
                dialog_order.dismiss();
            }
        });
        Btn_Asc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadRecord(orderTittleAsc);
                dialog_order.dismiss();
            }
        });
        Btn_Desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadRecord(orderTittleDesc);
                dialog_order.dismiss();
            }
        });
        dialog_order.show();
        dialog_order.setCancelable(true);
    }
}