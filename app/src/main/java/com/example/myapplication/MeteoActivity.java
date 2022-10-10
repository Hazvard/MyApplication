package com.example.myapplication;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeteoActivity extends AppCompatActivity {

    private String cle = "87e40d0b0050ac946bbd2b4c75eeb7dd" ;
    String url = "https:openWeather.org/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

        Button position = findViewById(R.id.position);
        TextView temperatureTexte = findViewById(R.id.TemperatureText);
        TextView minTemperatureTexte = findViewById(R.id.mintemperatureText);
        TextView maxTemperatureTexte = findViewById(R.id.maxtemperatureText2);
        TextView ville = findViewById(R.id.ville);




        position.setOnClickListener(view -> {

            Log.i("MeteoActivity", "position cliqué");

            LocationManager lm = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            Toast.makeText(MeteoActivity.this, "Longitude  : " + longitude + "\nLatitude : " + latitude, Toast.LENGTH_SHORT).show();

            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(new Runnable() {
                @Override
                public void run() {
                    Log.i("thread tache longue", "début");

                    InputStream in = null;
                    JSONObject res;

                    try {
                        in = new java.net.URL("https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&lang=fr&units=metric&appid=87e40d0b0050ac946bbd2b4c75eeb7dd").openStream();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        res = readStream(in) ;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        //String weather = String.valueOf(res.getJSONObject("weather"));
                        //String ville = String.valueOf(res.getJSONObject("weather"));
                        JSONObject temp = (res.getJSONObject("main"));
                        String.valueOf(temp.get("temp"));
                        String temperature = String.valueOf(temp.get("temp"));




                        temperatureTexte.setText(temp.get("temp")+"°C");
                        minTemperatureTexte.setText("Min : "+temp.get("temp_min")+"°C");
                        maxTemperatureTexte.setText("Max : "+temp.get("temp_max")+"°C");
                        ville.setText(String.valueOf(res.get("name")));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            });




            //String url ="api.openweathermap.org/data/2.5/forecast?lat="+latitude+"&lon="+longitude+"&appid=87e40d0b0050ac946bbd2b4c75eeb7dd";
            /*
            InputStream in = null;
            try {
                in = new java.net.URL(url).openStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                JSONObject res = readStream(in) ;
                Toast.makeText(MeteoActivity.this, "Longitude : " + longitude + "\nLatitude : " + latitude
                                + "\nTempérature : " + res.getString("temp")
                        , Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

             */








        } );
    }

    private JSONObject readStream(InputStream is) throws IOException, JSONException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()){
            sb.append(line);
        }
        is.close();
        return new JSONObject(sb.toString());
    }
}