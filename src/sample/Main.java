/**
 * @author: Thomas Stockx
 */

package sample;

import fi.foyt.foursquare.api.FoursquareApiException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.awt.event.ActionListener;

import java.util.Vector;

public class Main extends Application {
    private boolean firstTime;
    private TrayIcon trayIcon;
    public static FoursquareAPI fsAPI;

    @Override
    public void start(Stage primaryStage) throws Exception{
        createTrayIcon(primaryStage);
        firstTime = true;
        Platform.setImplicitExit(false);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        fsAPI = FoursquareAPI.getInstance();


        ConnectionChecker checker = new ConnectionChecker(fsAPI);
        checker.run();
        // Authenticate user
        //String code = "DPJ45L0LTEJ0BUKLJAEVKEEO3PWC55JIYJX5HGODR35HKQEH";
        //fsAPI.authenticateClient();
        //fsAPI.submitAccessCode(code);

        // Search for venues
        /*try {
            Vector<String> venues = fsAPI.getNearbyVenues("50.951983,5.348959");
            if (null != venues) {
                //fsAPI.checkInAt("Mobile Vikings");
            }
            else {
            }
        } catch (FoursquareApiException e) {
            e.printStackTrace();
        } */


    }


    public static void main(String[] args) {
        launch(args);
    }

    public void createTrayIcon(final Stage stage)
    {
        TrayIcon trayIcon = null;

        // SystemTray
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            Image image = Toolkit.getDefaultToolkit().getImage("res/icon-16x16.png");

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    hide(stage);
                }
            });

            final ActionListener closeListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.exit(0);
                }
            };

            ActionListener showListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.show();
                        }
                    });
                }
            };

            PopupMenu popup = new PopupMenu();

            MenuItem defaultItem = new MenuItem("Settings");
            defaultItem.addActionListener(showListener);
            popup.add(defaultItem);

            trayIcon = new TrayIcon(image, "FoursquareBot", popup);
            trayIcon.addActionListener(showListener);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
        }
        else {
            // disable tray
        }

    }

    private void hide(final Stage stage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (SystemTray.isSupported()) {
                    stage.hide();
                } else {
                    System.exit(0);
                }
            }
        });
    }
}
