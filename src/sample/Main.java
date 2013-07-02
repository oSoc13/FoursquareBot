package sample;

import fi.foyt.foursquare.api.FoursquareApiException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Vector;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

        FoursquareAPI fsAPI = new FoursquareAPI();

        // Authenticate user
        String code = "DPJ45L0LTEJ0BUKLJAEVKEEO3PWC55JIYJX5HGODR35HKQEH";
        fsAPI.authenticateClient();
        fsAPI.submitAccessCode(code);

        // Search for venues
        try {
            Vector<String> venues = fsAPI.getNearbyVenues("50.951983,5.348959");
            if (null != venues) {
                fsAPI.checkInAt("Mobile Vikings");
            }
            else {
            }
        } catch (FoursquareApiException e) {
            e.printStackTrace();
        }


    }
}
