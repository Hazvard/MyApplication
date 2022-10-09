package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
        ImageView haz = findViewById(R.id.haz);





        calcul.setOnClickListener(view -> {
            bar.setVisibility(View.VISIBLE);// Je n'arrive pas à la faire apparaitre puis disparaitre

            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(new Runnable() {
                @Override
                public void run() {
                    Log.i("thread tache longue", "début");

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bar.setVisibility(View.INVISIBLE);
                            haz.setImageResource(R.drawable.sans_dan);
                        }
                    });
                }
            });




        });
    }
}