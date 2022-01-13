package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class CancelController implements Initializable {

    static String datew, datez, mail, markP, modelP;
    @FXML
    private Label markC, modelC, dateWC, dateZC, error;
    @FXML
    private Button cancelButton;

    public static void dataToCancel(String date1, String date2, String email, String mark, String model){
        datew = date1;
        datez = date2;
        mail = email;
        markP = mark;
        modelP = model;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        markC.setText(markP);
        modelC.setText(modelP);
        dateWC.setText(datew);
        dateZC.setText(datez);
    }
    public void deleteData() {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        String timeNow = timeStamp.replaceAll("-","");
        int d1 = Integer.parseInt(timeNow);
        String timeW = datew.replaceAll("-","");
        int d2 = Integer.parseInt(timeW);
        String timeZ = datez.replaceAll("-","");
        int d3 = Integer.parseInt(timeZ);
        int days = d1-d2;
        if(d1>d2 && d1<d3){
            String sql = "update rented_cars set price = (price/days)*" +days + ", days="+days+", date2 = '"+timeStamp+"' where email = '" + mail + "' and date1 = '" + datew + "' and date2='" + datez + "' and model='" + modelP + "'";
            try {
                Statement statement = connectDB.createStatement();
                int resultSet = statement.executeUpdate(sql);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }else if(d1<d2 && d1<d3) {
            String sql = "delete from rented_cars where email = '" + mail + "' and date1 = '" + datew + "' and date2='" + datez + "' and model='" + modelP + "'";

            try {
                Statement statement = connectDB.createStatement();
                int resultSet = statement.executeUpdate(sql);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }else{
            error.setVisible(true);
        }
    }
    public void cancel(MouseEvent event){
        deleteData();
    }
}
