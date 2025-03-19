package com.example.bankapp;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class VolleyController {

    public void tempName(Context con) {
        RequestQueue queue = Volley.newRequestQueue(con);
        String url ="http://192.168.0.129:8080/generate-blik/123435634645564";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("trtOK", response);
                        Toast.makeText(con, response, LENGTH_SHORT);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("trtError", error.getMessage());
                        Toast.makeText(con, error.getMessage(), LENGTH_SHORT);
                    }
                });
        queue.add(stringRequest);
    }

}
