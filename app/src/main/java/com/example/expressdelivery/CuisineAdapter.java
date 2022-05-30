package com.example.expressdelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CuisineAdapter extends ArrayAdapter<Cusines> {
    public CuisineAdapter(Context context, ArrayList<Cusines> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.cuisine, parent, false);
        }
        Cusines cusine = getItem(position);
        TextView title = listItemView.findViewById(R.id.Cuisine_names);
        title.setText(cusine.title);
        ImageView image = listItemView.findViewById(R.id.cuisinepics);
        Picasso.with(getContext()).load("http://" + localhost.ip + "/ExpressDelivery/Food_items/" + cusine.image).into(image);
        return listItemView;
    }
}


