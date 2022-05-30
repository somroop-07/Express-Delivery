package com.example.expressdelivery;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ProjectViewHolder> {
    ArrayList<food> cuisine;

    public FoodAdapter(ArrayList<food> cuisine) {
        this.cuisine = cuisine;
    }

    @Override
    public int getItemCount() {
        return cuisine.size();
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_recycle, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int i) {
        holder.bind(cuisine.get(i), i,cuisine.get(i).id);
    }


    static class ProjectViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView title;
        private final TextView price;
        private final ImageView add;
        private dialogbox dialog;

        public ProjectViewHolder(@NonNull View itemview) {
            super(itemview);
            image = itemview.findViewById(R.id.Food_pics);
            title = itemview.findViewById(R.id.Food_names);
            price = itemview.findViewById(R.id.Food_price);
            add=itemview.findViewById(R.id.add_button);

        }

        public void bind(food cuisine, int position,int itemid) {
            title.setText(cuisine.title);
            String fin_price="₹ "+String.valueOf(cuisine.price);
            price.setText(fin_price);
            Picasso.with(itemView.getContext()).load("http://" + localhost.ip + "/ExpressDelivery/Food_items/" + cuisine.image).into(image);
           add.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                 UserInfo.item_id=itemid;

                 FragmentActivity activity= (FragmentActivity) itemView.getContext();
                 FragmentManager fm=activity.getSupportFragmentManager();
                   dialog=new dialogbox();
                   dialog.show(fm,"Enter quantity");
               }
           });

        }
    }
}