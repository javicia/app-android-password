package com.javier.passlive.Category;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.javier.passlive.Option_Bank.Bank_Add_Update_Record;
import com.javier.passlive.Option_Card.Card_Add_Update_Record;
import com.javier.passlive.Option_Web.Web_Add_Update_Record;
import com.javier.passlive.R;

public class Category extends AppCompatActivity {
    Button Btn_Web, Btn_Bank, Btn_Card;
    Dialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.box_add_category);

        Btn_Web = dialog.findViewById(R.id.Btn_Web);
        Btn_Bank = dialog.findViewById(R.id.Btn_Bank);
        Btn_Card = dialog.findViewById(R.id.Btn_Card);

        Btn_Web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, Web_Add_Update_Record.class);
                startActivity(intent);
            }
        });

        Btn_Bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, Bank_Add_Update_Record.class);
                startActivity(intent);
            }
        });

        Btn_Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, Card_Add_Update_Record.class);
                startActivity(intent);
            }
        });
    //Mostrar el dilogo y permitir que se pueda cancelar
        dialog.show();
        dialog.setCancelable(true);
    }


}




