package ca.ubc.cs.cpsc210.mindthegap.ui;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ca.ubc.cs.cpsc210.mindthegap.R;
import ca.ubc.cs.cpsc210.mindthegap.model.Arrival;
import ca.ubc.cs.cpsc210.mindthegap.model.ArrivalBoard;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;
import ca.ubc.cs.cpsc210.mindthegap.model.StationManager;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Fragment to display list of arrivals at selected station for particular line
 * and particular platform (as specified by travel direction).
 */
public class ArrivalsListFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        String lineId = bundle.getString(getString(R.string.line_id_key));
        String travelDirn = bundle.getString(getString(R.string.travel_dirn_key));


        ArrayList<Arrival> arrivals = getArrivalsForSelectedStationOnLineInDirection(lineId, travelDirn);
        ArrivalsAdapter adapter = new ArrivalsAdapter(arrivals);

        setListAdapter(adapter);
    }

    /**
     * Get arrivals for selected station on line with given ID at platform with given travel direction
     *
     * @param lineId   the line id
     * @param travelDirn   the travel direction
     * @return   list of arrivals at selected station on given line in given travel direction
     */
    public ArrayList<Arrival> getArrivalsForSelectedStationOnLineInDirection(String lineId, String travelDirn) {
        ArrayList<Arrival> listArrivals = new ArrayList<>();
        // Get selected station! //
        StationManager stnManager = StationManager.getInstance();
        Station selectedStation = stnManager.getSelected();
        // For each arrival board... //
        Iterator<ArrivalBoard> iteratorArrivalBoard = selectedStation.iterator();
        while (iteratorArrivalBoard.hasNext()) {
            // Get line ID and travel direction! //
            ArrivalBoard currentArrivalBoard = iteratorArrivalBoard.next();
            String currentLineId = currentArrivalBoard.getLine().getId();
            String currentTravelDirn = currentArrivalBoard.getTravelDirn();
            // If parameter line ID and parameter travel direction equals this arrival board's line ID and travel direction! //
            if (currentLineId.equals(lineId) && currentTravelDirn.equals(travelDirn)) {
                // For each arrival... //
                Iterator<Arrival> iteratorArrival = currentArrivalBoard.iterator();
                while (iteratorArrival.hasNext()) {
                    // Add arrival to the list of arrivals! //
                    Arrival currentArrival = iteratorArrival.next();
                    listArrivals.add(currentArrival);
                }
            }
        }
        return listArrivals;
    }

    /**
     * Array adapter for list of arrivals displayed to user
     */
    private class ArrivalsAdapter extends ArrayAdapter<Arrival> {
        public ArrivalsAdapter(ArrayList<Arrival> arrivals) {
            super(getActivity(), 0, arrivals);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.arrival_item, null);
            }

            Arrival arrival = getItem(position);
            TextView destination = (TextView) convertView.findViewById(R.id.destination);
            destination.setText(arrival.getDestination());
            TextView platform = (TextView) convertView.findViewById(R.id.platform);
            platform.setText(arrival.getPlatform());
            TextView waitTime = (TextView) convertView.findViewById(R.id.wait_time);
            waitTime.setText(Integer.toString(arrival.getTimeToStationInMins()) + " mins");

            return convertView;
        }
    }
}
