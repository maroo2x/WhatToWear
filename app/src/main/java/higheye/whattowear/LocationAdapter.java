package higheye.whattowear;


/**
 * Created by marekk-air13 on 04/06/2017.
 */

public class LocationAdapter {
    private static String mLatitudeText;
    private static String mLongitudeText;
    private static String address;

    public void setCoords(String mLatitudeText, String mLongitudeText) {
        this.mLatitudeText = mLatitudeText;
        this.mLongitudeText = mLongitudeText;
    }

    public String getmLatitudeText() {
        return mLatitudeText;
    }

    public String getmLongitudeText() {
        return mLongitudeText;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress() {
        return address;
    }
}
