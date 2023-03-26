package com.javier.passlive.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.javier.passlive.BBDD.BBDDHelper;
import com.javier.passlive.BBDD.Constans;
import com.javier.passlive.MainActivity;
import com.javier.passlive.Model.Password;
import com.javier.passlive.R;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


public class Fragment_Setting extends Fragment {

    TextView Delete_All_Record, Export_File, Import_File, Change_Password;
    Dialog dialog, dialog_password;

    BBDDHelper bbddHelper;

    String orderTitleAsc = Constans.C_TITTLE + "ASC";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        Delete_All_Record = view.findViewById(R.id.Delete_All_Record);
        Export_File = view.findViewById(R.id.Export_File);
        Import_File = view.findViewById(R.id.Import_File);
        Change_Password = view.findViewById(R.id.Change_Password);
        dialog = new Dialog(getActivity());
        dialog_password = new Dialog(getActivity());
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
                //Comprobamos si el permiso fue concedido
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Export_Record();
                    //Si el permiso no fue concedido se mostrará el cuadro de dialogo para que
                    // el usuario pueda conceder el permiso
                }else {
                    PermissionExport.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

            }
        });

        Import_File.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("¿Deseas importar CSV?");
                builder.setMessage("Se eliminarán todos los registros");
                builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                            //A la hora de importar los registros eliminamos todos los registros de la BBDD
                            bbddHelper.deleteAllRecord();
                            Import_Record();
                            //Si el permiso no fue concedido se mostrará el cuadro de dialogo para que
                            // el usuario pueda conceder el permiso
                        }else {
                            PermissionImport.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Importación cancelada", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.create().show();
            }
        });

        Change_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity(), "Cambiar contraseña", Toast.LENGTH_SHORT).show();
                Dialog_Change_Password();
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
    //Método para exportar registro
    private void Export_Record() {
        //Creamos el nombre de la carpeta
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS
        ), "PassLive");
        boolean fileFolder = false;
        //Si la carpeta no exista, la creamos
        if(!file.exists()){
        fileFolder = file.mkdirs();
        }
        //Creamos el nombre del archivo
        String csvfileName ="PassLive.csv";
        //Concatenamos el nombre de la carpeta y archivo para almacenar en File_Folder
        String File_Folder = file + "/" + csvfileName;
        //Obtenemos el registro que exportaremos
        ArrayList<Password> recordList = new ArrayList<>();
        recordList.clear();
        recordList = bbddHelper.GetAllrecord(orderTitleAsc);
        try {
            //Escribir en el archivo
            FileWriter fileWriter = new FileWriter(File_Folder);
            //recorremos cada atributo para escribirlo en el archivo
            int i;
            for (i = 0; i < recordList.size(); i++) {
                fileWriter.append("" + recordList.get(i).getId());
                fileWriter.append(",");
                fileWriter.append("" + recordList.get(i).getTittle());
                fileWriter.append(",");
                fileWriter.append("" + recordList.get(i).getAccount());
                fileWriter.append(",");
                fileWriter.append("" + recordList.get(i).getUsername());
                fileWriter.append(",");
                fileWriter.append("" + recordList.get(i).getPassword());
                fileWriter.append(",");
                fileWriter.append("" + recordList.get(i).getWebsites());
                fileWriter.append(",");
                fileWriter.append("" + recordList.get(i).getNote());
                fileWriter.append(",");
                fileWriter.append("" + recordList.get(i).getImage());
                fileWriter.append(",");
                fileWriter.append("" + recordList.get(i).getT_record());
                fileWriter.append(",");
                fileWriter.append("" + recordList.get(i).getT_update());
                fileWriter.append("\n");
            }
            fileWriter.flush();
            fileWriter.close();
            Toast.makeText(getActivity(), "Se ha exportado archivo CSV con éxito", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    //Método para importar registro
    private void Import_Record(){
    //Establecemos la ruta donde se encuentra alamcenado ese registro
        String File_Folder = Environment.getExternalStorageDirectory() + "/Documents/" + "/PassLive/"
                + "/PassLive.csv/";
        File file = new File(File_Folder);
        if (file.exists()){
            //Si file existe, comenzamos la lectura
            try {
                CSVReader csvReader = new CSVReader(new FileReader(file.getAbsoluteFile()));
                String [] nextLine;
                //Recorremos todos los registros del archivo
                while ((nextLine = csvReader.readNext())!=null){
                    String id = nextLine[0];
                    String title = nextLine[1];
                    String account = nextLine[2];
                    String username = nextLine[3];
                    String password = nextLine[4];
                    String websites = nextLine[5];
                    String note = nextLine[6];
                    String image = nextLine[7];
                    String t_Record = nextLine[8];
                    String t_Update = nextLine[9];

                    long ids = bbddHelper.insertRecord("" + title, ""+ account,
                            "" + username, "" + password, ""+ websites,
                            ""+ note, ""+ image, ""+ t_Record, "" + t_Update);
                }
                Toast.makeText(getActivity(), "Archivo CSV importado con éxito", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getActivity(), "No se encuentra archivo", Toast.LENGTH_SHORT).show();
        }
    }

    //Permiso para exportar registros
    private ActivityResultLauncher<String> PermissionExport = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), grantPermissionExport -> {
                if(grantPermissionExport){
                    Export_Record();
                }else {
                    Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            }
    );

    //permiso para importar registro
    private ActivityResultLauncher<String> PermissionImport = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), grantPermissionImport -> {
                if(grantPermissionImport){
                    Import_Record();
                }else {
                    Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            }
    );

    private void Dialog_Change_Password(){
    //Establecemos vistas
    }
}