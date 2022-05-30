package com.example.expressdelivery.orderdetails;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.expressdelivery.R;
import com.example.expressdelivery.UserInfo;
import com.example.expressdelivery.localhost;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import org.w3c.dom.Text;

import java.math.BigDecimal;

public class Total_bill extends AppCompatActivity {
    PayPalConfiguration config = null;
    Double amount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_bill);
        TextView total = findViewById(R.id.total_bill);
        Button button=findViewById(R.id.paypal_button);
        Intent intent = getIntent();
        String bill = intent.getStringExtra("bill_no");
        String url = "http://" + localhost.ip + "/ExpressDelivery/totaal.php?bill_no=" + bill;
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.GET, url, response -> {
            amount=Double.parseDouble(response);
            total.setText("₹ "+response);
        }, error -> Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show());
        rq.add(sr);

        /*
        Setting up Paypal service
         */

        config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(UserInfo.client_id);
        Intent intent1 = new Intent(this, PayPalService.class);
        intent1.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent1);

        button.setOnClickListener(view -> {
              amount=amount*0.013;
            PayPalPayment payment=new PayPalPayment(BigDecimal.valueOf(amount),"USD","Expresso Delivery",
                PayPalPayment.PAYMENT_INTENT_SALE);
            Intent intent2=new Intent(this, PaymentActivity.class);
            intent2.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
            intent2.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);
            startActivityForResult(intent2,777);
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==777)
        {
            if(resultCode== Activity.RESULT_OK) {
                Intent it = new Intent(this, Thankyou_screen.class);
                startActivity(it);
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

}