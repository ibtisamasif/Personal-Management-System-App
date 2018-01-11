package com.example.hp.pms_project.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.pms_project.R;
import com.example.hp.pms_project.model.transactionTable;
import com.example.hp.pms_project.realm.RealmController;
import com.example.hp.pms_project.utils.DateAndTimeUtils;
import com.example.hp.pms_project.utils.Prefs;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by HP on 1/11/2018.
 */

public class IncExpBudAdapter extends RealmRecyclerViewAdapter<transactionTable> {
    final Context context;
    private Realm realm;

    public IncExpBudAdapter(Context context) {

        this.context = context;
    }

    // create new views (invoked by the layout manager)
    @Override
    public IncExpBudAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inc_exp_bud, parent, false);
        return new IncExpBudAdapter.CardViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        realm = RealmController.getInstance().getRealm();
        // get the article
        final transactionTable transactionsTable = getItem(position);
        // cast the generic view holder to our specific one
        final IncExpBudAdapter.CardViewHolder holder = (IncExpBudAdapter.CardViewHolder) viewHolder;
        // set the title and the snippet
        holder.tvIncomeType.setText(transactionsTable.getType());
        holder.tvIncomeDes.setText(transactionsTable.getDescription());
        holder.tvIncomeDate.setText(DateAndTimeUtils.getDateTimeStringFromMiliseconds(transactionsTable.getDate(), "MMM dd,yyyy hh:mm:ss a"));
        holder.tvIncomeAmount.setText(transactionsTable.getAmount() + "");
        holder.tvIncomeTagName.setText(transactionsTable.getTagName());
        holder.tvIncomeStatus.setText(transactionsTable.getStatus());
        holder.tvIncomeMemo.setText(transactionsTable.getMemo());

    }

    // return the size of your data set (invoked by the layout manager)
    public int getItemCount() {

        if (getRealmAdapter() != null) {

            return getRealmAdapter().getCount();
        }
        return 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView tvIncomeDate;
        public TextView tvIncomeDes;
        public TextView tvIncomeAmount;
        public TextView tvIncomeTagName;
        public TextView tvIncomeStatus;
        public TextView tvIncomeMemo;
        public TextView tvIncomeType;

        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card);
            tvIncomeType = (TextView) itemView.findViewById(R.id.tvIncomeType);
            tvIncomeDes = (TextView) itemView.findViewById(R.id.tvIncomeDes);
            tvIncomeDate = (TextView) itemView.findViewById(R.id.tvIncomeDate);
            tvIncomeAmount = (TextView) itemView.findViewById(R.id.tvIncomeAmount);
            tvIncomeTagName = (TextView) itemView.findViewById(R.id.tvIncomeTagName);
            tvIncomeStatus = (TextView) itemView.findViewById(R.id.tvIncomeStatus);
            tvIncomeMemo = (TextView) itemView.findViewById(R.id.tvIncomeMemo);


        }
    }

}
