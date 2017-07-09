package higheye.whattowear;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
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
 * Created by marekk-air13 on 06/07/2017.
 */

public class CurrentWeatherFragment extends Fragment // implements View.OnClickListener
{
    protected Activity mActivity;
    DataHandler dataHandler = new DataHandler();

    @Override
    public void onAttach(Activity act) {
        super.onAttach(act);
        mActivity = act;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_fragment_layout, container, false);
        int i = 0;
        TextView datetime = (TextView) view.findViewById(R.id.datetime);
        TextView temp = (TextView) view.findViewById(R.id.temp);
        TextView clouds = (TextView) view.findViewById(R.id.clouds);
        TextView rain = (TextView) view.findViewById(R.id.rain);
        TextView snow = (TextView) view.findViewById(R.id.snow);
        TextView address = (TextView) view.findViewById(R.id.address);
        ImageView icon_weather = (ImageView) view.findViewById(R.id.icon_weather);
        ImageView icon_clothes_up = (ImageView) view.findViewById(R.id.icon_clothes_up);
        ImageView icon_clothes_down = (ImageView) view.findViewById(R.id.icon_clothes_down);
        ImageView umbrella = (ImageView) view.findViewById(R.id.icon_umbrella);
        ImageView sunglasses = (ImageView) view.findViewById(R.id.icon_sunglasses);
        LocationAdapter locationAdapter = new LocationAdapter();
        address.setText(locationAdapter.getAddress());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
        boolean unit = preferences.getBoolean("unit",false);

        if (dataHandler.getSingleDate(i) != -9999l) {


            if (dataHandler.getSingleDate(i) == 10l) {
                datetime.setText(R.string.now);
            } else if (datetime != null) {
                datetime.setText("" + getDateFromUnix(dataHandler.getSingleDate(i)));
            }

            if (temp != null) {
                if (unit == true) {
                    double tempF = (dataHandler.getSingleTemp(i) * 9 / 5) + 32;
                    temp.setText(Math.round(tempF) + "\u00b0F");
                } else {
                    temp.setText(Math.round(dataHandler.getSingleTemp(i)) + "\u00b0C");

                    //      temp.setText(getCorrentSunsetSunrise(dataObject.getSingleSunrise(), dataObject.getSingleDate())+"\n"+dataObject.getSingleDate()*1000+"\n"+getCorrentSunsetSunrise(dataObject.getSingleSunset(), dataObject.getSingleDate()));
                }
            }
            if (clouds != null && dataHandler.getSingleCloud(i) != 0) {
                //  clouds.setText(getContext().getString(R.string.clouds) + String.format("%.2f", dataObject.getSingleCloud()));
            }
            if (clouds != null && dataHandler.getSingleRain(i) != 0) {
                rain.setText(getContext().getString(R.string.rain) + "\n" + String.format("%.2f", dataHandler.getSingleRain(i)));
            }
            if (clouds != null && dataHandler.getSingleSnow(i) != 0) {
                snow.setText(getContext().getString(R.string.snow) + "\n" + String.format("%.2f", dataHandler.getSingleSnow(i)));
            }
            if (icon_weather != null) {
                switch ("z"+dataHandler.getSingleIcon(i)) {
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
                double factor = dataHandler.getSingleTemp(i);
                if (dataHandler.getSingleCloud(i) < 50) {
                    factor++;
                }
                if (dataHandler.getSingleRain(i) >= 3) {
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
                double factor = dataHandler.getSingleTemp(i);
                if (dataHandler.getSingleCloud(i) < 50) {
                    factor++;
                    // check if time is during the day, not night.
                    if (dataHandler.getSingleDate(i) * 1000 > (getCurrentSunsetSunrise(dataHandler.getSingleSunrise(i), dataHandler.getSingleDate(i))) && dataHandler.getSingleDate(i) * 1000 < (getCurrentSunsetSunrise(dataHandler.getSingleSunset(i), dataHandler.getSingleDate(i)))) {
                        sunglasses.setImageResource(ic_sunglasses);
                    }
                }
                if (dataHandler.getSingleRain(i) >= 2) {
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
        } else {
            datetime.setText(R.string.no_data);
        }


        return view;
    }

    public void fillFieldsCurrent(){

    }

    // previous style: displaying as a list
/*
    @Override
    public void onClick(View v) {
        //do whatever you want here
    }
*/

    public String getDateFromUnix(long unixtime) {
//        String today = getContext().getString(R.string.today);
//        String tomorrow = getContext().getString(R.string.tomorrow);
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


        return getContext().getString(R.string.now)+"\n"+formatedDayWeek+", " + formatedDayMonth;
 /*
        if (nowDay == forecastDay) {

            return today + ", " + formatedDayMonth + ", " + formatedTime;
        }
        if (nowDay + 1 == forecastDay) {
            return tomorrow + ", " + formatedDayMonth + ", " + formatedTime;
        } else {
            return formatedDayWeek + ", " + formatedDayMonth + ", " + formatedTime;
        }
        */
    }

    public Long getCurrentSunsetSunrise(Long sunsetOrSunrise, Long currentDay) {
        Long currentSusnetSunrise;
        Date thisDay = new Date(currentDay * 1000);
        Date lastSunsetSunrise = new Date(sunsetOrSunrise * 1000);
        SimpleDateFormat dayOfYear = new SimpleDateFormat("D");
        int currentDayOfYear = Integer.parseInt(dayOfYear.format(thisDay));
        int lastSusnetSunriseOfYear = Integer.parseInt(dayOfYear.format(lastSunsetSunrise));
        currentSusnetSunrise = (currentDayOfYear - lastSusnetSunriseOfYear) * TimeUnit.DAYS.toMillis(1) + sunsetOrSunrise * 1000;
        return currentSusnetSunrise;
    }
}