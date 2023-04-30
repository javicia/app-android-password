package com.javier.passlive.Menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;

import com.javier.passlive.BBDD.Helper;
import com.javier.passlive.R;

public class Web extends Fragment {
    Helper Helper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //No permite captura de pantalla
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        View view = inflater.inflate(R.layout.item_web, container, false);


    public void webMenu (){

    }
}
