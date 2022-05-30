package com.example.expressdelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;

public class food_items extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_items);
        RecyclerView food=findViewById(R.id.food_items);
        ArrayList<food> list1=new ArrayList<>();
        String cat=getIntent().getStringExtra("cat");
        String url="http://"+localhost.ip+"/ExpressDelivery/food_items.php?Category="+cat;
        RequestQueue rq= Volley.newRequestQueue(this);
        JsonArrayRequest jq=new JsonArrayRequest(Request.Method.GET, url,null, response -> {
            for(int x=0;x<response.length();x++)
            {
                try {
                    list1.add(new food(response.getJSONObject(x).getInt("id"),response.getJSONObject(x).getString("Name"),response.getJSONObject(x).getInt("Price"),response.getJSONObject(x).getString("Image")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            FoodAdapter adapter=new FoodAdapter(list1);
            food.setAdapter(adapter);
        },error -> Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show());
        rq.add(jq);

    }
}