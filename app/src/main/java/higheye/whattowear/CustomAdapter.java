package higheye.whattowear;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static higheye.whattowear.R.drawable.clothes1_down;
import static higheye.whattowear.R.drawable.clothes1_up;
import static higheye.whattowear.R.drawable.clothes2_down;
import static higheye.whattowear.R.drawable.clothes2_up;
import static higheye.whattowear.R.drawable.clothes3_down;
import static higheye.whattowear.R.drawable.clothes3_up;
import static higheye.whattowear.R.drawable.clothes4_down;
import static higheye.whattowear.R.drawable.clothes4_up;
import static higheye.whattowear.R.drawable.clothes5_down;
import static higheye.whattowear.R.drawable.clothes5_up;
import static higheye.whattowear.R.drawable.clothes6_down;
import static higheye.whattowear.R.drawable.clothes6_up;
import static higheye.whattowear.R.drawable.clothes7_down;
import static higheye.whattowear.R.drawable.clothes7_up;
import static higheye.whattowear.R.drawable.ic_sunglasses;
import static higheye.whattowear.R.drawable.ic_umbrella;
import static higheye.whattowear.R.drawable.na;
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

/**
 * Created by marekk-air13 on 22/06/2017.
 */

class CustomAdapter extends ArrayAdapter<DataObject> {
    private ArrayList<DataObject> entries;
    private LayoutInflater mInflater;
    private int mViewResourceId;
    private int unit;

    public CustomAdapter(Context context, int textViewResourceId, ArrayList<DataObject> entries, int unit) { // unit: 0 = C, 1 = F
        super(context, R.layout.custom_row, entries);
        this.entries = entries;
        this.unit = unit;
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
        ImageView icon_clothes_up = (ImageView) convertView.findViewById(R.id.icon_clothes_up);
        ImageView icon_clothes_down = (ImageView) convertView.findViewById(R.id.icon_clothes_down);
        ImageView umbrella = (ImageView) convertView.findViewById(R.id.icon_umbrella);
        ImageView sunglasses = (ImageView) convertView.findViewById(R.id.icon_sunglasses);

if (dataObject.getSingleDate() != -9999l){



        if (dataObject.getSingleDate() == 10l) {
            datetime.setText(R.string.now);
        } else if (datetime != null) {
            datetime.setText("" + getDateFromUnix(dataObject.getSingleDate()));
        }

        if (temp != null) {
            if (unit == 1) {
                double tempF = (dataObject.getSingleTemp() * 9 / 5) + 32;
                temp.setText(Math.round(tempF) + "\u00b0F");
            } else {
                temp.setText(Math.round(dataObject.getSingleTemp()) + "\u00b0C");

          //      temp.setText(getCorrentSunsetSunrise(dataObject.getSingleSunrise(), dataObject.getSingleDate())+"\n"+dataObject.getSingleDate()*1000+"\n"+getCorrentSunsetSunrise(dataObject.getSingleSunset(), dataObject.getSingleDate()));
            }
        }
        if (clouds != null && dataObject.getSingleCloud()!=0) {
          //  clouds.setText(getContext().getString(R.string.clouds) + String.format("%.2f", dataObject.getSingleCloud()));
        }
        if (clouds != null && dataObject.getSingleRain()!=0) {
            rain.setText(getContext().getString(R.string.rain)+"\n" + String.format("%.2f", dataObject.getSingleRain()));
        }
        if (clouds != null && dataObject.getSingleSnow()!=0) {
            snow.setText(getContext().getString(R.string.snow)+"\n" + String.format("%.2f", dataObject.getSingleSnow()));
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
        if (icon_clothes_up != null) {
            double factor = dataObject.getSingleTemp();
            if (dataObject.getSingleCloud() < 50) {
                factor++;
            }
            if (dataObject.getSingleRain() >= 3) {
                factor--;
            }
            if (25 <= factor) {
                icon_clothes_up.setImageResource(clothes1_up);
            } else if (22 <= factor && factor < 25) {
                icon_clothes_up.setImageResource(clothes2_up);
            } else if (20 <= factor && factor < 22) {
                icon_clothes_up.setImageResource(clothes3_up);
            } else if (17 <= factor && factor < 20) {
                icon_clothes_up.setImageResource(clothes4_up);
            } else if (13 <= factor && factor < 17) {
                icon_clothes_up.setImageResource(clothes5_up);
            } else if (3 <= factor && factor < 13) {
                icon_clothes_up.setImageResource(clothes6_up);
            } else if (factor < 3) {
                icon_clothes_up.setImageResource(clothes7_up);
            } else {
                icon_clothes_up.setImageResource(na);
            }
        }
        if (icon_clothes_down != null) {
            double factor = dataObject.getSingleTemp();
            if (dataObject.getSingleCloud() < 50) {
                factor++;
                // check if time is during the day, not night.
                if (dataObject.getSingleDate()*1000>(getCorrentSunsetSunrise(dataObject.getSingleSunrise(), dataObject.getSingleDate()))&&dataObject.getSingleDate()*1000<(getCorrentSunsetSunrise(dataObject.getSingleSunset(), dataObject.getSingleDate())))
                {sunglasses.setImageResource(ic_sunglasses);}
            }
            if (dataObject.getSingleRain() >= 2) {
                factor--;
                umbrella.setImageResource(ic_umbrella);
            }
            if (25 <= factor) {
                icon_clothes_down.setImageResource(clothes1_down);
            } else if (21 <= factor && factor < 25) {
                icon_clothes_down.setImageResource(clothes2_down);
            } else if (20 <= factor && factor < 21) {
                icon_clothes_down.setImageResource(clothes3_down);
            } else if (16 <= factor && factor < 20) {
                icon_clothes_down.setImageResource(clothes4_down);
            } else if (10 <= factor && factor < 16) {
                icon_clothes_down.setImageResource(clothes5_down);
            } else if (2 <= factor && factor < 10) {
                icon_clothes_down.setImageResource(clothes6_down);
            } else if (factor < 2) {
                icon_clothes_down.setImageResource(clothes7_down);
            } else {
                icon_clothes_down.setImageResource(na);
            }

        }

                                    /*
25 <= temp			szorty, podkoszulek						clothes1
21 <= temp < 25		szorty, koszulka				   		clothes2
20 <= temp < 21		dlugie spodnie, koszulka		    	clothes3
15 <= temp < 20		dlugie spodnie, bluza			    	clothes4
10 <= temp < 15		dlugie spodnie, kurtka			    	clothes5
3 <= temp < 10		dlugie spodnie, kurtka, czapka			clothes6
temp < 3			dlugie spodnie, kurtka zimowa, czapka	clothes7
         */
}
else {
    datetime.setText(R.string.no_data);
}
if (position%2==0){
    convertView.setBackgroundResource(R.color.list_background1);
}
else {
    convertView.setBackgroundResource(R.color.list_background2);
}
        return convertView;
    }

    public static int getClothes(Double temp, Double clouds, Double rain) {
        int icon_clothes = 0;
        if (clouds < 50) {
            temp++;
        }
        if (rain >= 3) {
            temp--;
        }
        if (25 <= temp) {
            icon_clothes = 1;
        } else if (21 <= temp && temp < 25) {
            icon_clothes = 2;
        } else if (20 <= temp && temp < 21) {
            icon_clothes = 3;
        } else if (16 <= temp && temp < 20) {
            icon_clothes = 4;
        } else if (10 <= temp && temp < 16) {
            icon_clothes = 5;
        } else if (2 <= temp && temp < 10) {
            icon_clothes = 6;
        } else if (temp < 2) {
            icon_clothes = 7;
        }
        return icon_clothes;
    }

    public String getDateFromUnix(long unixtime) {
        String today = getContext().getString(R.string.today);
        String tomorrow = getContext().getString(R.string.tomorrow);
        Date date = new Date(unixtime * 1000L); // *1000 is to convert seconds to milliseconds
        //current day of the year
        Calendar now = Calendar.getInstance();
        int nowDay = now.get(Calendar.DAY_OF_YEAR);
//      forecast day of the year
        SimpleDateFormat forecastDayDate = new SimpleDateFormat("D");
        int forecastDay = (Integer.parseInt(forecastDayDate.format(date)));
//      format current time and date
        SimpleDateFormat dayWeekForecast = new SimpleDateFormat("EEE");
        SimpleDateFormat dayMonthForecast = new SimpleDateFormat("d. MMM");
        String formatedTime = SimpleDateFormat.getTimeInstance(3).format(date);
        String formatedDayWeek = dayWeekForecast.format(date);
        String formatedDayMonth = dayMonthForecast.format(date);
//        return nowDay+", "+forecastDay;
        if (nowDay == forecastDay) {
            return today + ", " + formatedDayMonth + ", " + formatedTime;
        }
        if (nowDay + 1 == forecastDay) {
            return tomorrow + ", " + formatedDayMonth + ", " + formatedTime;
        } else {
            return formatedDayWeek + ", " + formatedDayMonth + ", " + formatedTime;
        }
    }
    public Long getCorrentSunsetSunrise(Long sunsetOrSunrise, Long currentDay)
    {
        Long currentSusnetSunrise;
        Date thisDay = new Date(currentDay*1000);
        Date lastSunsetSunrise = new Date(sunsetOrSunrise*1000);
        SimpleDateFormat dayOfYear = new SimpleDateFormat("D");
        int currentDayOfYear = Integer.parseInt(dayOfYear.format(thisDay));
        int lastSusnetSunriseOfYear = Integer.parseInt(dayOfYear.format(lastSunsetSunrise));
        currentSusnetSunrise = (currentDayOfYear-lastSusnetSunriseOfYear)*TimeUnit.DAYS.toMillis(1)+sunsetOrSunrise*1000;
        return currentSusnetSunrise;
    }
}
