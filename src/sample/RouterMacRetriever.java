package sample;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 02/07/13
 * Time: 12:19
 * To change this template use File | Settings | File Templates.
 */
public class RouterMacRetriever {

    String getMACAddress(){
        try {
            return getRouterMac(getRouterIP());
        } catch (IOException e) {
            System.out.println("Problem finding MAC address");
        }
        return null;
    }

    public static void main(String[] args)throws IOException {
        RouterMacRetriever retriever = new RouterMacRetriever();
        String mac = retriever.getMACAddress();
        System.out.println("Mac adress: " + mac);
    }

    private String getRouterMac(String routerIp) throws IOException {
        Process process;
        if (OSChecker.isMac()){
            String cmd[] = {
                    "/bin/sh",
                    "-c",
                    "arp " + routerIp
            };

            process = Runtime.getRuntime().exec(cmd);
            parseMACMac(getProcessResult(process));
        }else if(OSChecker.isWindows()){
            process = Runtime.getRuntime().exec("");
        }else if(OSChecker.isUnix()){
            process = Runtime.getRuntime().exec("netstat -rn | grep 0.0.0.0");
        }else{
            throw new RuntimeException("Unsupported OS");//bad runtime
        }

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(process.getInputStream()));

        String s;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }
        return "";
    }

    private String getRouterIP() throws IOException {
        String result = "";
        if (OSChecker.isMac()){
            String cmd[] = {
                    "/bin/sh",
                    "-c",
                    "netstat -rn | grep default"
            };

            Process process = Runtime.getRuntime().exec(cmd);
            result = parseIPMac(getProcessResult(process));
        }else if(OSChecker.isWindows()){
            Process process = Runtime.getRuntime().exec("");
        }else if(OSChecker.isUnix()){
            Process process = Runtime.getRuntime().exec("arp");
            return  parseMACLinux(getProcessResult(process));
        }else{
            throw new RuntimeException("Unsupported OS");//bad runtime
        }
        return result;
    }

    private String parseMACLinux(List<String> result) {
        String s = result.get(1);

        Matcher matcher = Pattern.compile(".*\\s(([0-9a-z]{1,2}:){5}[0-9a-z]{1,2})\\s.*").matcher(s);
        if(matcher.matches()){
            System.out.println(matcher.group(1));
            return matcher.group(1);
        }
        return null; // todo
    }

    private String parseIPMac(List<String> input) {
        String s = input.get(0);
        Matcher matcher = Pattern.compile(".*\\s(\\d{1,3}.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})\\s.*").matcher(s);
        if(matcher.matches()){
            System.out.println(matcher.group(1));
            return matcher.group(1);
        }else {
            throw new RuntimeException("No router ip found");
        }

    }

    private List<String> getProcessResult(Process process){
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(process.getInputStream()));
        List<String> resultList = new ArrayList<String>();

        String s;
        try {
            while((s = stdInput.readLine()) != null){
                resultList.add(s);
            }
        } catch (IOException e) {
            System.out.println("Problem reading command line output, exiting");
            throw new RuntimeException();
        }

        return resultList;
    }

    private String parseMACMac(List<String> result){
        String s = result.get(0);

        Matcher matcher = Pattern.compile(".*\\s(([0-9a-z]{1,2}:){5}[0-9a-z]{1,2})\\s.*").matcher(s);
        if(matcher.matches()){
            System.out.println(matcher.group(1));
            return matcher.group(1);
        }else {
            throw new RuntimeException("No router mac found");
        }
    }
}
