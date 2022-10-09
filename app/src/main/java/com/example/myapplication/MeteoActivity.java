package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Button;
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

public class MeteoActivity extends AppCompatActivity {

    private String cle = "87e40d0b0050ac946bbd2b4c75eeb7dd" ;
    String url = "https:openWeather.org/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

        Button position = findViewById(R.id.position);




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

            Toast.makeText(MeteoActivity.this, "Longitude : " + longitude + "\nLatitude : " + latitude, Toast.LENGTH_SHORT).show();

            /*
            String url ="api.openweathermap.org/data/2.5/forecast?lat="+latitude+"&lon="+longitude+"&appid="+cle+"";
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
        is.close();;
        return new JSONObject(sb.toString());
    }
}