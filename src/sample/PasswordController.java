package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class PasswordController implements Initializable {

    @FXML
    private PasswordField oldPasswordfx, newPasswordfx, newPassword2fx;
    @FXML
    private Label error;
    @FXML
    private Button okB;
    private String newPassword, oldPassword;
    static String email;

    public static void getData(Person p){
        email = p.getEmail();
    }

    public void changeOk(MouseEvent event){
        changePassword();
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
                oldPassword = resultSet.getString("password");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void changePassword(){
        if(oldPassword.equals(oldPasswordfx.getText())){
            if(newPasswordfx.getText().equals(newPassword2fx.getText())){
                error.setVisible(false);
                DataBaseConnection connectNow = new DataBaseConnection();
                Connection connection = connectNow.getConnection();

                String sql = "update persons set password ='"+newPasswordfx.getText()+"' where email ='" + email + "'";

                try {
                    Statement statement = connection.createStatement();
                    int resultSet = statement.executeUpdate(sql);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Stage stage = (Stage) okB.getScene().getWindow();
                stage.close();

            }else{
                error.setVisible(true);
                error.setText("Wpisano dwa różna hasła");
            }
        }else{
            error.setVisible(true);
            error.setText("Wprowadzono złe aktualne hasło");
        }
    }
}
