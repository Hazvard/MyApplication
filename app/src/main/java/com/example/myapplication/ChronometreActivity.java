package com.example.myapplication;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ChronometreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometre);

        Toast.makeText(ChronometreActivity.this, "Début du Chrono", Toast.LENGTH_SHORT).show();
    }
}