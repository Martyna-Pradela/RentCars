package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageConnection implements Initializable {

    @FXML
    private ImageView logo, st1, st2, st3, st4;
    @FXML
    private Label loginfx, registerfx, carsfx, contactfx;

    public void goToLogin(){
        try{
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage loginStage = new Stage();
                loginStage.setTitle("Logowanie");
                loginStage.setScene(new Scene(root, 550, 400));
                loginStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void goToContact(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("contact.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Kontakt");
            loginStage.setScene(new Scene(root, 550, 400));
            loginStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void goToRegister(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage registerStage = new Stage();
            registerStage.setTitle("Rejestracja");
            registerStage.setScene(new Scene(root, 400, 590));
            registerStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void goToShowCars(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("show_cars.fxml"));
            Stage registerStage = new Stage();
            registerStage.setTitle("Samochody");
            registerStage.setScene(new Scene(root, 550, 440));
            registerStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File bfile = new File("image/car.png");
        Image image = new Image(bfile.toURI().toString());
        logo.setImage(image);

        File bfile2 = new File("image/strzalka.png");
        Image image2 = new Image(bfile2.toURI().toString());
        st1.setImage(image2);
        loginfx.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                goToLogin();
                Stage stage = (Stage) loginfx.getScene().getWindow();
                stage.close();
            }
        });
        st1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                goToLogin();
                Stage stage = (Stage) st1.getScene().getWindow();
                stage.close();
            }
        });
        st2.setImage(image2);
        registerfx.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                goToRegister();
                Stage stage = (Stage) registerfx.getScene().getWindow();
                stage.close();
            }
        });
        st2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                goToRegister();
                Stage stage = (Stage) st2.getScene().getWindow();
                stage.close();
            }
        });
        st3.setImage(image2);
        carsfx.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                goToShowCars();
                Stage stage = (Stage) carsfx.getScene().getWindow();
                stage.close();
            }
        });
        st3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                goToShowCars();
                Stage stage = (Stage) st3.getScene().getWindow();
                stage.close();
            }
        });
        st4.setImage(image2);
        contactfx.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                goToContact();
                Stage stage = (Stage) contactfx.getScene().getWindow();
                stage.close();
            }
        });
        st4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                goToContact();
                Stage stage = (Stage) st4.getScene().getWindow();
                stage.close();
            }
        });
    }
}
