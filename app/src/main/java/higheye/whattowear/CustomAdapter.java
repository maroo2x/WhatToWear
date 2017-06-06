/* package higheye.whattowear;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by marekk-air13 on 06/06/2017.


public class CustomAdapter extends ArrayAdapter<String> {

    private ArrayList<String> entries;
    private LayoutInflater mInflater;
    private int mViewResourceId;
//    final String remove_entry_question = getContext().getString(R.string.remove_entry_question);
//    final String remove_entry_nr_1 = getContext().getString(R.string.remove_entry_nr_1);
//    final String remove_entry_nr_2 = getContext().getString(R.string.remove_entry_nr_2);


    public CustomAdapter(Context context, int textViewResourceId, ArrayList<String> entries) {
        super(context, R.layout.custom_row, entries);
        this.entries = entries;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;


    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // default -  return super.getView(position, convertView, parent);
        convertView = mInflater.inflate(mViewResourceId, null);

        final FuelDb fuelDb = entries.get(position);
        Date dt;
        TextView text1 = (TextView) convertView.findViewById(R.id.rowResult1);
        TextView text2 = (TextView) convertView.findViewById(R.id.rowResult2);
        TextView text3 = (TextView) convertView.findViewById(R.id.rowResult3);
        TextView text4 = (TextView) convertView.findViewById(R.id.full_id);
}
*/