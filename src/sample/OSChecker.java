package sample;

/**
 * Created with IntelliJ IDEA.
 * User: nik
 * Date: 02/07/13
 * Time: 11:39
 * To change this template use File | Settings | File Templates.
 */
public class OSChecker {

    public static String getOs(){
        return System.getProperty("os.name").toLowerCase();
    }

    public static boolean isWindows() {
        return (getOs().contains("win"));
    }

    public static boolean isMac() {
        return (getOs().contains("mac"));
    }

    public static boolean isUnix() {
        return (getOs().contains("nix") || getOs().contains("nux") || getOs().contains("aix"));
    }

    public static boolean isSolaris() {
        return (getOs().contains("sunos"));
    }
}
