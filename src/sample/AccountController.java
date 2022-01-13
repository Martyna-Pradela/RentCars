package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountController implements Initializable {

    @FXML
    Button findCar, reservations, showCar;
    @FXML
    private ImageView logout, profil, image2, image3, image4;

    public void goToFindCar(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("find.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Znajdź samochód");
            loginStage.setScene(new Scene(root, 550, 400));
            loginStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void goToMyReservations(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("my_reservations.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Moje rezerwacje");
            loginStage.setScene(new Scene(root, 550, 440));
            loginStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void showCars(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("show_cars.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Wszystkie samochody");
            loginStage.setScene(new Scene(root, 550, 440));
            loginStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File bfile = new File("image/logout.png");
        javafx.scene.image.Image image = new Image(bfile.toURI().toString());
        logout.setImage(image);

        File file = new File("image/profil.png");
        javafx.scene.image.Image image20 = new Image(file.toURI().toString());
        profil.setImage(image20);

        File fileImage1 = new File("image/image2.jpg");
        javafx.scene.image.Image imageImage1 = new Image(fileImage1.toURI().toString());
        image2.setImage(imageImage1);

        File fileImage4 = new File("image/image4.jpg");
        javafx.scene.image.Image imageImage4 = new Image(fileImage4.toURI().toString());
        image4.setImage(imageImage4);

        File fileImage3 = new File("image/image3.jpg");
        javafx.scene.image.Image imageImage3 = new Image(fileImage3.toURI().toString());
        image3.setImage(imageImage3);

        logout.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try{
                    Parent root = FXMLLoader.load(getClass().getResource("main_page.fxml"));
                    Stage loginStage = new Stage();
                    loginStage.setTitle("AutoRent");
                    loginStage.setScene(new Scene(root, 550, 400));
                    loginStage.show();
                }catch(Exception e){
                    e.printStackTrace();
                }
                Stage stage = (Stage) logout.getScene().getWindow();
                stage.close();
            }
        });

        profil.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try{
                    Parent root = FXMLLoader.load(getClass().getResource("profil.fxml"));
                    Stage loginStage = new Stage();
                    loginStage.setTitle("Profil");
                    loginStage.setScene(new Scene(root, 400, 300));
                    loginStage.show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}
