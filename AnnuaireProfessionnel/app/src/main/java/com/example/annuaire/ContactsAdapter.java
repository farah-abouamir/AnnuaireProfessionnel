package com.example.annuaire;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

class ContactsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Personne> arrayList;


    public ContactsAdapter(Context context, ArrayList<Personne> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();}

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.list_row, parent, false);
        }

        Personne currentItem = (Personne) getItem(position);

        TextView textViewItemID = (TextView) convertView.findViewById(R.id.ID);
        TextView textViewItemName = (TextView) convertView.findViewById(R.id.Name);
        TextView textViewItemJob = (TextView) convertView.findViewById(R.id.Job);
        TextView textViewItemTel = (TextView) convertView.findViewById(R.id.Tel);
        TextView textViewItemEmail = (TextView) convertView.findViewById(R.id.Email);
        ImageButton btn = (ImageButton) convertView.findViewById(R.id.Call);
        textViewItemID.setText(String.valueOf(currentItem.getID()));
        textViewItemName.setText(currentItem.getName());
        textViewItemJob.setText(currentItem.getJob());
        textViewItemTel.setText(currentItem.getPhone());
        textViewItemEmail.setText(currentItem.getEmail());

        btn.setOnClickListener((ev) -> {
            Uri telephone = Uri.parse("tel:"+currentItem.getPhone());
            Intent SA = new Intent(Intent.ACTION_DIAL,telephone);
            context.startActivity(SA);
        });
        return convertView;
    }
}