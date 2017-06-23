package higheye.whattowear;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static higheye.whattowear.R.drawable.abc_btn_default_mtrl_shape;
import static higheye.whattowear.R.drawable.z01d;
import static higheye.whattowear.R.drawable.z01n;
import static higheye.whattowear.R.drawable.z02d;
import static higheye.whattowear.R.drawable.z02n;
import static higheye.whattowear.R.drawable.z03d;
import static higheye.whattowear.R.drawable.z03n;
import static higheye.whattowear.R.drawable.z04d;
import static higheye.whattowear.R.drawable.z04n;
import static higheye.whattowear.R.drawable.z09d;
import static higheye.whattowear.R.drawable.z09n;
import static higheye.whattowear.R.drawable.z10d;
import static higheye.whattowear.R.drawable.z10n;
import static higheye.whattowear.R.drawable.z11d;
import static higheye.whattowear.R.drawable.z11n;
import static higheye.whattowear.R.drawable.z13d;
import static higheye.whattowear.R.drawable.z13n;
import static higheye.whattowear.R.drawable.z50d;
import static higheye.whattowear.R.drawable.z50n;
import static higheye.whattowear.R.drawable.na;

/**
 * Created by marekk-air13 on 22/06/2017.
 */

class CustomAdapter extends ArrayAdapter<DataObject> {
    private ArrayList<DataObject> entries;
    private LayoutInflater mInflater;
    private int mViewResourceId;

    public CustomAdapter(Context context, int textViewResourceId, ArrayList<DataObject> entries) {
        super(context, R.layout.custom_row, entries);
        this.entries = entries;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }
        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            // default -  return super.getView(position, convertView, parent);
            convertView = mInflater.inflate(mViewResourceId, null);
            final DataObject dataObject = entries.get(position);
            TextView datetime = (TextView) convertView.findViewById(R.id.datetime);
            TextView temp = (TextView) convertView.findViewById(R.id.temp);
            TextView clouds = (TextView) convertView.findViewById(R.id.clouds);
            TextView rain = (TextView) convertView.findViewById(R.id.rain);
            TextView snow = (TextView) convertView.findViewById(R.id.snow);
            ImageView icon_weather = (ImageView) convertView.findViewById(R.id.icon_weather);


                if (dataObject.getSingleDate() == 10l) {
                    datetime.setText("now");
                }
                else if (datetime != null)
                {
                    datetime.setText(""+getDateFromUnix(dataObject.getSingleDate()));
                }

            if (temp != null) {
                temp.setText("temp: "+dataObject.getSingleTemp());
            }
            if (clouds != null) {
                clouds.setText("clouds(%): "+dataObject.getSingleCloud());
            }
            if (clouds != null) {
                rain.setText("rain(mm/3h): "+dataObject.getSingleRain());
            }
            if (clouds != null) {
                snow.setText("snow(mm/3h): "+dataObject.getSingleSnow());
            }
            if (icon_weather != null) {
                switch (dataObject.getSingleIcon()) {
                    case "z01d":
                        icon_weather.setImageResource(z01d);
                        break;
                    case "z01n":
                        icon_weather.setImageResource(z01n);
                        break;
                    case "z02d":
                        icon_weather.setImageResource(z02d);
                        break;
                    case "z02n":
                        icon_weather.setImageResource(z02n);
                        break;
                    case "z03d":
                        icon_weather.setImageResource(z03d);
                        break;
                    case "z03n":
                        icon_weather.setImageResource(z03n);
                        break;
                    case "z04d":
                        icon_weather.setImageResource(z04d);
                        break;
                    case "z04n":
                        icon_weather.setImageResource(z04n);
                        break;
                    case "z09d":
                        icon_weather.setImageResource(z09d);
                        break;
                    case "z09n":
                        icon_weather.setImageResource(z09n);
                        break;
                    case "z10d":
                        icon_weather.setImageResource(z10d);
                        break;
                    case "z10n":
                        icon_weather.setImageResource(z10n);
                        break;
                    case "z11d":
                        icon_weather.setImageResource(z11d);
                        break;
                    case "z11n":
                        icon_weather.setImageResource(z11n);
                        break;
                    case "z13d":
                        icon_weather.setImageResource(z13d);
                        break;
                    case "z13n":
                        icon_weather.setImageResource(z13n);
                        break;
                    case "z50d":
                        icon_weather.setImageResource(z50d);
                        break;
                    case "z50n":
                        icon_weather.setImageResource(z50n);
                        break;
                    default:
 //                       icon_weather.setImageResource(na);
                        throw new IllegalArgumentException("No icon: " + dataObject.getSingleIcon());
                }
            }
            return convertView;
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
