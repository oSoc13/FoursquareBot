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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

public class FoursquareAPI {

    private FoursquareApi foursquareApi;
    private Properties properties = new Properties();

    public void initialize() {

        String clientID;
        String clientSecret;
        String clientURL;

        try {
            properties.load(new FileInputStream("config.properties"));
            clientID = properties.getProperty("clientID");
            clientSecret = properties.getProperty("clientSecret");
            clientURL = properties.getProperty("clientURL");
            System.out.println("clientID = " + clientID);
            System.out.println("clientSecret = " + clientSecret);
            System.out.println("clientURL = " + clientURL);

            foursquareApi = new FoursquareApi(clientID, clientSecret, clientURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Venue Search
    public void searchVenues(String ll) throws FoursquareApiException {

        // After client has been initialized we can make queries.
        Result<VenuesSearchResult> result = foursquareApi.venuesSearch(ll, null, null, null, null, null, null, null, null, null, null);

        if (result.getMeta().getCode() == 200) {
            // if query was ok we can finally we do something with the data
            for (CompactVenue venue : result.getResult().getVenues()) {
                // TODO: Do something we the data
                System.out.println(venue.getName());
            }
        } else {
            // TODO: Proper error handling
            System.out.println("Error occured: ");
            System.out.println("  code: " + result.getMeta().getCode());
            System.out.println("  type: " + result.getMeta().getErrorType());
            System.out.println("  detail: " + result.getMeta().getErrorDetail());
        }
    }



    // Authentication
    public void authenticationRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            // First we need to redirect our user to authentication page.
            response.sendRedirect(foursquareApi.getAuthenticationUrl());
        } catch (IOException e) {
            // TODO: Error handling
        }
    }

    public void handleCallback(HttpServletRequest request, HttpServletResponse response) {
        // After user has logged in and confirmed that our program may access user's Foursquare account
        // Foursquare redirects user back to callback url.
        // Callback url contains authorization code
        String code = request.getParameter("code");
        try {
            // finally we need to authenticate that authorization code
            foursquareApi.authenticateCode(code);
            // ... and voilà we have a authenticated Foursquare client
        } catch (FoursquareApiException e) {
            // TODO: Error handling
        }
    }

}
