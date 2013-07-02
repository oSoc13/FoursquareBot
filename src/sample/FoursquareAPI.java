/**
 * Author: Linsey Raymaekers
 * Date: 7/2/13
 * Time: 11:28 AM
 *
 * Description:
 *      Wrapper class for the Java Foursquare API:
 *      https://code.google.com/p/foursquare-api-java/
 */

package sample;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.Vector;
import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.entities.Checkin;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;
import org.json.JSONException;

public class FoursquareAPI {
    private static FoursquareAPI instance = null;
    private FoursquareApi foursquareApi;
    private Properties properties = new Properties();
    private CompactVenue venues[];
    private double lat;
    private double alt;

    public static FoursquareAPI getInstance() {
        if(instance == null) {
            instance = new FoursquareAPI();
        }
        return instance;
    }

    protected FoursquareAPI() {

        String clientID;
        String clientSecret;
        String clientURL;

        try {
            properties.load(new FileInputStream("config.properties"));
            clientID = properties.getProperty("clientID");
            clientSecret = properties.getProperty("clientSecret");
            clientURL = properties.getProperty("clientURL");

            foursquareApi = new FoursquareApi(clientID, clientSecret, clientURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Adapted from https://code.google.com/p/foursquare-api-java/wiki/BasicUsage
    // latlon String format: lat,lon     e.g. 50.951983,5.348959
    public Vector<String> getNearbyVenues(String latlon) throws FoursquareApiException {
        Result<VenuesSearchResult> result = foursquareApi.venuesSearch(latlon, null, null, null, null, null, null, null, null, null, null);

        System.out.println("Venue List:");
        if (result.getMeta().getCode() == 200) {
            // if query was ok we can finally we do something with the data
            venues = result.getResult().getVenues();
            Vector<String> venueNames = new Vector<String>(venues.length);
            for (CompactVenue venue : venues) {
                System.out.println(venue.getName());
                venueNames.add(venue.getName());
            }

            return venueNames;
        } else {
            System.out.println("FoursquareAPI.searchVenues(): Error occured: ");
            System.out.println("  code: " + result.getMeta().getCode());
            System.out.println("  type: " + result.getMeta().getErrorType());
            System.out.println("  detail: " + result.getMeta().getErrorDetail());
            return null;
        }
    }



    // Adapted from https://code.google.com/p/foursquare-api-java/wiki/AuthenticationExample
    public void authenticateClient() {

        // First we need to redirect our user to authentication page.
        String authURL = foursquareApi.getAuthenticationUrl();
        openWebpage(authURL);
    }

    public static void submitAccessCode(String code) {
        // After user has logged in and confirmed that our program may access user's Foursquare account
        // Foursquare redirects user back to callback url which contains authorization code.
        try {
            // finally we need to authenticate that authorization code
            foursquareApi.authenticateCode(code);
            // ... and voilà we have a authenticated Foursquare client
        } catch (FoursquareApiException e) {
            // TODO: Error handling
        }
    }

    public static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void openWebpage(String url) {
        URL urlObject = null;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            openWebpage(urlObject.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void checkInAt(String venueName) {
        for (CompactVenue venue : venues) {
            if (venueName == venue.getName()) {
                String venueId = venue.getId();
                try {
                    Result<Checkin> result = foursquareApi.checkinsAdd(venueId, null, null, null, null, null, null, null);
                    if (null != result)
                        System.out.println("Check-in success!");
                    return;
                } catch (FoursquareApiException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Check-in failed.");
    }

}
