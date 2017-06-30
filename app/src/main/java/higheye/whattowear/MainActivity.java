package higheye.whattowear;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    TextView mCoords;
    TextView mAddress;
    ListView list;
    Context context;
    DataObject dataObject;
    GoogleApiClient mGoogleApiClient;
    LocationRequest locationRequest;
    Location mLastLocation;
    public LocationAdapter locationAdapter = new LocationAdapter();
    WeatherAdapter weatherAdapter = new WeatherAdapter();
    DataHandler dataHandler = new DataHandler();
    String latitude;
    String longitude;
    public String address;
    ArrayList<DataObject> entries;
    ProgressBar spinner;
    LocationListener locationListener;

    private Switch switchbtn;

    @Override
    public void onLocationChanged(Location location) {
        // Do something with the location
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCoords = (TextView) findViewById(R.id.mLatitudeText);
        mAddress = (TextView) findViewById(R.id.mLongitudeText);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
//        spinner.setVisibility(View.VISIBLE);
 //       Toast.makeText(this, "toast right after spinner", Toast.LENGTH_SHORT).show();
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1);
        locationRequest.setFastestInterval(1);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setNumUpdates(1);
//        spinner.setVisibility(View.VISIBLE);
        // call api client
        buildGoogleApiClient();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else {
            Toast.makeText(this, "Not connected...", Toast.LENGTH_SHORT).show();
//            spinner.setVisibility(View.GONE);
        }
        switchbtn = (Switch) findViewById(R.id.switchForActionBar);
/*        switchbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                }else{
                }
            }
        });
*/
    }

    // connection with API failed
    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        Toast.makeText(this, "Failed to connect...", Toast.LENGTH_SHORT).show();
//        spinner.setVisibility(View.GONE);
    }

    @Override
//    public void onConnected(Bundle arg0) {
    public void onConnected(Bundle arg0) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_GRANTED);
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_GRANTED);
                return;
            }
        }
        //check if current location is available
//        mLastLocationIsTrue = LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);

 /*       // get last location
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        // format data location and address if exists
        if (mLastLocation != null) {
            latitude = String.valueOf(mLastLocation.getLatitude());
            longitude = String.valueOf(mLastLocation.getLongitude());
            locationAdapter.setCoords(latitude, longitude);
            try {
                address = getAddress(Double.parseDouble(locationAdapter.getmLatitudeText()), Double.parseDouble(locationAdapter.getmLongitudeText()));
                locationAdapter.setAddress(address);
                checkAsynctask(MainActivity.this.getCurrentFocus());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // update last location
            //          LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, (LocationListener) this);
            //          mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mCoords.setText(R.string.no_current_location);
//          spinner.setVisibility(View.GONE);
//          return;
        }
        */

        checkAsynctask(findViewById(android.R.id.content));
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

    //    public void checkAsynctask() {
    public void checkAsynctask(View view) {
//        spinner.setVisibility(View.VISIBLE);
        if (mLastLocation != null) {
            new Asynctask(getApplicationContext()).execute(0);
//            Toast.makeText(this, "Asynctask 1", Toast.LENGTH_SHORT).show();
        }
        else {
            updateLocation();
            onConnected(Bundle.EMPTY);
        }
    }

    public void updateLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_GRANTED);
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_GRANTED);
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, (LocationListener) this);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    private class Asynctask extends AsyncTask<Integer, Void, Boolean> {
        private Context mContext;

        public Asynctask(Context context) {
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

// get last location
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_GRANTED);
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_GRANTED);
                return;
            }
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            // format data location and address if exists
            while (mLastLocation == null) {
                updateLocation();
                break;
            }
                latitude = String.valueOf(mLastLocation.getLatitude());
                longitude = String.valueOf(mLastLocation.getLongitude());
                locationAdapter.setCoords(latitude, longitude);
                try {
                    address = getAddress(Double.parseDouble(locationAdapter.getmLatitudeText()), Double.parseDouble(locationAdapter.getmLongitudeText()));
                    locationAdapter.setAddress(address);
                } catch (IOException e) {
                    e.printStackTrace();
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
//        getMenuInflater().inflate(R.menu.navigation, menu);
        MenuItem item = menu.findItem(R.id.switchForActionBar);
//        item.setActionView(R.layout.switch_layout);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuItem1:
                return true;
            case R.id.menuItem2:
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
/*
    //method to request current location
    public void requestLocation() {
        mLocationRequest.setPriority(104);
    }
*/