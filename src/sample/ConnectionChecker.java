package sample;

import fi.foyt.foursquare.api.FoursquareApiException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 02/07/13
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionChecker {
    FoursquareAPI api = null;
    public ConnectionChecker(FoursquareAPI api){
        this.api = api;
    }

    public void run(){
        InetAddress adr = null;
        try {
            adr = InetAddress.getByName("8.8.8.8");
        } catch (UnknownHostException e) {
            //todo
        }

        while(true){
            try {
                while(!adr.isReachable(3000)){

                }
            } catch (IOException e) {
                //todo
            }
            while(!api.userIsAuthenticated()){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            doCheckIn();

            try {
                while(adr.isReachable(3000)){

                }
            } catch (IOException e) {
                //todo
            }
        }
    }

    private void doCheckIn() {
        try {
            Vector<String> venues = api.getNearbyVenues("50.951983,5.348959");
            if (null != venues) {
                api.checkInAt("Mobile Vikings");
            }
            else {
            }
        } catch (FoursquareApiException e) {
            e.printStackTrace();
        }

    }
}
