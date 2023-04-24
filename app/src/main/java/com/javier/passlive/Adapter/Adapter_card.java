package com.javier.passlive.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.javier.passlive.BBDD.BBDD_Helper;


import com.javier.passlive.Model.Web;
import com.javier.passlive.Record.Card_Record;
import com.javier.passlive.MainActivity;


import com.javier.passlive.Model.Card;
import com.javier.passlive.Util.Util_Card;
import com.javier.passlive.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_card extends RecyclerView.Adapter<Adapter_card.HolderCard> {
    private Context context;
    private ArrayList<Card> cardList;
    BBDD_Helper bbddHelper;
    Dialog dialog;

    //Creamos Constructor
    public Adapter_card(Context context, ArrayList<Card> cardList) {
        this.context = context;
        this.cardList = cardList;
        dialog = new Dialog(context);
        bbddHelper = new BBDD_Helper(context);
    }

    @NonNull
    @Override
    public HolderCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflamos item
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new HolderCard(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCard holder, @SuppressLint("Recyclerview") int position) {
        //if (cardList.get(position) instanceof Card) {
        Card model_card = cardList.get(position);
        String id = model_card.getId();
        String title = model_card.getTitle();
        String username = model_card.getUsername();
        String number = model_card.getNumber();
        String date = model_card.getDate();
        String cvc = model_card.getCvc();
        String note = model_card.getNotes();
        String image = model_card.getImage();
        String t_record = model_card.getRecord_time();
        String t_update = model_card.getUpdate_time();

        holder.Item_c_tittle.setText(title);
        holder.Item_c_account_name.setText(username);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            //Cuando el usuario presione el item
            public void onClick(View v) {
                Intent intent = new Intent(context, Card_Record.class);
                //Enviamos el dato id a la actividad Detail_record
                intent.putExtra("Id_registro", id);
                context.startActivity(intent);
            }
        });
        holder.Img_C_option.setOnClickListener(new View.OnClickListener() {
            @Override
            //Cuando el usuario presione el Image Button
            public void onClick(View v) {
                Option_edit_deleteCard(
                        "" + position,
                        "" + id,
                        "" + title,
                        "" + username,
                        "" + date,
                        "" + cvc,
                        "" + note,
                        "" + image,
                        "" + t_record,
                        "" + t_update);
            }
        });
    }
    //}

    //Método para obtener el registro
    @Override
    public int getItemCount() {
        //Devuelve el tamaño de la lista
        return cardList.size();
    }

    class HolderCard extends RecyclerView.ViewHolder {

        TextView Item_c_tittle, Item_c_account_name, Item_c_number, Item_c_date, Item_c_cvc, Item_c_note;
        ImageButton Img_C_option;

        public HolderCard(@NonNull View itemView) {
            super(itemView);

            Item_c_tittle = itemView.findViewById(R.id.Item_c_tittle);
            Item_c_account_name = itemView.findViewById(R.id.Item_c_account_name);
            Item_c_number = itemView.findViewById(R.id.Item_c_number);
            Item_c_date = itemView.findViewById(R.id.Item_c_date);
            Item_c_cvc = itemView.findViewById(R.id.Item_c_cvc);
            Item_c_note = itemView.findViewById(R.id.Item_c_note);

            Img_C_option = itemView.findViewById(R.id.Img_C_option);
        }
    }

    //Método para visualizar el cuadro de dialogo
    private void Option_edit_deleteCard(String position, String id, String title, String account_name,
                                        String date, String cvc, String note, String image, String t_record,
                                        String t_update) {
        Button Btn_edit_record, Btn_edit_delete_record;
        dialog.setContentView(R.layout.box_dialog_edit_delete);
        Btn_edit_record = dialog.findViewById(R.id.Btn_edit_record);
        Btn_edit_delete_record = dialog.findViewById(R.id.Btn_edit_delete_record);

        Btn_edit_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Util_Card.class);
                intent.putExtra("POSITION", position);
                intent.putExtra("ID", id);
                intent.putExtra("TITLE", title);
                intent.putExtra("ACCOUNT_NAME", account_name);
                intent.putExtra("DATE", date);
                intent.putExtra("CVC", cvc);
                intent.putExtra("NOTE", note);
                intent.putExtra("IMAGE", image);
                intent.putExtra("T_RECORD", t_record);
                intent.putExtra("T_UPDATE", t_update);
                intent.putExtra("EDITION MODE", true);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });

        Btn_edit_delete_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bbddHelper.deleteRecordCard(id);
                Intent intent = new Intent(context, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Toast.makeText(context, "Registro eliminado", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(true);

    }
}





