package com.parallelfalchion.gamerwatch.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parallelfalchion.gamerwatch.R;
import com.parallelfalchion.gamerwatch.models.Game;

import java.util.ArrayList;

/**
 * Created by Luke on 2016-09-24.
 */

public class CustomBaseAdapter extends BaseAdapter {
    private static ArrayList<Game> gameList;

    private LayoutInflater mInflater;

    public CustomBaseAdapter(Context context, ArrayList<Game> results) {
        gameList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return gameList.size();
    }

    public Object getItem(int position) {
        return gameList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_row_view, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.row_game_title);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.row_game_price);
            holder.txtPlatform = (TextView) convertView.findViewById(R.id.row_game_platform);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String platformString = gameList.get(position).getPlatform().toString();
        holder.txtTitle.setText(gameList.get(position).getTitle());
        holder.txtPrice.setText(gameList.get(position).getPrice().toString());
        holder.txtPlatform.setText(platformString.equals("THREEDS")? "3DS":platformString);

        return convertView;
    }

    static class ViewHolder {
        TextView txtTitle;
        TextView txtPrice;
        TextView txtPlatform;
    }
}
