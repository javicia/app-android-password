package com.javier.passlive.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.javier.passlive.BBDD.BBDD;
import com.javier.passlive.BBDD.Constans;
import com.javier.passlive.Login.Login_user;
import com.javier.passlive.MainActivity;
import com.javier.passlive.Model.Password;
import com.javier.passlive.R;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


public class F_Setting extends Fragment {

    TextView Delete_All_Record, Export_File, Import_File, Change_Password;
    Dialog dialog, dialog_password;

    BBDD bbddHelper;

    String orderTitleAsc = Constans.C_TITTLE + "ASC";

    //Guardar preferencias de usuario en un archivo con una clave y valor
    SharedPreferences sharedPreferences;

    private static final String SHARE_PREF = "my_pref";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_C_PASSWORD = "c_password";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //No permite captura de pantalla
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        Delete_All_Record = view.findViewById(R.id.Delete_All_Record);
        Export_File = view.findViewById(R.id.Export_File);
        Import_File = view.findViewById(R.id.Import_File);
        Change_Password = view.findViewById(R.id.Change_Password);
        dialog = new Dialog(getActivity());
        dialog_password = new Dialog(getActivity());
        bbddHelper = new BBDD(getActivity());

        sharedPreferences = getActivity().getSharedPreferences(SHARE_PREF, Context.MODE_PRIVATE);

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
    //Establecemos las vistas
        EditText Txt_Password, New_Password, Confirm_Password;
        Button Btn_Change_password,Btn_Cancel_Password;

    //Creamos un String para almacenar la contraseña del usuario,
        // pasando Key_Password por parámetro que es donde están las contraseñas almacenadas
        String Recovered_password = sharedPreferences.getString(KEY_PASSWORD, null);

        //Realizamos la conexión con el cuadro de diálogo
        dialog_password.setContentView(R.layout.box_dialog_password);

        //Inicializamos las vistas
        Txt_Password = dialog_password.findViewById(R.id.Txt_Password);
        New_Password = dialog_password.findViewById(R.id.New_Password);
        Confirm_Password = dialog_password.findViewById(R.id.Confirm_Password);
        Btn_Change_password = dialog_password.findViewById(R.id.Btn_Change_password);
        Btn_Cancel_Password = dialog_password.findViewById(R.id.Btn_Cancel_Password);

        Btn_Change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //Recibimos los datos de los editText
                String String_newPassword = New_Password.getText().toString().trim();
                String String_confirmedNewPassword = Confirm_Password.getText().toString().trim();

                //Validamos los datos
                //Si el campo está vacío,
                if(String_newPassword.equals("")){
                    Toast.makeText(getActivity(), "Ingrese nueva contraseña", Toast.LENGTH_SHORT).show();
                } else if (String_confirmedNewPassword.equals("")) {
                    Toast.makeText(getActivity(), "Confirme nueva contraseña", Toast.LENGTH_SHORT).show();
                } else if (String_newPassword.length()<8) {
                    Toast.makeText(getActivity(), "La contraseña debe de tener al menos 8 caractéres.", Toast.LENGTH_SHORT).show();
                } else if (!String_newPassword.equals(String_confirmedNewPassword)) {
                    Toast.makeText(getActivity(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    //Cuando todas las condiciones no se cumplen
                }else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //Enviamos los nuevos datos a las llaves
                    editor.putString(KEY_PASSWORD, String_newPassword);
                    editor.putString(KEY_C_PASSWORD, String_confirmedNewPassword);
                    editor.apply();

                    //Después, salimos de la sesión para iniciar de nuevo con la nueva contraseña
                    startActivity(new Intent(getActivity(), Login_user.class));
                    getActivity().finish();
                    Toast.makeText(getActivity(), "la contraseña se ha cambiado con éxito.", Toast.LENGTH_SHORT).show();
                    dialog_password.dismiss();
                }


            }
        });
        Btn_Cancel_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
                dialog_password.dismiss();
            }
        });

        Txt_Password.setText(Recovered_password);
        //Desabilitamos escritura
        Txt_Password.setEnabled(false);
        Txt_Password.setBackgroundColor(Color.TRANSPARENT);
        //Cuando visualicemos el cuadro de diálogo la contraseña se encontrará oculta
        Txt_Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        dialog_password.show();
        dialog_password.setCancelable(false);
    }


}