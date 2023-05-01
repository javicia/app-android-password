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

import com.javier.passlive.BBDD.Helper;

import com.javier.passlive.Record.Web_Record;
import com.javier.passlive.MainActivity;
import com.javier.passlive.Model.Web;
import com.javier.passlive.Util.Util_Web;
import com.javier.passlive.R;

import java.util.ArrayList;


public class Adapter_web extends RecyclerView.Adapter<Adapter_web.HolderWeb>{

    private Context context;
    private ArrayList<Web> webList;

    Helper bbddHelper;

    Dialog dialog;

    //Creamos Constructor
    public Adapter_web(Context context, ArrayList<Web> webList) {
        this.context = context;
        this.webList = webList;
        dialog = new Dialog(context);
        bbddHelper = new Helper(context);
    }

    @NonNull
    @Override
    public HolderWeb onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflamos item
        View view = LayoutInflater.from(context).inflate(R.layout.item_web, parent, false);
        return new HolderWeb(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderWeb holder, @SuppressLint("Recyclerview") int position) {

            Web model_web = webList.get(position);
            String id = model_web.getId();
            String tittle = model_web.getTitle();
            String account = model_web.getAccount();
            String username = model_web.getUsername();
            String password = model_web.getPassword();
            String websites = model_web.getWebsites();
            String note = model_web.getNote();
            String image = model_web.getImage();
            String t_record = model_web.getT_record();
            String t_update = model_web.getT_update();

            holder.Item_tittle.setText(tittle);
            holder.Item_account.setText(account);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                //Cuando el usuario presione el item
                public void onClick(View v) {
                    Intent intent = new Intent(context, Web_Record.class);
                    //Enviamos el dato id a la actividad Detail_record
                    intent.putExtra("Id_registro", id);
                    context.startActivity(intent);
                }
            });
            holder.ImgB_option.setOnClickListener(new View.OnClickListener() {
                @Override
                //Cuando el usuario presione el Image Button
                public void onClick(View v) {
                    Option_edit_deleteWeb(
                            "" + position,
                            "" + id,
                            "" + tittle,
                            "" + account,
                            "" + username,
                            "" + websites,
                            "" + note,
                            "" + image,
                            "" + t_record,
                            "" + t_update
                    );

                }
            });
        }
    //}
        //Método para obtener el registro
        @Override
        public int getItemCount () {
            //Devuelve el tamaño de la lista
            return webList.size();
        }
    static class HolderWeb extends RecyclerView.ViewHolder{

        TextView Item_tittle,Item_account,Item_username,Item_password, Item_websites,Item_note;
        ImageButton ImgB_option;
        public HolderWeb(@NonNull View itemView) {
            super(itemView);

            Item_tittle = itemView.findViewById(R.id.Item_tittle);
            Item_account = itemView.findViewById(R.id.Item_account);
            Item_username = itemView.findViewById(R.id.Item_username);
            Item_password = itemView.findViewById(R.id.Item_password);
            Item_websites = itemView.findViewById(R.id.Item_websites);
            Item_note = itemView.findViewById(R.id.Item_note);

            ImgB_option = itemView.findViewById(R.id.ImgB_option);
        }

        public void bind(Web web) {
            Item_tittle.setText(web.getTitle());
            Item_account.setText(web.getAccount());
            Item_username.setText(web.getUsername());
            Item_password.setText(web.getPassword());
            Item_websites.setText(web.getWebsites());
            Item_note.setText(web.getNote());
        }
    }

    //Método para visualizar el cuadro de dialogo
    private void Option_edit_deleteWeb (String position, String id, String tittle, String account,
                                    String usename, String websites, String note, String image, String t_record,
                                    String t_update){
        Button Btn_edit_record, Btn_edit_delete_record;
        dialog.setContentView(R.layout.box_dialog_edit_delete);

        Btn_edit_record = dialog.findViewById(R.id.Btn_edit_record);
        Btn_edit_delete_record = dialog.findViewById(R.id.Btn_edit_delete_record);

        Btn_edit_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Util_Web.class);
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
                    try {
                        bbddHelper.deleteRecordWeb(id);
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
