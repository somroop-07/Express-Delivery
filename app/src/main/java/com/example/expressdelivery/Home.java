package com.example.expressdelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ListView home=findViewById(R.id.home);
        ArrayList<Cusines> list=new ArrayList<>();
        String url="http://"+localhost.ip+"/ExpressDelivery/Food_items/cuisine.php";
        RequestQueue rq= Volley.newRequestQueue(this);
        JsonArrayRequest jq=new JsonArrayRequest(Request.Method.GET, url,null,response -> {
        for(int x=0;x<response.length();x++)
            {
                try {
                    list.add(new Cusines(response.getJSONObject(x).getString("Category"),response.getJSONObject(x).getString("Image")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            CuisineAdapter adapter=new CuisineAdapter(this,list);
            home.setAdapter(adapter);
            home.setOnItemClickListener((adapterView, view, i, l) -> {
                Intent intent=new Intent(this,food_items.class);
                String temp=list.get(i).title;
                intent.putExtra("cat",temp);
                startActivity(intent);

            });
        },error -> Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show());
        rq.add(jq);

    }
}
