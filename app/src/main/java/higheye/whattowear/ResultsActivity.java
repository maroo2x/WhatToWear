package higheye.whattowear;

import android.app.VoiceInteractor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ResultsActivity extends AppCompatActivity {
    TextView textView;
    TextView textView2;
    WeatherAdapter weatherAdapter = new WeatherAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView.setText(weatherAdapter.getCurrentWeather());
        textView2.setText(weatherAdapter.getFutureWeather());

//        textView.setText("jedden");
//        textView2.setText("ddwa");
    }
}
