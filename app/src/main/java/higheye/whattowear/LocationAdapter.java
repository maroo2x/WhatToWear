package higheye.whattowear;


/**
 * Created by marekk-air13 on 04/06/2017.
 */

public class LocationAdapter {
    private String _mLatitudeText;
    private String _mLongitudeText;

    public void setCoords(String mLatitudeText, String mLongitudeText) {
        this._mLatitudeText = mLatitudeText;
        this._mLongitudeText = mLongitudeText;
    }

    public String getmLatitudeText() {
        return _mLatitudeText;
    }

    public String getmLongitudeText() {
        return _mLongitudeText;
    }
}
