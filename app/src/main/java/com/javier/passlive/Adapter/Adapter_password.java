package com.javier.passlive.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.javier.passlive.Detail.Detail_record;
import com.javier.passlive.Model.Password;
import com.javier.passlive.R;

import java.util.ArrayList;

//Inflar diseño item_password obtener los datos leidos en la BBDD en los textView
public class Adapter_password extends RecyclerView.Adapter<Adapter_password.HolderPassword>{

    private Context context;
    private ArrayList<Password> passwordList;

    //Constructor

    public Adapter_password(Context context, ArrayList<Password> passwordList) {
        this.context = context;
        this.passwordList = passwordList;
    }

    @NonNull
    @Override
    public HolderPassword onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflamos item
        View view = LayoutInflater.from(context).inflate(R.layout.item_password, parent, false);
        return new HolderPassword(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPassword holder, int position) {
    Password model_password = passwordList.get(position);
    String id = model_password.getId();
    String tittle = model_password.getTittle();
    String account = model_password.getAccount();
    String username = model_password.getUsername();
    String password = model_password.getPassword();
    String websites = model_password.getWebsites();
    String note = model_password.getNote();
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
}
