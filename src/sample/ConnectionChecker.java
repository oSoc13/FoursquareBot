package sample;

import java.net.InetAddress;

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
        /*InetAddress adr = InetAddress.getByName();

        while(true){
            while(!adr.isReachable(3000)){

            }

            api.checkInAt();

            while(adr.isReachable(3000)){

            }
        } */
    }
}
