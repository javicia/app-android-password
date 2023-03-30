package com.javier.passlive.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.javier.passlive.BBDD.BBDD;
import com.javier.passlive.Detail.Bank_Detail_Record;
import com.javier.passlive.Model.Bank;
import com.javier.passlive.R;

import java.util.ArrayList;

public class Adapter_bank extends RecyclerView.Adapter<Adapter_bank.HolderBank>{

    private Context context;
    private ArrayList<Bank> bankList;

    BBDD bbddHelper;

    Dialog dialog;

    public Adapter_bank(Context context, ArrayList<Bank> bankList) {
        this.context = context;
        this.bankList = bankList;
        dialog = new Dialog(context);
        bbddHelper = new BBDD(context);
    }
    @NonNull
    @Override
    public HolderBank onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflamos item
        View view = LayoutInflater.from(context).inflate(R.layout.item_bank, parent, false);
        return new Adapter_bank.HolderBank(view);

    @Override
    public void onBindViewHolder @NonNull HolderBank holder, @SuppressLint("Recyclerview") int position)  {
        Bank model_bank = bankList.get(position);
            String id = model_bank.getId();
            String tittle = model_bank.getTitle();
            String number = model_bank.getNumber;
            String websites = model_bank.getWebsites();
            String note = model_bank.getNotes();
            String image = model_bank.getImage();
            String t_record = model_bank.getRecord_time();
            String t_update = model_bank.getUpdate_time();

            holder.Item_b_tittle.setText(tittle);
            holder.Item_b_account_title.setText(account);
    }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            //Cuando el usuario presione el item
            public void onClick(View v) {
                Intent intent = new Intent(context, Bank_Detail_Record.class);
                //Enviamos el dato id a la actividad Detail_record
                intent.putExtra("Id_registro", id);
                context.startActivity(intent);
            }
        });

    @Override
    public int getItemCount() {
            return bankList.size();
    }


        class HolderBank extends RecyclerView.ViewHolder{

            TextView Item_b_tittle,Item_b_bank,Item_b_account_title, Item_b_number,
                    Item_b_websites, Item_b_note;
            ImageButton Img_B_option;
            public HolderBank(@NonNull View itemView) {
                super(itemView);

                Item_b_tittle = itemView.findViewById(R.id.Item_b_tittle);
                Item_b_bank = itemView.findViewById(R.id.Item_b_bank);
                Item_b_account_title = itemView.findViewById(R.id.Item_b_account_title);
                Item_b_number = itemView.findViewById(R.id.Item_b_number);
                Item_b_websites = itemView.findViewById(R.id.Item_b_websites);
                Item_b_note = itemView.findViewById(R.id.Item_b_note);


                Img_B_option = itemView.findViewById(R.id.Img_B_option);
            }
        }
}
