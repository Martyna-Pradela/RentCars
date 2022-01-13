package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button closeb, loginb;
    @FXML
    private Label loginMessage;
    @FXML
    private ImageView logo;
    @FXML
    private TextField logintf;
    @FXML
    private PasswordField passwordpf;

    public void loginButtonOnAction(ActionEvent event){
        if(logintf.getText().isBlank()==false && passwordpf.getText().isBlank()==false){
            validateLogin();
        }else{
            loginMessage.setText("Wprowadź login i hasło");
        }
    }

    public void cancelButtonOnAction(ActionEvent event){
        goToMainPage();
        Stage stage = (Stage) closeb.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File bfile = new File("image/car.png");
        Image image = new Image(bfile.toURI().toString());
        logo.setImage(image);
    }

    public void goToMainPage(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("main_page.fxml"));
            Stage mainStage = new Stage();
            mainStage.setTitle("AutoRent");
            mainStage.setScene(new Scene(root, 550, 400));
            mainStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void validateLogin(){
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "select * from persons where email = '" + logintf.getText() + "' and password = '" + passwordpf.getText() + "'";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(verifyLogin);

            while(resultSet.next()) {
                if(resultSet.getString(4).equals(logintf.getText()) && resultSet.getString(5).equals(passwordpf.getText())){
                    try{
                        Parent root = FXMLLoader.load(getClass().getResource("account.fxml"));
                        Stage mainStage = new Stage();
                        mainStage.setTitle("AutoRent");
                        mainStage.setScene(new Scene(root, 550, 400));
                        mainStage.show();
                        Person p = new Person(logintf.getText());
                        ReservationController.getData(p);
                        MyReservationsController.getData(p);
                        DeleteController.getData(p);
                        ProfilController.getData(p);
                        PasswordController.getData(p);
                        Stage stage = (Stage) loginb.getScene().getWindow();
                        stage.close();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
