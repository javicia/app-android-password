package com.javier.passlive.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.javier.passlive.Password_Option.Add_Password;
import com.javier.passlive.R;


public class Fragment_All extends Fragment {

    RecyclerView RView_record;
    FloatingActionButton btnadd_password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        RView_record = view.findViewById(R.id.RView_record);
        btnadd_password= view.findViewById(R.id.btnadd_password);
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
}