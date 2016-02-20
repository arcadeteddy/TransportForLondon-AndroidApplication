package ca.ubc.cs.cpsc210.mindthegap.TfL;

import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Set;

/**
 * Wrapper for TfL Arrival Data Provider
 */
public class TfLHttpArrivalDataProvider extends AbstractHttpDataProvider {
    private Station stn;

    public TfLHttpArrivalDataProvider(Station stn) {
        super();
        this.stn = stn;
    }

    @Override
    /**
     * Produces URL used to query TfL web service for expected arrivals at
     * station specified in call to constructor.
     *
     * @returns URL to query TfL web service for arrival data
     */
    protected URL getURL() throws MalformedURLException {
        // Get station ID and line IDs! //
        String lineIds = "";
        String stopPointId = stn.getID();
        // Set station ID and line IDs! //
        Set<Line> lines = stn.getLines();
        for (Line line : lines) {
            if (lineIds.equals("")) {
                lineIds = line.getId();
            } else {
                lineIds = lineIds + "," + line.getId();
            }
        }
        // Update request string and return it! //
        String request = "https://api.tfl.gov.uk/Line/" + lineIds + "/Arrivals?stopPointId=" + stopPointId;
        return new URL(request);
    }
}
