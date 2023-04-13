package com.javier.passlive.PrivacyPolicy;


import android.content.Context;
import android.content.DialogInterface;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.javier.passlive.UserRegistration.Registration;


public class PrivacyPolicy  {

    //Método para mostrar el diálogo de política de privacidad
    public static void showPrivacyPolicyDialog(Context context, final Registration registration) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Política de privacidad");
        builder.setMessage("Por favor acepte la política de privacidad para continuar con el registro.");

        //Casilla de verificación para aceptar los términos de privacidad
        final CheckBox Cb_accept_terms = new CheckBox(context);
        Cb_accept_terms.setText("¿Acepta la política de privacidad?");

        //Agregar la casilla de verificación al cuadro de diálogo
        builder.setView(Cb_accept_terms);

        // Establecer un botón positivo para aceptar la política de privacidad
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Comprueba si la casilla de verificación está marcada
                if (Cb_accept_terms.isChecked()) {

                    //Llama al método onPrivacyPolicyAccepted() en la actividad de registro
                    registration.onPrivacyPolicyAccepted();
                } else {

                    Toast.makeText(context, "Por favor acepta la política de privacidad", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //
        //Establece un botón negativo para cancelar el diálogo
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Crear y mostrar el AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}


