package higheye.whattowear;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by marekk-air13 on 06/07/2017.
 */

public class ForecastFragment extends Fragment implements View.OnClickListener {

    protected Activity mActivity;
    ArrayList<DataObject> entries;
    DataHandler dataHandler = new DataHandler();
    DataObject dataObject;
    ListView list;
    int unit;
    CustomAdapter adapter;

    @Override
    public void onAttach(Activity act)
    {
        super.onAttach(act);
        mActivity = act;
    }

    @Override
    public void onCreate(Bundle saveInstanceState)
    {

        super.onCreate(saveInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState)
    {
        super.onActivityCreated(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forecast_fragment_layout, container, false);
        // new arraylist adapter
        entries = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            dataObject = new DataObject(dataHandler.getSingleDate(i), dataHandler.getSingleTemp(i), dataHandler.getSingleCloud(i), dataHandler.getSingleRain(i), dataHandler.getSingleSnow(i), dataHandler.getSingleIcon(i), dataHandler.getSingleSunrise(i), dataHandler.getSingleSunset(i));
            entries.add(i-1, dataObject);
        }

        adapter = new CustomAdapter(getActivity(), R.layout.custom_row, entries, unit); // unit: 0 = C, 1 = F
        list = (ListView)view.findViewById(R.id.list);
        list.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View v)
    {
        //do whatever you want here
    }
}
