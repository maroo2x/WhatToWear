package higheye.whattowear;

/**
 * Created by marekk-air13 on 04/06/2017.
 */

public class WeatherAdapter {
    private String currentWeather;
    private String futureWeather;

    public void setCurrentWeather(String currentWeather){
        this.currentWeather = currentWeather;
    }
    public void setFutureWeather(String futureWeather){
        this.futureWeather = futureWeather;
    }
    public String getCurrentWeather(){
        return currentWeather;
    }
    public String getFutureWeather(){
        return futureWeather;
    }
}
