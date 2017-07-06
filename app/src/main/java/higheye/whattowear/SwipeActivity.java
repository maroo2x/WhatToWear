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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
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
import java.util.List;
import java.util.Locale;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class SwipeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private AdView mAdView;
    TextView mCoords;
    TextView mAddress;
    ListView list;
    Context context;
    GoogleApiClient mGoogleApiClient;
    LocationRequest locationRequest;
    Location mLastLocation;
    public LocationAdapter locationAdapter = new LocationAdapter();
    WeatherAdapter weatherAdapter = new WeatherAdapter();
    DataHandler dataHandler = new DataHandler();
    String latitude;
    String longitude;
    public String address;
    ProgressBar spinner;
    LocationListener locationListener;
    int unit;
    CustomAdapter adapter;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        spinner = (ProgressBar) findViewById(R.id.progressBar1);
 //       spinner.setVisibility(View.GONE);
        // advert
        MobileAds.initialize(this, "ca-app-pub-9181728221541409~1070109579");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_swipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_swipe, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /*        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }
*/
        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    return new CurrentWeatherFragment();
                case 1:
                    return new ForecastFragment();
            }
            return null;
        }

    }

    private class Asynctask extends AsyncTask<Integer, Void, Boolean> {
        private Context mContext;

        public Asynctask(Context context) {
            mContext = context;
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
//            mCoords.setText("Latitude: " + locationAdapter.getmLatitudeText() + ",\n " + "Longitude: " + locationAdapter.getmLongitudeText());
//            mAddress.setText(locationAdapter.getAddress());
            dataHandler.setSingleString(weatherAdapter.getCurrentWeather(), 0);
            dataHandler.DefineStrings(weatherAdapter.getFutureWeather());
            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            spinner.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
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
        if (countryName == null){return cityName + "\n" + stateName;}
        return cityName + "\n" + stateName + ", " + countryName;
    }

    public void checkAsynctask(View view) {
//        spinner.setVisibility(View.VISIBLE);
        if (mLastLocation != null) {
            new SwipeActivity.Asynctask(getApplicationContext()).execute(0);
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
}
