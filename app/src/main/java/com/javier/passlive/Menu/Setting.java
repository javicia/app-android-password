package com.javier.passlive.Menu;

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

import com.javier.passlive.BBDD.Helper;
import com.javier.passlive.BBDD.Query;
import com.javier.passlive.Login.Login_user;
import com.javier.passlive.MainActivity;
import com.javier.passlive.Model.Bank;
import com.javier.passlive.Model.Card;
import com.javier.passlive.Model.Web;
import com.javier.passlive.R;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Setting extends Fragment {

    TextView Delete_All_Record, Export_File, Import_File, Change_Password;
    Dialog dialog, dialog_password;

    Helper bbddHelper;

    String orderTitleAscWeb = Query.W_TITTLE + " ASC";
    String orderTitleAscBank = Query.B_TITLE_BANK + " ASC";
    String orderTitleAscCard = Query.C_TITLE_CARD + " ASC";

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
        bbddHelper = new Helper(getActivity());

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
                    try {
                        Export_Record();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
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
                            try {
                                bbddHelper.deleteAllRecord();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                Import_Record();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
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
    //Método para eliminar todos los registros
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
                try {
                    bbddHelper.deleteAllRecord();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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
    private void Export_Record() throws Exception {
        //Creamos el nombre de la carpeta
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS
        ), "PassLive");
        boolean fileFolder = false;
        //Si la carpeta no exista, la creamos
        if (!file.exists()) {
            fileFolder = file.mkdirs();
        }
        //Creamos el nombre del archivo
        String csvfileName = "PassLive.csv";

        //Obtenemos el registro que exportaremos
        ArrayList<Web> recordListWeb = bbddHelper.GetAllrecordWeb(orderTitleAscWeb);
        ArrayList<Bank> recordListBank = bbddHelper.GetAllrecordBank(orderTitleAscBank);
        ArrayList<Card> recordListCard = bbddHelper.GetAllrecordCard(orderTitleAscCard);

        try {
            //Escribir en el archivo
            FileWriter fileWriter = new FileWriter(file + "/" + csvfileName);
            //recorremos cada atributo para escribirlo en el archivo
            for (Web web : recordListWeb) {

                fileWriter.append("" + web.getId());
                fileWriter.append(",");
                fileWriter.append("" + web.getTittle());
                fileWriter.append(",");
                fileWriter.append("" + web.getAccount());
                fileWriter.append(",");
                fileWriter.append("" + web.getUsername());
                fileWriter.append(",");
                fileWriter.append("" + web.getPassword());
                fileWriter.append(",");
                fileWriter.append("" + web.getWebsites());
                fileWriter.append(",");
                fileWriter.append("" + web.getNote());
                fileWriter.append(",");
                fileWriter.append("" + web.getImage());
                fileWriter.append(",");
                fileWriter.append("" + web.getT_record());
                fileWriter.append(",");
                fileWriter.append("" + web.getT_update());
                fileWriter.append("\n");
            }

            for (Bank bank : recordListBank) {
                fileWriter.append("" + bank.getId());
                fileWriter.append(",");
                fileWriter.append("" + bank.getTitle());
                fileWriter.append(",");
                fileWriter.append("" + bank.getBank());
                fileWriter.append(",");
                fileWriter.append("" + bank.getTitle_account_bank());
                fileWriter.append(",");
                fileWriter.append("" + bank.getNumber());
                fileWriter.append(",");
                fileWriter.append("" + bank.getWebsites());
                fileWriter.append(",");
                fileWriter.append("" + bank.getNotes());
                fileWriter.append(",");
                fileWriter.append("" + bank.getImage());
                fileWriter.append(",");
                fileWriter.append("" + bank.getRecord_time());
                fileWriter.append(",");
                fileWriter.append("" + bank.getUpdate_time());
                fileWriter.append("\n");
            }
            for (Card card : recordListCard) {
                fileWriter.append("" + card.getId());
                fileWriter.append(",");
                fileWriter.append("" + card.getTitle());
                fileWriter.append(",");
                fileWriter.append("" + card.getUsername());
                fileWriter.append(",");
                fileWriter.append("" + card.getNumber());
                fileWriter.append(",");
                fileWriter.append("" + card.getDate());
                fileWriter.append(",");
                fileWriter.append("" + card.getCvc());
                fileWriter.append(",");
                fileWriter.append("" + card.getNotes());
                fileWriter.append(",");
                fileWriter.append("" + card.getImage());
                fileWriter.append(",");
                fileWriter.append("" + card.getRecord_time());
                fileWriter.append(",");
                fileWriter.append("" + card.getUpdate_time());
                fileWriter.append("\n");

            }

            fileWriter.flush();
            fileWriter.close();

            Toast.makeText(getActivity(), "Se ha exportado archivo CSV con éxito", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
        }
    }


    //Método para importar registro
    private void Import_Record() throws IOException {
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
                while ((nextLine = csvReader.readNext())!=null) {
                    if (nextLine.length == 10) { // Verificamos que la línea tenga la cantidad correcta de elementos
                        // Asignamos los valores de la línea a las variables cor
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
                        long idsWeb = bbddHelper.insertRecordWeb("" + title, "" + account,
                                "" + username, "" + password, "" + websites,
                                "" + note, "" + image, "" + t_Record, "" + t_Update);
                    } else if (nextLine.length == 9) {
                        String titleBank = nextLine[0];
                        String bank = nextLine[1];
                        String accountbank = nextLine[2];
                        String number = nextLine[3];
                        String websitesBank = nextLine[4];
                        String noteBank = nextLine[5];
                        String imageBank = nextLine[6];
                        String t_RecordBank = nextLine[7];
                        String t_UpdateBank = nextLine[8];

                        long idsBank = bbddHelper.insertRecordBank("" + titleBank, "" + bank,
                                "" + accountbank, "" + number, "" + websitesBank,
                                "" + noteBank, "" + imageBank, "" + t_RecordBank,
                                "" + t_UpdateBank);
                    } else if (nextLine.length == 8) {
                        String titleCard = nextLine[0];
                        String account_name_Card = nextLine[1];
                        String numberCard = nextLine[2];
                        String dateCard = nextLine[3];
                        String cvc = nextLine[4];
                        String noteCard = nextLine[5];
                        String imageCard = nextLine[6];
                        String t_RecordCard = nextLine[7];
                        String t_UpdateCard = nextLine[8];
                    long idsCard = bbddHelper.insertRecordCard("" + titleCard, "" + account_name_Card,
                                "" + numberCard, "" + dateCard, "" + cvc, "" + noteCard,
                                "" + imageCard, "" + t_RecordCard, "" + t_UpdateCard);
                    }
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
                    try {
                        Export_Record();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            }
    );

    //Darpermiso para importar registro
    private ActivityResultLauncher<String> PermissionImport = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), grantPermissionImport -> {
                if(grantPermissionImport){
                    try {
                        Import_Record();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            }
    );
    //Método para cambiar la contraseña
    private void Dialog_Change_Password(){
    //Establecemos las vistas
        EditText Password__;
        EditText New_Password, Confirm_Password;
        Button Btn_Change_password,Btn_Cancel_Password;

    //Creamos un String para almacenar la contraseña del usuario,
        // pasando Key_Password por parámetro que es donde están las contraseñas almacenadas
        String Recovered_password = sharedPreferences.getString(KEY_PASSWORD, null);

        //Realizamos la conexión con el cuadro de diálogo
        dialog_password.setContentView(R.layout.box_dialog_password);

        //Inicializamos las vistas
        Password__ = dialog_password.findViewById(R.id.Password__);
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
        Password__.setText(Recovered_password);
        //Desabilitamos escritura
        Password__.setEnabled(false);
        Password__.setBackgroundColor(Color.TRANSPARENT);
        //Cuando visualicemos el cuadro de diálogo la contraseña se encontrará oculta
        Password__.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        dialog_password.show();
        dialog_password.setCancelable(false);
    }

}