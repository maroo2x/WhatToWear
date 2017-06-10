package higheye.whattowear;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.R.attr.delay;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    TextView mLatitudeText;
    TextView mLongitudeText;
    TextView weather;
    TextView weather2;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest = LocationRequest.create();
    public LocationAdapter locationAdapter = new LocationAdapter();
    WeatherAdapter weatherAdapter = new WeatherAdapter();
    String latitude;
    String longitude;
    LocationAvailability mLastLocationIsTrue;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLatitudeText = (TextView) findViewById(R.id.mLatitudeText);
        mLongitudeText = (TextView) findViewById(R.id.mLongitudeText);
        weather = (TextView) findViewById(R.id.weather);
        weather2 = (TextView) findViewById(R.id.weather2);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        // call api client
        buildGoogleApiClient();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else {
            Toast.makeText(this, "Not connected...", Toast.LENGTH_SHORT).show();
        }
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
        //check if current location is available
//        mLastLocationIsTrue = LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        // get last location
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        // check if there is any data in current location
        if (mLastLocation != null) {
            latitude = String.valueOf(mLastLocation.getLatitude());
            longitude = String.valueOf(mLastLocation.getLongitude());
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

    // method to build API client
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public synchronized void checkResults(View view) {
        spinner.setVisibility(View.VISIBLE);
        if (mLastLocation != null) {
            latitude = String.valueOf(mLastLocation.getLatitude());
            longitude = String.valueOf(mLastLocation.getLongitude());
            try {
                mLongitudeText.setText(getAddress(Double.parseDouble(latitude), Double.parseDouble(longitude)));
                locationAdapter.setCoords(latitude, longitude);
                } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            weather.setText("no current location");
        }
        locationAdapter.setCoords(latitude, longitude);
        try {
            mLongitudeText.setText(getAddress(Double.parseDouble(latitude), Double.parseDouble(longitude)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mLatitudeText.setText("Latitude: " + locationAdapter.getmLatitudeText() + ", " + "Longitude: " + locationAdapter.getmLongitudeText());
        determineWeather(0);
        determineWeather(1);

        spinner.setVisibility(View.GONE);
    }

    // method to get address basing on latitude and longitude
    public String getAddress(double latitude, double longitude) throws IOException {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
        String cityName = addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getAddressLine(1);
        String countryName = addresses.get(0).getAddressLine(2);
        return cityName + ", " + stateName; // +", "+countryName;
    }

    //method to request current location
    public void requestLocation() {
        mLocationRequest.setPriority(104);
    }


    public synchronized void determineWeather(int flag) {
        if (flag == 0) {
            DataHandler.Networking("http://api.openweathermap.org/data/2.5/weather?lat=" + locationAdapter.getmLatitudeText() + "&lon=" + locationAdapter.getmLongitudeText() + "&units=metric&appid=7e7469bd4b8aec9b7684f7b5dd63d3b5", flag);
            try {
                wait(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            weather.setText(weatherAdapter.getCurrentWeather());
        } else if (flag == 1) {
            DataHandler.Networking("http://api.openweathermap.org/data/2.5/forecast?lat=" + locationAdapter.getmLatitudeText() + "&lon=" + locationAdapter.getmLongitudeText() + "&units=metric&appid=06e65c7536988468e72a018ff2e8cf9b", flag);
            try {
                wait(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            weather2.setText(weatherAdapter.getFutureWeather());
        }
    }
}

/*
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread.start();
*/