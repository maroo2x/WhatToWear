package higheye.whattowear;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.security.AccessController.getContext;

/**
 * Created by marekk-air13 on 04/06/2017.
 */

public class DataHandler {
    static String callback = "";

    public static String Networking(final String address) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
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
                        callback = response.toString();
 //
                    } finally {
                        connection.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return callback;
    }
}

