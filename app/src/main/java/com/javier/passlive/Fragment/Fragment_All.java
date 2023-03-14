package com.javier.passlive.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.javier.passlive.Password_Option.Add_Password;
import com.javier.passlive.R;


public class Fragment_All extends Fragment {

    FloatingActionButton btnadd_password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__all, container, false);
        btnadd_password= view.findViewById(R.id.btnadd_password);
                btnadd_password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), Add_Password.class);
                        startActivity(intent);
                    }
                });
        return view;
    }
}