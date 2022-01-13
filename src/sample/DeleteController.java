package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DeleteController implements Initializable {
    @FXML
    private Button deleteB;
    static  String email;

    public static void getData(Person p){
        email = p.getEmail();
    }

    public void deleteAccount(MouseEvent event){
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();

        String sql = "delete from persons where email ='" + email + "'";
        String sql2 = "delete from rented_cars where email = '" + email + "'";

        try {
            Statement statement = connection.createStatement();
            int resultSet = statement.executeUpdate(sql);
            int resultSet2 = statement.executeUpdate(sql2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) deleteB.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
