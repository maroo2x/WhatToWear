package higheye.whattowear;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

//class CustomAdapter extends ArrayAdapter<WeatherData> {
//    private ArrayList fields;
//    String entries = null;
//    public CustomAdapter(Context context, int textViewResourceId, ArrayList<WeatherData> entries) {
//        super(context, R.layout.activity_results, entries);
//        this.entries = entries;
//        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mViewResourceId = textViewResourceId;


//    }
//}