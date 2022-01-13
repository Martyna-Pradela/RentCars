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
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private Button closeb, saveb;
    @FXML
    private Label wrongPassword, allFields;
    @FXML
    private TextField firstnametf, lastnametf, peseltf, emailtf;
    @FXML
    private PasswordField passwordtf, password2tf;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File bfile = new File("image/car.png");
        Image image = new Image(bfile.toURI().toString());
        logo.setImage(image);
    }

    public void cancelButtonOnAction(ActionEvent event){
        goToMainPage();
        Stage stage = (Stage) closeb.getScene().getWindow();
        stage.close();
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

    public void registerUser(){
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();

        String firstName = firstnametf.getText();
        String lastName = lastnametf.getText();
        String pesel = peseltf.getText();
        String email = emailtf.getText();
        String password = password2tf.getText();
        String birth = pesel.substring(0,2) + "/" + pesel.substring(2,4) + "/" + pesel.substring(4,6);

        String insertFields = "insert into persons (name, lastname, pesel, email, password, birth) values ('";
        String insertValue = firstName + "','" + lastName + "','" + pesel + "','" + email + "','" +password + "','" + birth +"')";
        String insertToRegister = insertFields + insertValue;

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertToRegister);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void saveButtonOnAction(ActionEvent event){
        if(!passwordtf.getText().equals(password2tf.getText())) {
            wrongPassword.setVisible(true);
        }if(firstnametf.getText().isBlank()==false && lastnametf.getText().isBlank()==false && peseltf.getText().isBlank()==false && emailtf.getText().isBlank()==false && passwordtf.getText().isBlank()==false && password2tf.getText().isBlank()==false) {
            allFields.setVisible(false);
            if (passwordtf.getText().equals(password2tf.getText())) {
                wrongPassword.setVisible(false);
                registerUser();
                goToMainPage();
                Stage stage = (Stage) saveb.getScene().getWindow();
                stage.close();
            }
        }
            if (firstnametf.getText().isBlank() == true || lastnametf.getText().isBlank() == true || peseltf.getText().isBlank() == true || emailtf.getText().isBlank() == true || passwordtf.getText().isBlank() == true || password2tf.getText().isBlank() == true) {
                allFields.setVisible(true);
            }
    }
}
