package com.example.myapplication;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    private static final int RETOUR_PRENDRE_PHOTO = 1;

    private ImageView imgAffichePhoto;
    private  ActivityResultLauncher<Intent> activityResultLauncher;


    private String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("MainActivity", "onCreate: ");

        Button bonjour = findViewById(R.id.bonjour);
        Button photo = findViewById(R.id.photo);
        Button chrono = findViewById(R.id.chrono);
        Button tacheL = findViewById(R.id.tache_longue);
        Button galerie = findViewById(R.id.galerie);
        Button meteo = findViewById(R.id.meteo);
        Button contacts = findViewById(R.id.contact);


        bonjour.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "Bonjour", Toast.LENGTH_SHORT).show();
        } );

        chrono.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ChronometreActivity.class);
            startActivity(intent);
        });

        contacts.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Contacts.class);
            startActivity(intent);
        });

        meteo.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MeteoActivity.class);
            startActivity(intent);
        });


        tacheL.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TacheLongue.class);
            startActivity(intent);
        });




        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // récupérer la photo
                        Log.i("photo", "début on activity");
                        if(result.getResultCode() == RESULT_OK && result.getData() != null){
                            Bundle bundle = result.getData().getExtras();
                            Bitmap bitmap = (Bitmap) bundle.get("data");

                            Toast.makeText(MainActivity.this, "Photo prise taille : "+bitmap.getHeight() , Toast.LENGTH_SHORT).show();

                            //enregistrer
                            FileOutputStream fos = null;
                            try {
                                fos = openFileOutput("image.data", MODE_PRIVATE);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                            try {
                                fos.flush();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }


                    }
                }
        );
        photo.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Log.i("photo", "début");
            if(intent.resolveActivity(getPackageManager()) != null){
                Log.i("photo", "dans le if");
                activityResultLauncher.launch(intent);
            }
        });

        galerie.setOnClickListener((view -> {
            Intent intent = new Intent(MainActivity.this, Galerie.class);
            startActivity(intent);
        }));





    }

/*
    private void prendreUnePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // On vérifie que le téléphone dispose d'un appareil photo
        if(intent.resolveActivity(getPackageManager()) != null){
            // Créer un nom de fichier unique
            String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File photoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File photoFile = File.createTempFile("photo"+time, ".jpg", photoDir);
                //enregistrer le chemin complet
                photoPath = photoFile.getAbsolutePath();
                // Création de l'URI
                Uri photoUri = FileProvider.getUriForFile(MainActivity.this,
                        MainActivity.this.getApplicationContext().getPackageName()+".provider",
                        photoFile);
                // transfer Uri vers intent
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                // ouvrir l'intent
                startActivityForResult(intent, RETOUR_PRENDRE_PHOTO );

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
*/
    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        // On vérifie le requestcode
        if(requestcode==RETOUR_PRENDRE_PHOTO && resultcode== RESULT_OK){
            Bitmap image = BitmapFactory.decodeFile(photoPath);
            imgAffichePhoto.setImageBitmap(image);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void demarerChrono(MenuItem v){
        Intent intent = new Intent(MainActivity.this, ChronometreActivity.class);
        startActivity(intent);

    }

    public void quitter(MenuItem v){
        System.exit(0);

    }

    public void suspendre(MenuItem v){
        finish();
    }

}