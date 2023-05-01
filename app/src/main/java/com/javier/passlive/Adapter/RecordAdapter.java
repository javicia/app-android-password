package com.javier.passlive.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.javier.passlive.BBDD.Helper;
import com.javier.passlive.Model.Bank;
import com.javier.passlive.Model.Card;
import com.javier.passlive.Model.Web;
import com.javier.passlive.R;
import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int WEB_TYPE = 0;
    private static final int CARD_TYPE = 1;
    private static final int BANK_TYPE = 2;

    private Context context;
    private ArrayList<Object> recordList;

    public RecordAdapter(Context context, ArrayList<Object> recordList) {
        this.context = context;
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case WEB_TYPE:
                View webView = inflater.inflate(R.layout.item_web, parent, false);
                return new Adapter_web.HolderWeb(webView);
            case CARD_TYPE:
                View cardView = inflater.inflate(R.layout.item_card, parent, false);
                return new Adapter_card.HolderCard(cardView);
            case BANK_TYPE:
                View bankView = inflater.inflate(R.layout.item_bank, parent, false);
                return new Adapter_bank.HolderBank(bankView);
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case WEB_TYPE:
                Web web = (Web) recordList.get(position);
                Adapter_web.HolderWeb webViewHolder = (Adapter_web.HolderWeb) holder;
                webViewHolder.bind(web);
                break;
            case CARD_TYPE:
                Card card = (Card) recordList.get(position);
                Adapter_card.HolderCard cardViewHolder = (Adapter_card.HolderCard) holder;
                cardViewHolder.bind(card);
                break;
            case BANK_TYPE:
                Bank bank = (Bank) recordList.get(position);
                Adapter_bank.HolderBank bankViewHolder = (Adapter_bank.HolderBank) holder;
                bankViewHolder.bind(bank);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + holder.getItemViewType());
        }
    }

    @Override
    public int getItemCount() {
         return recordList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = recordList.get(position);
        if (item instanceof Web) {
            return WEB_TYPE;
        } else if (item instanceof Card) {
            return CARD_TYPE;
        } else if (item instanceof Bank) {
            return BANK_TYPE;
        } else {
            throw new IllegalStateException("Unexpected value: " + item);
        }
    }
}
