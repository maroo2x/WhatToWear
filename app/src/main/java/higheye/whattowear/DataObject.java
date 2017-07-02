package higheye.whattowear;

/**
 * Created by marekk-air13 on 22/06/2017.
 */

public class DataObject {
    private Long data;
    private Double temp;
    private Double cloud;
    private Double rain;
    private Double snow;
    private String icon;
    private Long sunrise;
    private Long sunset;

    public DataObject (Long data, Double temp, Double cloud, Double rain, Double snow, String icon) {
        this.data = data;
        this.temp = temp;
        this.cloud = cloud;
        this.rain = rain;
        this.snow = snow;
        this.icon = icon;
    }

    public DataObject (Long data, Double temp, Double cloud, Double rain, Double snow, String icon, Long sunrise, Long sunset) {
        this.data = data;
        this.temp = temp;
        this.cloud = cloud;
        this.rain = rain;
        this.snow = snow;
        this.icon = icon;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public Long getSingleDate() {
        if (data != null) {
            return data;
        } else return -9999l;
    }

    public Double getSingleTemp() {
        if (temp != null) {
            return temp;
        } else return -9999d;
    }

    public Double getSingleCloud() {
        if (cloud != null) {
            return cloud;
        } else return -9999d;
    }

    public Double getSingleRain() {
        if (rain != null) {
            return rain;
        } else return -9999d;
    }

    public Double getSingleSnow() {
        if (snow != null) {
            return snow;
        } else return -9999d;
    }

    public String getSingleIcon() {
        if (icon != null) {
            return "z"+icon;
        } else return null;
    }
    public Long getSingleSunrise() {
        if (sunrise != null) {
            return sunrise;
        } else return -9999l;
    }
    public Long getSingleSunset() {
        if (sunset != null) {
            return sunset;
        } else return -9999l;
    }
}
