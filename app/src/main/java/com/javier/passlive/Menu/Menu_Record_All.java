package com.javier.passlive.Menu;

import android.annotation.SuppressLint;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.javier.passlive.Adapter.Adapter_bank;
import com.javier.passlive.Adapter.Adapter_card;
import com.javier.passlive.Adapter.Adapter_web;
import com.javier.passlive.Adapter.RecordAdapter;
import com.javier.passlive.BBDD.Helper;
import com.javier.passlive.BBDD.Query;
import com.javier.passlive.Category.Category;
import com.javier.passlive.Model.Bank;
import com.javier.passlive.Model.Card;
import com.javier.passlive.Model.Record;
import com.javier.passlive.Model.Web;
import com.javier.passlive.R;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class Menu_Record_All extends Fragment {

    Dialog dialog, dialog_order, dialog_category;
    Helper helper;
    FloatingActionButton btn_add_record;
    RecyclerView RView_record;
    String orderTitleAsc = Query.TITLE + " ASC";
    String orderTitleDesc = Query.TITLE + " DESC";
    String newOrder = Query.RECORD_TIME + " DESC";
    String sortPast = Query.RECORD_TIME + " ASC";
    String statusOrder = orderTitleAsc;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //No permite captura de pantalla
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        View view = inflater.inflate(R.layout.item_record, container, false);

        SQLiteDatabase.loadLibs(getActivity());


        RView_record = view.findViewById(R.id.RView_record);
        btn_add_record = view.findViewById(R.id.btn_add_record);
        helper = new Helper(getActivity());
        dialog = new Dialog(getActivity());
        dialog_order = new Dialog(getActivity());
        dialog_category = new Dialog(getActivity());


//Listar registros
        try {
            loadRecord(orderTitleAsc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        btn_add_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Category.class);
                startActivity(intent);
            }
        });
        return view;
    }

    //Método para cargar registros
    private void loadRecord(String orderby) throws Exception {
        statusOrder = orderby;
        ArrayList<Object> allRecords = new ArrayList<>();

        // Obtener todos los registros de las tres fuentes y agregárlos al arraylist allRecords
        ArrayList<Web> webRecords = helper.GetAllrecordWeb(orderby);
        allRecords.addAll(webRecords);
        ArrayList<Bank> bankRecords = helper.GetAllrecordBank(orderby);
        allRecords.addAll(bankRecords);
        ArrayList<Card> cardRecords = helper.GetAllrecordCard(orderby);
        allRecords.addAll(cardRecords);


        if(orderby.equals(orderTitleAsc)) {
                Collections.sort(allRecords, new Comparator<Object>() {
                    public int compare(Object o1, Object o2) {
                        String titulo1 = ((Record) o1).getTitle();
                        String titulo2 = ((Record) o2).getTitle();
                        return titulo1.compareTo(titulo2);
                    }
                });
            }
            if (orderby.equals(orderTitleDesc)) {
                Collections.sort(allRecords, new Comparator<Object>() {
                    public int compare(Object o1, Object o2) {
                        String titulo1 = ((Record) o1).getTitle();
                        String titulo2 = ((Record) o2).getTitle();
                        return titulo2.compareTo(titulo1);
                    }
                });
            }
        if (orderby.equals(sortPast)) {
            Collections.sort(allRecords, new Comparator<Object>() {
                public int compare(Object o1, Object o2) {
                    String time1 = ((Record) o1).getRecord_time();
                    String time2 = ((Record) o2).getRecord_time();
                    if (time1 == null && time2 == null) {
                        return 0;
                    } else if (time1 == null) {
                        return -1;
                    } else if (time2 == null) {
                        return 1;
                    } else {
                        return time1.compareTo(time2);
                    }
                }
            });
        }
        else if (orderby.equals(newOrder)) {
            Collections.sort(allRecords, new Comparator<Object>() {
                public int compare(Object o1, Object o2) {
                    String time1 = ((Record) o1).getRecord_time();
                    String time2 = ((Record) o2).getRecord_time();
                    if (time1 == null && time2 == null) {
                        return 0;
                    } else if (time1 == null) {
                        return -1;
                    } else if (time2 == null) {
                        return 1;
                    } else {
                        return time2.compareTo(time1);
                    }
                }
            });
        }
                        //Creamos el adaptador para cargar los registros en el recycleView
                        RecordAdapter adapter = new RecordAdapter(getActivity(), allRecords);
                        RView_record.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }


        //Buscar registro en base de datos
                    private void Record_seach(String consultation) throws Exception {
                        Adapter_web adapter_web = new Adapter_web(getActivity(), helper.search_RecordsWeb(consultation));
                        Adapter_bank adapter_bank = new Adapter_bank(getActivity(), helper.search_RecordsBank(consultation));
                        Adapter_card adapter_card = new Adapter_card(getActivity(), helper.search_RecordsCard(consultation));
                        ConcatAdapter concatAdapter = new ConcatAdapter(adapter_web, adapter_bank, adapter_card);

                        RView_record.setAdapter(concatAdapter);
                    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_all, menu);

        MenuItem item = menu.findItem(R.id.menu_Record_seach);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //Método que se ejecuta cuando los usuarios presionan el botón de búsqueda del teclado
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    Record_seach(query);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
            //Método que permite realizar la búsqueda mientras el usuario está escribiendo en el teclado
            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    Record_seach(newText);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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
            try {
                Display_total_records();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
        try {
            loadRecord(statusOrder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        super.onResume();
    }

    //Método para visualizar total de registros
    public void Display_total_records() throws Exception {
        TextView Total_web, Total_bank, Total_card, Total;
        Button BtnFully_Undertood;

        dialog.setContentView(R.layout.box_total_records_dialog);
        Total_web = dialog.findViewById(R.id.Total_web);
        Total_bank = dialog.findViewById(R.id.Total_bank);
        Total_card = dialog.findViewById(R.id.Total_card);
        Total = dialog.findViewById(R.id.Total);
        BtnFully_Undertood = dialog.findViewById(R.id.BtnFully_Undertood);

        //Obtenemos valor entero de cada categoria de registro
        int totalWeb = helper.GetRecordNumberWeb();
        int totalBank = helper.GetRecordNumberBank();
        int totalCard = helper.GetRecordNumberCard();
        int total = helper.GetRecordNumber();

        //Convertir a String el número total de registros

        String total_Web = String.valueOf(totalWeb);
        Total_web.setText(total_Web);

        String total_Card = String.valueOf(totalCard);
        Total_card.setText(total_Card);

        String total_Bank = String.valueOf(totalBank);
        Total_bank.setText(total_Bank);

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
    //Método para ordenar registros
    private void Order_Record(){
        Button Btn_Category, Btn_New, Btn_Past, Btn_Asc, Btn_Desc;
        //Conexión hacia el diseño
        dialog_order.setContentView(R.layout.box_dialog_order_record);
        dialog_category.setContentView(R.layout.box_order_category);

        Btn_Category = dialog_order.findViewById(R.id.Btn_Category);
        Btn_New = dialog_order.findViewById(R.id.Btn_New);
        Btn_Past = dialog_order.findViewById(R.id.Btn_Past);
        Btn_Asc = dialog_order.findViewById(R.id.Btn_Asc);
        Btn_Desc = dialog_order.findViewById(R.id.Btn_Desc);

        Btn_Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_order.dismiss();
                orderRecordsByCategory();

            }
        });
        Btn_New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loadRecord(newOrder);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                dialog_order.dismiss();
            }
        });

        Btn_Past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loadRecord(sortPast);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                dialog_order.dismiss();
            }
        });
        Btn_Asc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loadRecord(orderTitleAsc);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                dialog_order.dismiss();
            }
        });
        Btn_Desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loadRecord(orderTitleDesc);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                dialog_order.dismiss();
            }
        });
        dialog_order.show();
        dialog_order.setCancelable(true);

    }
    //Método para ordenar los registros por categorías
    private void orderRecordsByCategory() {
        Button Btn_Order_Web, Btn_Order_Bank, Btn_Order_Card;
        dialog_order.setContentView(R.layout.box_order_category);

        Btn_Order_Web = dialog_order.findViewById(R.id.Btn_Order_Web);
        Btn_Order_Bank = dialog_order.findViewById(R.id.Btn_Order_Bank);
        Btn_Order_Card = dialog_order.findViewById(R.id.Btn_Order_Card);
        Btn_Order_Web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Adapter_web adapter_web = null;
                try {
                    adapter_web = new Adapter_web(getActivity(), helper.GetAllrecordWeb(statusOrder));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                RView_record.setAdapter(adapter_web);
                dialog_order.dismiss();
            }
        });

        Btn_Order_Bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Adapter_bank adapter_bank = null;
                try {
                    adapter_bank = new Adapter_bank(getActivity(), helper.GetAllrecordBank(statusOrder));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                RView_record.setAdapter(adapter_bank);
                dialog_order.dismiss();

            }
        });

       Btn_Order_Card.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Adapter_card adapter_card = null;
               try {
                   adapter_card = new Adapter_card(getActivity(), helper.GetAllrecordCard(statusOrder));
               } catch (Exception e) {
                   throw new RuntimeException(e);
               }
               RView_record.setAdapter(adapter_card);
               dialog_order.dismiss();
           }
       });
        dialog_order.show();
        dialog_order.setCancelable(true);
    }


}