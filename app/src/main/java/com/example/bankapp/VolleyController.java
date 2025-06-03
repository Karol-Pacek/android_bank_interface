package com.example.bankapp;

import static android.widget.Toast.LENGTH_SHORT;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VolleyController {

    /*public void tempName(Context con) {
        RequestQueue queue = Volley.newRequestQueue(con);
        String url ="http://192.168.0.114:8080/generate-blik/123435634645564";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("trtOK", response);
                        Toast.makeText(con, response, LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("trtError", error.getMessage());
                        Toast.makeText(con, error.getMessage(), LENGTH_SHORT).show();
                    }
                });
        queue.add(stringRequest);
    }*/

    public void checkBlik(Context con, TextView view, TextView timeLeft, ProgressBar bar,OnBlikRequestDetected callback) {
        RequestQueue queue = Volley.newRequestQueue(con);
        String url ="http://192.168.56.1:8080/check-blik/1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gson = new Gson();
                            Blik blikData = gson.fromJson(response.toString(), Blik.class);

                            if(blikData.blik_code == -1) {
                                generateBlik(con);
                            } else if (blikData.expiration.before(new Date())) {
                                updateBlik(con);
                            } else if (!Objects.equals(blikData.getRequested(), "")) {
                                if (callback != null) {
                                    callback.onRequestDetected();
                                }
                                Intent intent = new Intent(con, BlikConfirmActivity.class);
                                con.startActivity(intent);
                            } else {
                                view.setText(String.valueOf(blikData.blik_code));
                                long diffInMillis = blikData.expiration.getTime() - new Date().getTime();

                                long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);
                                long seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);

                                String formattedTime = con.getResources().getString(R.string.blik_progress_timer,minutes,seconds%60);
                                timeLeft.setText(formattedTime);
                                bar.setProgress((int) seconds);
                            }

                        } catch (Exception e) {
                            Log.e("changeText", "Error parsing response", e);
                            Toast.makeText(con, "Error processing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = (error.getMessage() != null) ? error.getMessage() : "Unknown error occurred";
                        Log.d("changeText", errorMessage);
                        Toast.makeText(con, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void generateBlik(Context con) {
        RequestQueue queue = Volley.newRequestQueue(con);
        String url ="http://192.168.56.1:8080/generate-blik/1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            /*Gson gson = new Gson();
                            Blik blikData = gson.fromJson(response.toString(), Blik.class);

                            view.setText(String.valueOf(blikData.blik_code));*/

                        } catch (Exception e) {
                            Log.e("changeText", "Error parsing response", e);
                            Toast.makeText(con, "Error processing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = (error.getMessage() != null) ? error.getMessage() : "Unknown error occurred";
                        Log.d("changeText", errorMessage);
                        Toast.makeText(con, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void updateBlik(Context con) {
        RequestQueue queue = Volley.newRequestQueue(con);
        String url ="http://192.168.56.1:8080/update-blik/1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            /*Gson gson = new Gson();
                            Blik blikData = gson.fromJson(response.toString(), Blik.class);

                            view.setText(String.valueOf(blikData.blik_code));*/

                        } catch (Exception e) {
                            Log.e("changeText", "Error parsing response", e);
                            Toast.makeText(con, "Error processing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = (error.getMessage() != null) ? error.getMessage() : "Unknown error occurred";
                        Log.d("changeText", errorMessage);
                        Toast.makeText(con, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }

}
