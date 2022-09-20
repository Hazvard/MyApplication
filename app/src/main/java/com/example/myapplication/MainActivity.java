package com.example.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bonjour = findViewById(R.id.bonjour);
        Button photo = findViewById(R.id.photo);
        Button chrono = findViewById(R.id.chrono);

        bonjour.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "Bonjour", Toast.LENGTH_SHORT).show();
        } );

        chrono.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ChronometreActivity.class);
            startActivity(intent);
        });



        Toast.makeText(MainActivity.this, "Bienvenue", Toast.LENGTH_SHORT).show();

    }
}