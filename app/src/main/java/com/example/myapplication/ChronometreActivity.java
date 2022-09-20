package com.example.myapplication;

import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ChronometreActivity extends AppCompatActivity {
    static long chrono ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometre);

        Log.i("ChronometreActivity", "onCreate: ");

        Toast.makeText(ChronometreActivity.this, "Chronomètre", Toast.LENGTH_SHORT).show();


        ImageButton start = findViewById(R.id.start);
        ImageButton stop = findViewById(R.id.stop);
        stop.setEnabled(false);

        start.setOnClickListener(view -> {
            Toast.makeText(ChronometreActivity.this, "Début", Toast.LENGTH_SHORT).show();
            start.setEnabled(false);
            stop.setEnabled(true);
            chrono = java.lang.System.currentTimeMillis() ;
        } );

        stop.setOnClickListener(view -> {
            start.setEnabled(true);
            stop.setEnabled(false);

            long chrono2 = java.lang.System.currentTimeMillis() ;
            long temps = chrono2 - chrono ;
            Toast.makeText(ChronometreActivity.this, "Temps écoulé = " + temps + " ms", Toast.LENGTH_SHORT).show();
        } );

    }
}