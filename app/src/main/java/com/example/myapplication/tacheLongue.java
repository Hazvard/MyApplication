package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class tacheLongue extends AppCompatActivity {

    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tache_longue);

        Log.i("tacheLongue", "début");

        Button calcul = findViewById(R.id.chargement);
        bar = findViewById(R.id.progressBar);
        bar.setVisibility(View.INVISIBLE);


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ProgressBar bar = findViewById(R.id.progressBar);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        };

        calcul.setOnClickListener(view -> {
            bar.setVisibility(View.VISIBLE);// Je n'arrive pas à la faire apparaitre puis disparaitre

            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(runnable);
            Toast.makeText(tacheLongue.this, "Fini", Toast.LENGTH_SHORT).show();
            runOnUiThread(runnable );


        });
    }
}