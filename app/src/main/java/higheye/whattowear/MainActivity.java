package higheye.whattowear;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    TextView mLatitudeText;
    TextView mLongitudeText;
    TextView weather1;
    TextView weather2;
    TextView weather3;
    TextView weather4;
    TextView weather5;
    TextView weather6;
    TextView weather7;
    TextView weather8;
    TextView weather9;
    TextView weather10;
    TextView weather11;
    TextView weather12;
    TextView weather13;
    TextView weather14;
    TextView weather15;
    TextView weather16;
    TextView weather17;
    TextView weather18;
    TextView weather19;
    TextView weather20;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest = LocationRequest.create();
    public LocationAdapter locationAdapter = new LocationAdapter();
    WeatherAdapter weatherAdapter = new WeatherAdapter();
    DataHandler dataHandler = new DataHandler();
    String latitude;
    String longitude;
    LocationAvailability mLastLocationIsTrue;
    private ProgressBar spinner;
    public String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLatitudeText = (TextView) findViewById(R.id.mLatitudeText);
        mLongitudeText = (TextView) findViewById(R.id.mLongitudeText);
        weather1 = (TextView) findViewById(R.id.weather1);
        weather2 = (TextView) findViewById(R.id.weather2);
        weather3 = (TextView) findViewById(R.id.weather3);
        weather4 = (TextView) findViewById(R.id.weather4);
        weather5 = (TextView) findViewById(R.id.weather5);
        weather6 = (TextView) findViewById(R.id.weather6);
        weather7 = (TextView) findViewById(R.id.weather7);
        weather8 = (TextView) findViewById(R.id.weather8);
        weather9 = (TextView) findViewById(R.id.weather9);
        weather10 = (TextView) findViewById(R.id.weather10);
        weather11 = (TextView) findViewById(R.id.weather11);
        weather12 = (TextView) findViewById(R.id.weather12);
        weather13 = (TextView) findViewById(R.id.weather13);
        weather14 = (TextView) findViewById(R.id.weather14);
        weather15 = (TextView) findViewById(R.id.weather15);
        weather16 = (TextView) findViewById(R.id.weather16);
        weather17 = (TextView) findViewById(R.id.weather17);
        weather18 = (TextView) findViewById(R.id.weather18);
        weather19 = (TextView) findViewById(R.id.weather19);
        weather20 = (TextView) findViewById(R.id.weather20);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
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
            locationAdapter.setCoords(latitude, longitude);
            try {
                address = getAddress(Double.parseDouble(locationAdapter.getmLatitudeText()), Double.parseDouble(locationAdapter.getmLongitudeText()));
                locationAdapter.setAddress(address);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mLatitudeText.setText(R.string.no_current_location);
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

/*
    public void resultsToText(View view) {
        mLatitudeText.setText("Latitude: " + locationAdapter.getmLatitudeText() + ", " + "Longitude: " + locationAdapter.getmLongitudeText());
        weather1.setText(weatherAdapter.getCurrentWeather());
        weather2.setText(weatherAdapter.getFutureWeather());
        mLongitudeText.setText(locationAdapter.getAddress());
    }
    */

//        spinner.setVisibility(View.GONE);

    // method to get address basing on latitude and longitude
    public String getAddress(double latitude, double longitude) throws IOException {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
        String cityName = addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getAddressLine(1);
        String countryName = addresses.get(0).getAddressLine(2);
        return cityName + ", " + stateName + ", " + countryName;
    }

    public void checkAsynctask(View view) {
               new Asynctask().execute(0);
    }

    private class Asynctask extends AsyncTask<Integer, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Integer... ints) {
                    try {
                        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat=" + locationAdapter.getmLatitudeText() + "&lon=" + locationAdapter.getmLongitudeText() + "&units=metric&appid=7e7469bd4b8aec9b7684f7b5dd63d3b5");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.connect();
                        StringBuilder response = new StringBuilder(50000);
                        try {
                            InputStream in = connection.getInputStream();
                            BufferedReader rd = new BufferedReader(new InputStreamReader(in));
                            int i = 0;
                            while ((i = rd.read()) > 0) {
                                response.append((char) i);
                            }
                            weatherAdapter.setCurrentWeather(response.toString());
                        } finally {
                            connection.disconnect();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    try {
                        URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?lat=" + locationAdapter.getmLatitudeText() + "&lon=" + locationAdapter.getmLongitudeText() + "&units=metric&appid=06e65c7536988468e72a018ff2e8cf9b");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.connect();
                        StringBuilder response = new StringBuilder(50000);
                        try {
                            InputStream in = connection.getInputStream();
                            BufferedReader rd = new BufferedReader(new InputStreamReader(in));
                            int i = 0;
                            while ((i = rd.read()) > 0) {
                                response.append((char) i);
                            }
                            weatherAdapter.setFutureWeather(response.toString());
                        } finally {
                            connection.disconnect();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            int n = 0;
        mLatitudeText.setText("Latitude: " + locationAdapter.getmLatitudeText() + ", " + "Longitude: " + locationAdapter.getmLongitudeText());
            mLongitudeText.setText(locationAdapter.getAddress());

            dataHandler.setSingleString(weatherAdapter.getCurrentWeather(), 0);
            dataHandler.DefineStrings(weatherAdapter.getFutureWeather());

 //           weather1.setText(dataHandler.getSingleString(0));
            weather1.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
//            weather2.setText("Date "+getDateFromUnix(dataHandler.getSingleDate(n)));
 //           weather3.setText(dataHandler.getSingleString(1));
            n++;

            weather2.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather3.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather4.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather5.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather6.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather7.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather8.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather9.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather10.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather11.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather12.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather13.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather14.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather15.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather16.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather17.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather18.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather19.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");
            n++;
            weather20.setText(dataHandler.getSingleString(n)+"\nClouds: "+dataHandler.getSingleCloud(n)+"; temp:"+dataHandler.getSingleTemp(n)+"; date: "+getDateFromUnix(dataHandler.getSingleDate(n))+"; rain: "+dataHandler.getSingleRain(n)+"; snow: "+dataHandler.getSingleSnow(n)+"\n");


        }


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public String getDateFromUnix(long unixtime) {
        Date date = new Date(unixtime * 1000L); // *1000 is to convert seconds to milliseconds
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm - EEE, d. MMM"); // the format of your date
        String formatedDate = SimpleDateFormat.getDateTimeInstance(2,3).format(date);
//        String formatedDate = sdf.format(date);
        return formatedDate;
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
/*
    //method to request current location
    public void requestLocation() {
        mLocationRequest.setPriority(104);
    }
*/