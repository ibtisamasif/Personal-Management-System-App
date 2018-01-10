package com.example.hp.pms_project.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.pms_project.R;
import com.example.hp.pms_project.model.addTags;
import com.example.hp.pms_project.realm.RealmController;
import com.example.hp.pms_project.utils.Prefs;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by HP on 1/10/2018.
 */

public class TagsAdapter extends RealmRecyclerViewAdapter<addTags> {

    final Context context;
    private Realm realm;
    private LayoutInflater inflater;
    private String showCategory;
    public TagsAdapter(Context context) {

        this.context = context;
    }
    // create new views (invoked by the layout manager)
    @Override
    public TagsAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_tags, parent, false);
        return new TagsAdapter.CardViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        realm = RealmController.getInstance().getRealm();
        // get the article
        final addTags tags = getItem(position);
        final CardViewHolder holder = (CardViewHolder) viewHolder;
        // set the title and the snippet
        holder.tvIncomeTagName.setText(tags.getTagName());

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

                                RealmResults<addTags> results = realm.where(addTags.class).findAll();
                                // Get the transactionsTable title to show it in toast message
                                addTags t = results.get(position);
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
//        holder.card.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View content = inflater.inflate(R.layout.items_tags, null);
//
//
//
//                //final EditText etType = (EditText) content.findViewById(R.id.etType);
//
//                final EditText etTagNa = (EditText) content.findViewById(R.id.etTagNa);
//
//
//                //etType.setText(transactionsTable.getType());
//
//
//                etTagNa.setText(etTagNa.getTagName());
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setView(content)
//                        .setTitle("Edit Transaction")
//                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                RealmResults<transactionTable> results = realm.where(transactionTable.class).findAll();
//                                realm.beginTransaction();
//                                results.get(position).setTagName(etTagNa.getText().toString());
//                                realm.commitTransaction();
//                                notifyDataSetChanged();
//                                // realm.close();
//
//                            }
//                        })
//                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//                //realm.close();
//            }
//        });
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
        public TextView tvIncomeTagName;


        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card);

            tvIncomeTagName = (TextView) itemView.findViewById(R.id.tvIncomeTagName);



        }
    }

}
