package com.example.trapconnectwyres;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ListAdapter extends BaseAdapter {

    MainActivity main;

    ListAdapter(MainActivity main) {
        this.main = main;
    }

    static class ViewHolderItem {
        TextView hashedId;
        TextView state;
    }

    @Override
    public int getCount() {
        return  main.cage.size();
    }

    @Override
public Object getItem(int position) {
    return main.cage.get(position);
}

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolderItem holder = new ViewHolderItem();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) main.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cell, null);

            holder.hashedId = (TextView) convertView.findViewById(R.id.hashedId);
            holder.state = (TextView) convertView.findViewById(R.id.state);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolderItem) convertView.getTag();
        }
        holder.hashedId.setText(this.main.cage.get(position).hashedId);
        holder.state.setText(this.main.cage.get(position).state);

        switch(this.main.cage.get(position).state)
        {
            case "Open":
                holder.state.setTextColor(Color.parseColor("#8BC34A")); /*color code green*/
                break;
            case "Close":
                holder.state.setTextColor(Color.parseColor("#E00707")); /*color code red*/
                break;
            case "Self-test":
                holder.state.setTextColor(Color.parseColor("#FFDD00")); /*color code yellow*/
                break;
            case "Device not connected":
                holder.state.setTextColor(Color.parseColor("#2196F3")); /*color code blue*/
                break;
            default:
                holder.state.setTextColor(Color.parseColor("#000000")); /*color code black*/
                break;

        }

        return convertView;
    }

}