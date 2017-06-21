package higheye.whattowear;

/**
 * Created by marekk-air13 on 04/06/2017.
 */

public class WeatherAdapter {
    private static String currentWeather;
    private static String futureWeather;


    public void setCurrentWeather(String currentWeather) {
        this.currentWeather = currentWeather;
    }

    public void setFutureWeather(String futureWeather) {
        this.futureWeather = futureWeather;
    }

    public String getCurrentWeather() {
        if (currentWeather != null) {
            return currentWeather;
        } else {
            return "no data";
        }
    }

    public String getFutureWeather() {
        if (futureWeather != null) {
            return futureWeather;
        } else {
            return "no data";
        }
    }
}
