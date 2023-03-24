package com.javier.passlive.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.javier.passlive.BBDD.BBDDHelper;
import com.javier.passlive.Detail.Detail_record;
import com.javier.passlive.MainActivity;
import com.javier.passlive.Model.Password;
import com.javier.passlive.Password_Option.Add_Update_Record;
import com.javier.passlive.R;

import java.util.ArrayList;

//Inflar diseño item_password obtener los datos leidos en la BBDD en los textView
public class Adapter_password extends RecyclerView.Adapter<Adapter_password.HolderPassword>{

    private Context context;
    private ArrayList<Password> passwordList;

    BBDDHelper bbddHelper;

    Dialog dialog;

    //Constructor

    public Adapter_password(Context context, ArrayList<Password> passwordList) {
        this.context = context;
        this.passwordList = passwordList;
        dialog = new Dialog(context);
        bbddHelper = new BBDDHelper(context);
    }

    @NonNull
    @Override
    public HolderPassword onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflamos item
        View view = LayoutInflater.from(context).inflate(R.layout.item_password, parent, false);
        return new HolderPassword(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPassword holder, @SuppressLint("Recyclerview") int position) {
    Password model_password = passwordList.get(position);
    String id = model_password.getId();
    String tittle = model_password.getTittle();
    String account = model_password.getAccount();
    String username = model_password.getUsername();
    String password = model_password.getPassword();
    String websites = model_password.getWebsites();
    String note = model_password.getNote();
    String image = model_password.getImage();
    String t_record = model_password.getT_record();
    String t_update = model_password.getT_update();

    holder.Item_tittle.setText(tittle);
    holder.Item_account.setText(account);

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        //Cuando el usuario presione el item
        public void onClick(View v) {
            Intent intent = new Intent(context, Detail_record.class);
            //Enviamos el dato id a la actividad Detail_record
                    intent.putExtra("Id_registro", id);
                    context.startActivity(intent);
        }
    });
    holder.ImgB_option.setOnClickListener(new View.OnClickListener() {
        @Override
        //Cuando el usuario presione el Image Button
        public void onClick(View v) {
        Option_edit_delete(
                "" + position,
                "" +id,
                "" +tittle,
                "" + account,
                "" + username,
                "" + websites,
                "" + note,
                "" + image,
                ""+ t_record,
                "" + t_update);

        }
    });
    }

    @Override
    public int getItemCount() {
        //Devuelve el tamaño de la lista
        return passwordList.size();
    }

    class HolderPassword extends RecyclerView.ViewHolder{

        TextView Item_tittle,Item_account,Item_username,Item_password, Item_websites,Item_note;
        ImageButton ImgB_option;
        public HolderPassword(@NonNull View itemView) {
            super(itemView);

            Item_tittle = itemView.findViewById(R.id.Item_tittle);
            Item_account = itemView.findViewById(R.id.Item_account);
            Item_username = itemView.findViewById(R.id.Item_username);
            Item_password = itemView.findViewById(R.id.Item_password);
            Item_websites = itemView.findViewById(R.id.Item_websites);
            Item_note = itemView.findViewById(R.id.Item_note);

            ImgB_option = itemView.findViewById(R.id.ImgB_option);
        }
    }

    //Método para visualizar el cuadro de dialogo
    private void Option_edit_delete(String position, String id, String tittle, String account,
                                    String usename, String websites, String note, String image, String t_record,
                                    String t_update){
        Button Btn_edit_record, Btn_edit_delete_record;
        dialog.setContentView(R.layout.box_dialog_edit_delete);
        Btn_edit_record = dialog.findViewById(R.id.Btn_edit_record);
        Btn_edit_delete_record = dialog.findViewById(R.id.Btn_edit_delete_record);

        Btn_edit_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Add_Update_Record.class);
                intent.putExtra("POSITION", position);
                intent.putExtra("ID", id);
                intent.putExtra("TITTLE", tittle);
                intent.putExtra("ACCOUNT", account);
                intent.putExtra("USERNAME", usename);
                intent.putExtra("WEBSITES", websites);
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
                    bbddHelper.deleteRecord(id);
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
