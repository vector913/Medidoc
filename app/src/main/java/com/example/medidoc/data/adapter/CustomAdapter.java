package com.example.medidoc.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.medidoc.R;
import com.example.medidoc.data.phonebook;
import com.example.medidoc.activity.sendlistactivity;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<phonebook> phonebooklist;
    public CustomAdapter(sendlistactivity activity, ArrayList<phonebook> phonebooklist) {
        this.context = activity;
        this.phonebooklist = phonebooklist;
    }

    @Override
    public int getCount() {
        return phonebooklist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listcontactview,null);
        }

        TextView phonename = v.findViewById(R.id.nameview);
        TextView phonenum = v.findViewById(R.id.phoneview);

        phonename.setText(phonebooklist.get(position).getName());
        phonenum.setText(phonebooklist.get(position).getNum());

        return v;
    }
}
