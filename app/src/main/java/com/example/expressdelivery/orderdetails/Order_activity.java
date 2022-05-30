package com.example.expressdelivery.orderdetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.expressdelivery.CuisineAdapter;
import com.example.expressdelivery.Cusines;
import com.example.expressdelivery.Home;
import com.example.expressdelivery.R;
import com.example.expressdelivery.UserInfo;
import com.example.expressdelivery.localhost;

import org.json.JSONException;

import java.util.ArrayList;

public class Order_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ListView temp_order=findViewById(R.id.temp_order);
        ArrayList<String> list=new ArrayList<>();
        String url="http://"+localhost.ip+"/ExpressDelivery/get_temp_order.php?mobile="+ UserInfo.mobile;
        RequestQueue rq= Volley.newRequestQueue(this);
        JsonArrayRequest jq=new JsonArrayRequest(Request.Method.GET, url,null, response -> {
            for(int x=0;x<response.length();x++)
            {
                try {
                    list.add("Name :"+response.getJSONObject(x).getString("Name")+"\n"+"Price : ₹"+
                            response.getJSONObject(x).getString("Price")+"\n"+"Quantity: "+response.getJSONObject(x).getString("qnty"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,list);
            temp_order.setAdapter(adapter);
    },error -> Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show());
        rq.add(jq);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.item_back)
        {
            Intent intent=new Intent(Order_activity.this, Home.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.item_cancel)
        {
            String url = "http://"+localhost.ip+"/ExpressDelivery/order_cancel.php?mobile="+UserInfo.mobile;
            RequestQueue rq= Volley.newRequestQueue(this);
            StringRequest sr=new StringRequest(Request.Method.GET, url, response -> {
                Intent intent=new Intent(Order_activity.this,Home.class);
                startActivity(intent);
            }, error -> Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show());
            rq.add(sr);
        }
        else
        {
            String url = "http://"+localhost.ip+"/ExpressDelivery/confirm_order.php?mobile="+UserInfo.mobile;
            RequestQueue rq= Volley.newRequestQueue(this);
            StringRequest sr=new StringRequest(Request.Method.GET, url, response -> {
                Intent intent=new Intent(Order_activity.this,Total_bill.class);
                intent.putExtra("bill_no",response);
                startActivity(intent);
            }, error -> Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show());
            rq.add(sr);
        }

        return true;
    }
}