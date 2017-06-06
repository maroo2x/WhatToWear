package higheye.whattowear;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    TextView mLatitudeText;
    TextView mLongitudeText;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
public LocationAdapter locationAdapter = new LocationAdapter();
    WeatherAdapter weatherAdapter = new WeatherAdapter();
    DataHandler dataHandler = new DataHandler();
    String latitude;
    String longitude;
   String currentWeather;
    String futureWeather;
    static String callback = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLatitudeText = (TextView) findViewById(R.id.mLatitudeText);
        mLongitudeText = (TextView) findViewById(R.id.mLongitudeText);
        buildGoogleApiClient();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            Toast.makeText(this, "Not connected...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        Toast.makeText(this, "Failed to connect...", Toast.LENGTH_SHORT).show();
    }

    //    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onConnected(Bundle arg0) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        LocationServices.FusedLocationApi.requestLocationUpdates;
        if (mLastLocation != null) {
            latitude = String.valueOf(mLastLocation.getLatitude());
            longitude = String.valueOf(mLastLocation.getLongitude());
//            cityname = String.valueOf(mLastLocation.get)
            mLatitudeText.setText("Latitude: " + latitude+", "+"Longitude: " + longitude);
            try {
                mLongitudeText.setText(getAddress(Double.parseDouble(latitude),Double.parseDouble(longitude)));
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
//            currentWeather = DataHandler.Networking("http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=metric&appid=7e7469bd4b8aec9b7684f7b5dd63d3b5");
            futureWeather = DataHandler.Networking("http://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&units=metric&appid=7e7469bd4b8aec9b7684f7b5dd63d3b5");
//            Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();

        } else {
            mLatitudeText.setText("no current location");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void checkResults(View view) {
        locationAdapter.setCoords(latitude, longitude);
//        weatherAdapter.setCurrentWeather(currentWeather);
        weatherAdapter.setFutureWeather(futureWeather);
//        Toast.makeText(this, weatherAdapter.getCurrentWeather(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }
    public String getAddress(double latitude, double longitude) throws IOException {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
        String cityName = addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getAddressLine(1);
        String countryName = addresses.get(0).getAddressLine(2);
        return cityName+", "+stateName; // +", "+countryName;
    }
}
