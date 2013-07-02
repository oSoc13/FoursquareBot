package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    // value will be injected by the FXMLLoader
    @FXML
    private javafx.scene.control.Button loginButton;


    @FXML
    private javafx.scene.control.Button buttonLogin;

    private javafx.scene.text.Text textPassword;


    public void initialize() {

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FoursquareAPI.getInstance().authenticateClient();
            }

        });

        buttonLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FoursquareAPI.submitAccessCode(textPassword.getText());
            }

        });


        // initialize your logic here: all @FXML variables will have been injected

    }
}
