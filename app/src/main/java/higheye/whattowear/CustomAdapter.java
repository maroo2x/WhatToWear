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
import static higheye.whattowear.R.drawable.clothes1;
import static higheye.whattowear.R.drawable.clothes2;
import static higheye.whattowear.R.drawable.clothes3;
import static higheye.whattowear.R.drawable.clothes4;
import static higheye.whattowear.R.drawable.clothes5;
import static higheye.whattowear.R.drawable.clothes6;
import static higheye.whattowear.R.drawable.clothes7;

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
            ImageView icon_clothes = (ImageView) convertView.findViewById(R.id.icon_clothes);


                if (dataObject.getSingleDate() == 10l) {
                    datetime.setText("now");
                }
                else if (datetime != null)
                {
                    datetime.setText(""+getDateFromUnix(dataObject.getSingleDate()));
                }

            if (temp != null) {
                temp.setText(dataObject.getSingleTemp()+"\u00b0");
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
                        icon_weather.setImageResource(na);
 //                       throw new IllegalArgumentException("No icon: " + dataObject.getSingleIcon());
                }
            }
            if (icon_clothes != null) {
                double factor = dataObject.getSingleTemp();
                if (dataObject.getSingleCloud() < 50) {factor++;}
                if (dataObject.getSingleRain() >= 3) {factor--;
                // set imageview "parasol"
                }
                if (25 <= factor) {
                    icon_clothes.setImageResource(clothes1);
                }
                else if (21 <= factor && factor < 25){
                    icon_clothes.setImageResource(clothes2);
                }
                else if (20 <= factor && factor < 21){
                    icon_clothes.setImageResource(clothes3);
                }
                else if (16 <= factor && factor < 20){
                    icon_clothes.setImageResource(clothes4);
                }
                else if (10 <= factor && factor < 16){
                    icon_clothes.setImageResource(clothes5);
                }
                else if (2 <= factor && factor < 10){
                    icon_clothes.setImageResource(clothes6);
                }
                else if (factor < 2){
                    icon_clothes.setImageResource(clothes7);
                }
                else{
                    icon_clothes.setImageResource(na); }
                        /*
25 <= temp			szorty, podkoszulek						clothes1
21 <= temp < 25		szorty, koszulka				   		clothes2
20 <= temp < 21		dlugie spodnie, koszulka		    	clothes3
16 <= temp < 20		dlugie spodnie, bluza			    	clothes4
10 <= temp < 16		dlugie spodnie, kurtka			    	clothes5
2 <= temp < 10		dlugie spodnie, kurtka, czapka			clothes6
temp < 2			dlugie spodnie, kurtka zimowa, czapka	clothes7
         */
            }

            return convertView;
    }
    public static int getClothes(Double temp, Double clouds, Double rain){
        int icon_clothes = 0;
        if (clouds < 50) {temp++;}
        if (rain >= 3) {temp--;}
        if (25 <= temp) {
            icon_clothes = 1;
        }
        else if (21 <= temp && temp < 25){
            icon_clothes = 2;
        }
        else if (20 <= temp && temp < 21){
            icon_clothes = 3;
        }
        else if (16 <= temp && temp < 20){
            icon_clothes = 4;
        }
        else if (10 <= temp && temp < 16){
            icon_clothes = 5;
        }
        else if (2 <= temp && temp < 10){
            icon_clothes = 6;
        }
        else if (temp < 2){
            icon_clothes = 7;
        }

        return icon_clothes;
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
