package com.javier.passlive.PrivacyPolicy;


import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.javier.passlive.R;
import com.javier.passlive.UserRegistration.Registration;


public class PrivacyPolicy  {

    //Método para mostrar el diálogo de política de privacidad
    public static void showPrivacyPolicyDialog(Context context, final Registration registration) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.box_privacy_policy_dialog, null);
        builder.setView(dialogView);

        final CheckBox cbAcceptTerms = dialogView.findViewById(R.id.Cb_accept_terms);
        Button btnAccept = dialogView.findViewById(R.id.Btn_accept);

        //Establecer un botón positivo para aceptar la política de privacidad
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Comprueba si la casilla de verificación está marcada
                if (cbAcceptTerms.isChecked()) {
                    //Llama al método onPrivacyPolicyAccepted() en la actividad de registro
                    registration.onPrivacyPolicyAccepted();
                } else {
                    Toast.makeText(context, "Por favor acepta la política de privacidad", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //Establece un botón negativo para cancelar el diálogo
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Crear y mostrar el AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}


