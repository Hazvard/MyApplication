package com.example.myapplication;

import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("MainActivity", "onCreate: ");

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


        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                // Contrat qui détermine le type de l'interaction
                new ActivityResultContracts.StartActivityForResult(),
                // Callback appelé lorsque le résultat sera disponible
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // récupérer la photo
                        Toast.makeText(MainActivity.this, "Photo prise", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        photo.setOnClickListener(view -> {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                Toast.makeText(MainActivity.this, "Photo", Toast.LENGTH_SHORT).show();
                launcher.launch(intent);
            }
        });





    }
}