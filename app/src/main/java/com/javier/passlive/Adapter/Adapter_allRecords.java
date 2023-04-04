package com.javier.passlive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.javier.passlive.Model.Bank;
import com.javier.passlive.Model.Card;
import com.javier.passlive.Model.Web;
import com.javier.passlive.R;

import java.util.List;

public class Adapter_allRecords extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> recordList;
    private Context context;

    public Adapter_allRecords(Context context, List<Object> recordList) {
        this.context = context;
        this.recordList = recordList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_all, parent, false);
        return new ViewHolderRecord(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object registro = recordList.get(position);
        if (registro instanceof Web) {
            ((ViewHolderRecord) holder).bindWeb((Web) registro);
        }

        else if (registro instanceof Bank) {
            ((ViewHolderRecord) holder).bindBank((Bank) registro);
        } else if (registro instanceof Card) {
            ((ViewHolderRecord) holder).bindCard((Card) registro);
        }
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public static class ViewHolderRecord extends RecyclerView.ViewHolder {

        private TextView txt_titulo;
        private TextView txt_usuario;
        private TextView txt_contrasena;

        public ViewHolderRecord(View itemView) {
            super(itemView);
            txt_titulo = itemView.findViewById(R.id.);
            txt_usuario = itemView.findViewById(R.id.txt_usuario);
            txt_contrasena = itemView.findViewById(R.id.txt_contrasena);
        }

        public void bindWeb(Web web) {
            txt_titulo.setText(web.getTittle());
            txt_usuario.setText(web.getUsername());
            txt_contrasena.setText(web.getPassword());
        }

        public void bindBank(Bank bank) {
            txt_titulo.setText(bank.getTitle());
            txt_usuario.setText(bank.getBank());
            txt_contrasena.setText(bank.getBankpassword());
        }

        public void bindCard(Card card) {
            txt_titulo.setText(card.getCardname());
            txt_usuario.setText(card.getCardnumber());
            txt_contrasena.setText(card.getCardpassword());
        }
    }
}
