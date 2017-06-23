package higheye.whattowear;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    TextView mCoords;
    TextView mAddress;
    ListView list;
    Context context;
    DataObject dataObject;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest = LocationRequest.create();
    public LocationAdapter locationAdapter = new LocationAdapter();
    WeatherAdapter weatherAdapter = new WeatherAdapter();
    DataHandler dataHandler = new DataHandler();
    String latitude;
    String longitude;
    LocationAvailability mLastLocationIsTrue;
    public String address;
    ArrayList<DataObject> entries;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCoords = (TextView) findViewById(R.id.mLatitudeText);
        mAddress = (TextView) findViewById(R.id.mLongitudeText);
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
// connection with API failed
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
        // format data location and address if exists
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
            mCoords.setText(R.string.no_current_location);
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
     //   Context context = this.context;
        new Asynctask(getApplicationContext()).execute(0);
    }

    private class Asynctask extends AsyncTask<Integer, Void, Boolean> {
        private Context mContext;
        public Asynctask (Context context){
            mContext = context;
        }
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

// call weather API and save current weather and forecast
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
            // format weather data
            mCoords.setText("Latitude: " + locationAdapter.getmLatitudeText() + ",\n " + "Longitude: " + locationAdapter.getmLongitudeText());
            mAddress.setText(locationAdapter.getAddress());
            dataHandler.setSingleString(weatherAdapter.getCurrentWeather(), 0);
            dataHandler.DefineStrings(weatherAdapter.getFutureWeather());
            // new arraylist adapter
            entries = new ArrayList<>();
            int i = 0;
            dataObject = new DataObject(10l, dataHandler.getSingleTemp(i), dataHandler.getSingleCloud(i), dataHandler.getSingleRain(i), dataHandler.getSingleSnow(i), dataHandler.getSingleIcon(i));
            entries.add(i, dataObject);

            for (i = 1; i < 20; i++) {
                dataObject = new DataObject(dataHandler.getSingleDate(i), dataHandler.getSingleTemp(i), dataHandler.getSingleCloud(i), dataHandler.getSingleRain(i), dataHandler.getSingleSnow(i), dataHandler.getSingleIcon(i));
                entries.add(i, dataObject);
            }

            CustomAdapter adapter = new CustomAdapter(mContext, R.layout.custom_row, entries);
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);
            spinner.setVisibility(View.GONE);
        }

        @Override
        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public String getDateFromUnix(long unixtime) {
        Date date = new Date(unixtime * 1000L); // *1000 is to convert seconds to milliseconds
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm - EEE, d. MMM"); // the format of your date
        String formatedDate = SimpleDateFormat.getDateTimeInstance(2, 3).format(date);
//        String formatedDate = sdf.format(date);
        return formatedDate;
    }
}

// ikony: http://openweathermap.org/img/w/10d.png

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