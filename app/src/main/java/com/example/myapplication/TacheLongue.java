package com.example.myapplication;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TacheLongue extends AppCompatActivity {

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

        MediaPlayer son = MediaPlayer.create(this, R.raw.amongus);





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
                            son.start();
                            bar.setVisibility(View.INVISIBLE);
                            haz.setImageResource(R.drawable.sans_dan);
                        }
                    });
                }
            });




        });
    }
}