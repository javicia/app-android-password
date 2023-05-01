package com.javier.passlive.Menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.javier.passlive.Adapter.Adapter_card;
import com.javier.passlive.BBDD.Helper;
import com.javier.passlive.BBDD.Query;
import com.javier.passlive.R;
import net.sqlcipher.database.SQLiteDatabase;

public class Menu_Card extends Fragment {

    Helper helper;
    String orderTitleAsc = Query.TITLE + " ASC";
    String statusOrder = orderTitleAsc;
    RecyclerView RView_record;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_record, container, false);

        SQLiteDatabase.loadLibs(getActivity());
        RView_record = view.findViewById(R.id.RView_record);
        helper = new Helper(getActivity());
        try {
            loadRecordCard(orderTitleAsc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return view;
    }

    private void loadRecordCard(String orderby) throws Exception {
        statusOrder = orderby;

        Adapter_card adapter_card = new Adapter_card(getActivity(), helper.GetAllrecordCard(orderby));
        RView_record.setAdapter(adapter_card);
    }
}

