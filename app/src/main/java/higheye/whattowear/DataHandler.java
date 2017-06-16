package higheye.whattowear;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ListView;

import com.google.android.gms.location.LocationRequest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.String;

import static android.R.id.content;
import static android.R.id.input;
import static java.security.AccessController.getContext;

/**
 * Created by marekk-air13 on 04/06/2017.
 */
public class DataHandler {
    /*   LocationAdapter locationAdapter = new LocationAdapter();
       WeatherAdapter weatherAdapter = new WeatherAdapter();
       public synchronized void Networking(final String address, final int type) {
           Thread thread = new Thread(new Runnable() {
               @Override
               public synchronized void run() {
                   try {
                       URL url = new URL(address);
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
                           WeatherAdapter weatherAdapter = new WeatherAdapter();
                           if (type == 0) {
                               weatherAdapter.setCurrentWeather(response.toString());
                           } else if (type == 1) {
                               weatherAdapter.setFutureWeather(response.toString());
                           }
                       } finally {
                           connection.disconnect();
                       }
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
                   notifyAll();
               }
           });
           thread.start();

       }

       public synchronized void determineWeather(int flag) {
           if (flag == 0) {
               Networking("http://api.openweathermap.org/data/2.5/weather?lat=" + locationAdapter.getmLatitudeText() + "&lon=" + locationAdapter.getmLongitudeText() + "&units=metric&appid=7e7469bd4b8aec9b7684f7b5dd63d3b5", flag);

           } else if (flag == 1) {
               Networking("http://api.openweathermap.org/data/2.5/forecast?lat=" + locationAdapter.getmLatitudeText() + "&lon=" + locationAdapter.getmLongitudeText() + "&units=metric&appid=06e65c7536988468e72a018ff2e8cf9b", flag);
           }
       }
   */
    private static String[] listOfEntries = new String[50];

    public void DefineStrings(String input) {
        int index = input.indexOf("\"dt_txt\"");
        int i = 1;
        while (index != -1 && i<49) {
            listOfEntries[i] = input.substring(0, index+31);
            input = input.substring(index+32, input.length());
            i++;
            index = input.indexOf("\"dt_txt\"");
        }
    }

    public void setSingleString(String input, int n){
listOfEntries[n] = input;
    }

    public String getSingleString(int n){
        return listOfEntries[n];
    }

    public static String getTemperature(String input) {
        int index = input.indexOf("\"temp\":");
        if (index == -1) {
            return "no data";
        }
        String cutString = input.substring(index);
        int newIndex = cutString.indexOf(",");
        return cutString.substring(7, newIndex);
    }

    public static String getClouds(String input) {
        int index = input.indexOf("\"all\":");
        if (index == -1) {
            return "no data";
        }
        String cutString = input.substring(index);
        int newIndex = cutString.indexOf("}");
        return cutString.substring(6, newIndex);
    }

    public static String getIcon(String input) {
        int index = input.indexOf("\"icon\":");
        if (index == -1) {
            return "no data";
        }
        String cutString = input.substring(index);
        int newIndex = cutString.indexOf("}");
        return cutString.substring(8, newIndex - 1);
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