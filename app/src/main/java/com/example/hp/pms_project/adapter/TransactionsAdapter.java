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
 * Created by HP on 1/2/2018.
 */


public class TransactionsAdapter extends RealmRecyclerViewAdapter<transactionTable> {

    final Context context;
    private Realm realm;
    private LayoutInflater inflater;
    private String showCategory;

    public TransactionsAdapter(Context context) {

        this.context = context;
    }

    // create new views (invoked by the layout manager)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books, parent, false);
        return new CardViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        realm = RealmController.getInstance().getRealm();
        // get the article
        final transactionTable transactionsTable = getItem(position);
        // cast the generic view holder to our specific one
        final CardViewHolder holder = (CardViewHolder) viewHolder;
        // set the title and the snippet
        holder.tvIncomeType.setText(transactionsTable.getType());
        holder.tvIncomeDes.setText(transactionsTable.getDescription());
        holder.tvIncomeDate.setText(DateAndTimeUtils.getDateTimeStringFromMiliseconds(transactionsTable.getDate(), "MMM dd,yyyy hh:mm:ss a"));
        holder.tvIncomeAmount.setText(transactionsTable.getAmount() + "");
        holder.tvIncomeTagId.setText(transactionsTable.getTagId() + "");
        holder.tvIncomeTagName.setText(transactionsTable.getTagName());
        holder.tvIncomeStatus.setText(transactionsTable.getStatus());
        holder.tvIncomeMemo.setText(transactionsTable.getMemo());
        //remove single match from realm
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Do you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                RealmResults<transactionTable> results = realm.where(transactionTable.class).findAll();
                                // Get the transactionsTable title to show it in toast message
                                transactionTable t = results.get(position);
                                long title = t.getId();
                                // All changes to data must happen in a transaction
                                realm.beginTransaction();
                                // remove single match
                                results.remove(position);
                                realm.commitTransaction();
                                if (results.size() == 0) {
                                    Prefs.with(context).setPreLoad(false);
                                }
                                notifyDataSetChanged();
                                //  realm.close();

                                Toast.makeText(context, title + " is removed from Realm", Toast.LENGTH_SHORT).show();

                            }
                        }).setNegativeButton("No", null).show();


                return false;
            }
        });

        //update single match from realm
        holder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View content = inflater.inflate(R.layout.edit_item, null);
                final Spinner spCategory;
                ArrayList<String> category;
                category = new ArrayList<String>();
                spCategory = (Spinner) content.findViewById(R.id.spCategory);
                String check = transactionsTable.getType();

                if (check.equals("Income")) {
                    Log.d("", "onClick: Income type  " + transactionsTable.getType());
                    category.clear();

                    Log.d("", "onClick: " + category);
                    category.add("Income");
                    category.add("Expense");
                    category.add("Budget");
                    Log.d("", "onClick: _______________________Income_________________" + category);

                }
                if (check.equals("Expense")) {
                    category.clear();
                    category.add("Expense");
                    category.add("Income");
                    category.add("Budget");
                    Log.d("", "onClick: _______________________Expense_________________" + category);

                }
                if (check.equals("Budget")) {
                    category.clear();
                    category.add("Budget");
                    category.add("Income");
                    category.add("Expense");
                    Log.d("", "onClick: _______________________Budget_________________" + category);

                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, category);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCategory.setAdapter(dataAdapter);
                spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                        showCategory = String.valueOf(spCategory.getSelectedItem());
                        //Toast.makeText(getApplicationContext(), showCategory, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
                //final EditText etType = (EditText) content.findViewById(R.id.etType);
                final EditText etDes = (EditText) content.findViewById(R.id.etDes);
                final EditText etAm = (EditText) content.findViewById(R.id.etAm);
                final EditText etTagNa = (EditText) content.findViewById(R.id.etTagNa);
                final EditText etstatus = (EditText) content.findViewById(R.id.etstatus);
                final EditText etmemo = (EditText) content.findViewById(R.id.etmemo);
                //etType.setText(transactionsTable.getType());
                etDes.setText(transactionsTable.getDescription() + "");
                etAm.setText(transactionsTable.getAmount() + "");
                etTagNa.setText(transactionsTable.getTagName());
                etstatus.setText(transactionsTable.getStatus());
                etmemo.setText(transactionsTable.getMemo());
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(content)
                        .setTitle("Edit Transaction")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RealmResults<transactionTable> results = realm.where(transactionTable.class).findAll();
                                realm.beginTransaction();
                                results.get(position).setType(showCategory);
                                results.get(position).setDescription(etDes.getText().toString());
                                results.get(position).setAmount(Long.parseLong(etAm.getText().toString()));
                                results.get(position).setTagName(etTagNa.getText().toString());
                                results.get(position).setStatus(etstatus.getText().toString());
                                results.get(position).setMemo(etmemo.getText().toString());
                                realm.commitTransaction();
                                notifyDataSetChanged();
                                // realm.close();

                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                //realm.close();
            }
        });
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
        public TextView tvIncomeTagId;
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
            tvIncomeTagId = (TextView) itemView.findViewById(R.id.tvIncomeTagId);
            tvIncomeTagName = (TextView) itemView.findViewById(R.id.tvIncomeTagName);
            tvIncomeStatus = (TextView) itemView.findViewById(R.id.tvIncomeStatus);
            tvIncomeMemo = (TextView) itemView.findViewById(R.id.tvIncomeMemo);


        }
    }
}
