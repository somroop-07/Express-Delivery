package com.example.expressdelivery;

import android.content.Intent;
import android.os.Bundle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.expressdelivery.orderdetails.Order_activity;

public class dialogbox extends DialogFragment  {

    private EditText quantity;
    private AlertDialog dialog;

    public dialogbox(){};

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    /**
     *  Creating the dialogbox
     */

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.activity_dialogbox, null);
        builder.setView(view).setTitle("Enter quantity");
        quantity=view.findViewById(R.id.Quantity);
        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int id) {

            }

        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

        dialog=builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qnty = quantity.getText().toString().trim();
                if (qnty.isEmpty()){
                    quantity.setError("Enter Amount");}
                else {
                    long temp = Long.parseLong(qnty);
                    if(temp<=0)
                        quantity.setError("Enter valid quantity > 0");
                    else {
                        //listener.applyText(transfer_amount);

                        String url = "http://"+localhost.ip+"/ExpressDelivery/add_temp_order.php?mobile="
                        +UserInfo.mobile+"&item_id="+String.valueOf(UserInfo.item_id)+"&qnty="+qnty;
                        RequestQueue rq= Volley.newRequestQueue(getActivity());
                        StringRequest sr=new StringRequest(Request.Method.GET, url, response -> {
                            Intent intent=new Intent(getActivity(), Order_activity.class);
                            startActivity(intent);
                        }, error -> Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show());
                        rq.add(sr);

                    }
                }

            }
        });
        /**
         *  OnBackPressed Function defined
         */

        dialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                dialog.dismiss();
                return true;
            }
            return false;
        });
        return dialog;
    }


}
