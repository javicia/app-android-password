package com.javier.passlive.Menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.javier.passlive.Adapter.Adapter_web;
import com.javier.passlive.BBDD.Helper;
import com.javier.passlive.BBDD.Query;
import com.javier.passlive.R;

import net.sqlcipher.database.SQLiteDatabase;

public class Menu_Web extends Fragment {
    Helper helper;
    String orderTitleAsc = Query.TITLE + " ASC";
    String statusOrder = orderTitleAsc;
    RecyclerView RView_record;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_web, container, false);

        SQLiteDatabase.loadLibs(getActivity());
        RView_record = view.findViewById(R.id.RView_record);
        try {
            loadRecordWeb(orderTitleAsc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return view;
    }

    private void loadRecordWeb(String orderby) throws Exception {
        statusOrder = orderby;
        Adapter_web adapter_web = new Adapter_web(getActivity(), helper.GetAllrecordWeb(orderby));
        RView_record.setAdapter(adapter_web);
    }
}





