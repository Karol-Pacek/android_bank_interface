package com.example.bankapp;

import static android.widget.Toast.LENGTH_SHORT;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class BlikMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Toast.makeText(this,"Syyygma",LENGTH_SHORT).show();
    }
}
