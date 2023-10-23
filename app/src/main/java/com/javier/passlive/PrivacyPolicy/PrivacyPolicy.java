package com.javier.passlive.PrivacyPolicy;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.javier.passlive.MainActivity;
import com.javier.passlive.R;
import com.javier.passlive.UserRegistration.Registration;


public class PrivacyPolicy {

    public static void showPrivacyPolicyDialog(Context context, Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.box_privacy_policy_dialog, null);
        builder.setView(dialogView);

        final CheckBox Cb_accept_terms = dialogView.findViewById(R.id.Cb_accept_terms);
        Button Btn_accept = dialogView.findViewById(R.id.Btn_accept);

        final AlertDialog dialog = builder.create();
        dialog.show();

        Btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Cb_accept_terms.isChecked()) {
                    Toast.makeText(context, "Política de privacidad aceptada", Toast.LENGTH_SHORT).show();
                    // Llamar al método onPrivacyPolicyAccepted de la activity
                    if (activity instanceof Registration) {
                        ((Registration) activity).onPrivacyPolicyAccepted();
                    }
                    // Abrir el MainActivity
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Por favor acepte la política de privacidad", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}





