package sample;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ProfilController implements Initializable {

    static String email;
    String name, lastName, password;
    @FXML
    private Label namefx, last_namefx, mailfx;
    @FXML
    private Button closeB, passwordB, deleteB;

    public static void getData(Person p){
        email = p.getEmail();
    }

    public void closeProfil(MouseEvent event){
        Stage stage = (Stage) closeB.getScene().getWindow();
        stage.close();
    }
    public void changePassword(MouseEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("password.fxml"));
            Stage mainStage = new Stage();
            mainStage.setTitle("Zmiana hasła");
            mainStage.setScene(new Scene(root, 400, 300));
            mainStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void deleteAccount(MouseEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("delete.fxml"));
            Stage mainStage = new Stage();
            mainStage.setTitle("Usuwanie konta");
            mainStage.setScene(new Scene(root, 360, 150));
            mainStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();

        String sql = "select name, lastname, password from persons where email ='" + email + "'";
//
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                name = resultSet.getString("name");
                lastName = resultSet.getString("lastname");
                password = resultSet.getString("password");
            }
            namefx.setText(name);
            last_namefx.setText(lastName);
            mailfx.setText(email);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
