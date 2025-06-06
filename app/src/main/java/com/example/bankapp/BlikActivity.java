package com.example.bankapp;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BlikActivity extends AppCompatActivity {
    Button btnCopy;
    TextView blikCode;
    ProgressBar progressBar;
    TextView timeLeft;
    VolleyController volleyController;

    BlikTimer blikTimer;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_blik);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.blik_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        blikCode = findViewById(R.id.blik_code);
        btnCopy = findViewById(R.id.copy_blik_code);
        progressBar = findViewById(R.id.progress_bar);
        timeLeft = findViewById(R.id.timer);

        volleyController = new VolleyController();

        volleyController.generateBlik(this);
        blikTimer = new BlikTimer(this,blikCode,timeLeft,progressBar);
        blikTimer.setOnBlikRequestDetected(() -> {
            blikTimer.stoptimertask();
            finish();
        });
        blikTimer.startTimer();

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("BLIK Code", blikCode.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(BlikActivity.this, "Skopiowano kod", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}