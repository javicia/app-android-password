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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.javier.passlive.BBDD.Helper;
import com.javier.passlive.MainActivity;
import com.javier.passlive.Model.Bank;
import com.javier.passlive.R;
import com.javier.passlive.Record.Bank_Record;
import com.javier.passlive.Util.Util_Bank;

import java.util.ArrayList;

public class Adapter_bank extends RecyclerView.Adapter<Adapter_bank.HolderBank>{
    private Context context;
    private ArrayList<Bank> bankList;
    Helper bbddHelper;
    Dialog dialog;


   public Adapter_bank(Context context, ArrayList<Bank> bankList){
       this.context = context;
       this.bankList = bankList;
       dialog = new Dialog(context);
       bbddHelper = new Helper((FragmentActivity) context);

   }

    @NonNull
    @Override
    public HolderBank onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflamos item
        View view = LayoutInflater.from(context).inflate(R.layout.item_bank, parent, false);
        return new HolderBank(view);
    }

    @Override
    public void onBindViewHolder (@NonNull HolderBank holder, @SuppressLint("Recyclerview") int position) {
        //if (bankList.get(position) instanceof Bank) {
            Bank model_bank = bankList.get(position);
            String id = model_bank.getId();
            String title = model_bank.getTitle();
            String bank = model_bank.getBank();
            String account_name = model_bank.getAccount_bank();
            String number = model_bank.getNumber();
            String websites = model_bank.getWebsites();
            String note = model_bank.getNotes();
            String image = model_bank.getImage();
            String t_record = model_bank.getRecord_time();
            String t_update = model_bank.getUpdate_time();

            holder.Item_b_tittle.setText(title);
            holder.Item_b_account_name.setText(account_name);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                //Cuando el usuario presione el item
                public void onClick(View v) {
                    Intent intent = new Intent(context, Bank_Record.class);
                    //Enviamos el dato id a la actividad Detail_record
                    intent.putExtra("Id_registro", id);
                    context.startActivity(intent);
                }
            });
            holder.Img_B_option.setOnClickListener(new View.OnClickListener() {
                @Override
                //Cuando el usuario presione el Image Button
                public void onClick(View v) {
                    Option_edit_deleteBank(
                            "" + position,
                            "" + id,
                            "" + title,
                            "" + bank,
                            "" + account_name,
                            "" + number,
                            "" + websites,
                            "" + note,
                            "" + image,
                            "" + t_record,
                            "" + t_update);

                }
            });
        }

    //Método para obtener el registro
    @Override
    public int getItemCount() {
        //Devuelve el tamaño de la lista
        return bankList.size();
    }

    static class HolderBank extends RecyclerView.ViewHolder{

       TextView Item_b_tittle, Item_b_bank, Item_b_account_name, Item_b_number, Item_b_websites,Item_b_note;
        ImageButton Img_B_option;
        public HolderBank(@NonNull View itemView) {
            super(itemView);

            Item_b_tittle = itemView.findViewById(R.id.Item_b_tittle);
            Item_b_bank = itemView.findViewById(R.id.Item_b_bank);
            Item_b_account_name = itemView.findViewById(R.id.Item_b_account_name);
            Item_b_number = itemView.findViewById(R.id.Item_b_number);
            Item_b_websites = itemView.findViewById(R.id.Item_b_websites);
            Item_b_note = itemView.findViewById(R.id.Item_b_note);

            Img_B_option = itemView.findViewById(R.id.Img_B_option);
        }

        public void bind(Bank bank) {
            Item_b_tittle.setText(bank.getTitle());
            Item_b_bank.setText(bank.getBank());
            Item_b_account_name.setText(bank.getAccount_bank());
            Item_b_number.setText(bank.getNumber());
            Item_b_websites.setText(bank.getWebsites());
            Item_b_note.setText(bank.getNotes());

        }
    }
    private void Option_edit_deleteBank(String position, String id, String title, String bank ,
                                    String account_name, String number, String websites, String notes ,String image, String t_record,
                                    String t_update){
        Button Btn_edit_record, Btn_edit_delete_record;
        dialog.setContentView(R.layout.box_dialog_edit_delete);

        Btn_edit_record = dialog.findViewById(R.id.Btn_edit_record);
        Btn_edit_delete_record = dialog.findViewById(R.id.Btn_edit_delete_record);

        Btn_edit_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Util_Bank.class);
                intent.putExtra("POSITION", position);
                intent.putExtra("ID", id);
                intent.putExtra("TITLE", title);
                intent.putExtra("ACCOUNT", bank);
                intent.putExtra("NAME_OWNER", account_name);
                intent.putExtra("NUMBER", number);
                intent.putExtra("WEBSITES", websites);
                intent.putExtra("NOTE", notes);
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
                try {
                    bbddHelper.deleteRecordBank(id);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Intent intent = new Intent(context, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Toast.makeText(context,"Registro eliminado", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(true);

    }
    }

