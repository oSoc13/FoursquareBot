package sample;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
        //check in logic
    }
}
