package higheye.whattowear;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by marekk-air13 on 22/06/2017.
 */

class CustomAdapter extends ArrayAdapter<DataObject> {
    private ArrayList<DataObject> listObject;
    private LayoutInflater mInflater;
    private int mViewResourceId;

    public CustomAdapter(Context context, int textViewResourceId, ArrayList<DataObject> listObject) {
        super(context, R.layout.custom_row, listObject);
        this.listObject = listObject;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }
        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            // default -  return super.getView(position, convertView, parent);
            convertView = mInflater.inflate(mViewResourceId, null);
            final DataObject dataObject = listObject.get(position);
            TextView datetime = (TextView) convertView.findViewById(R.id.datetime);
            TextView temp = (TextView) convertView.findViewById(R.id.temp);
            TextView clouds = (TextView) convertView.findViewById(R.id.clouds);

//            if (datetime != null) {
              datetime.setText(""+getDateFromUnix(dataObject.getSingleDate()));
//            }
            if (temp != null) {
                temp.setText("temp: "+dataObject.getSingleTemp());
            }
            if (clouds != null) {
                clouds.setText("clouds: "+dataObject.getSingleCloud());
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
