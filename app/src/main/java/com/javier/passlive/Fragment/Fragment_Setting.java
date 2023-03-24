package com.javier.passlive.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.javier.passlive.BBDD.BBDDHelper;
import com.javier.passlive.MainActivity;
import com.javier.passlive.R;

import java.io.File;


public class Fragment_Setting extends Fragment {

    TextView Delete_All_Record, Export_File, Import_File;
    Dialog dialog;

    BBDDHelper bbddHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        Delete_All_Record = view.findViewById(R.id.Delete_All_Record);
        Export_File = view.findViewById(R.id.Export_File);
        Import_File = view.findViewById(R.id.Import_File);
        dialog = new Dialog(getActivity());
        bbddHelper = new BBDDHelper(getActivity());

        Delete_All_Record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Delete_All_Record();
            }
        });

        Export_File.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Exportar archivo", Toast.LENGTH_SHORT).show();
            }
        });

        Import_File.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Importar archivo", Toast.LENGTH_SHORT).show();
                Export_Record();
            }
        });
        return view;
    }
    private void Dialog_Delete_All_Record() {
        Button Btn_Yes, Btn_Cancel;
        dialog.setContentView(R.layout.box_dialog_delete_all_record);

        //Inicializamos las vistas
        Btn_Yes = dialog.findViewById(R.id.Btn_Yes);
        Btn_Cancel = dialog.findViewById(R.id.Btn_Cancel);

        //Asignamos los eventos
        Btn_Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bbddHelper.deleteAllRecord();
                startActivity(new Intent(getActivity(), MainActivity.class));
                Toast.makeText(getActivity(), "Eliminaste todos los registros", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        Btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(false);
    }
    private void Export_Record() {
        //Creamos el nombre de la carpeta
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS
        ), "PassLive");
        boolean fileFolder = false;
        //Si la carpeta no exista, la creamos
        if(!file.exists()){
        fileFolder = file.mkdirs();
        }
    }
}