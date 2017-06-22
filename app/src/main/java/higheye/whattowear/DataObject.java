package higheye.whattowear;

/**
 * Created by marekk-air13 on 22/06/2017.
 */

public class DataObject {
    private static Long data;
    private static Double temp;
    private static Double cloud;
    private static Double rain;
    private static Double snow;
    private static String icon;

    public DataObject (Long data, Double temp, Double cloud, Double rain, Double snow, String icon)
    {
        this.data = data;
        this.temp = temp;
        this.cloud = cloud;
        this.rain = rain;
        this.snow = snow;
        this.icon = icon;
    }

    public Long getSingleDate() {
        if (data != null) {
            return data;
        } else return 0l;
    }

    public Double getSingleTemp() {
        if (temp != null) {
            return temp;
        } else return 0d;
    }

    public Double getSingleCloud() {
        if (cloud != null) {
            return cloud;
        } else return 0d;
    }

    public Double getSingleRain() {
        if (rain != null) {
            return rain;
        } else return 0d;
    }

    public Double getSingleSnow() {
        if (snow != null) {
            return snow;
        } else return 0d;
    }

    public String getSingleIcon() {
        if (icon != null) {
            return "z"+icon;
        } else return null;
    }
}
