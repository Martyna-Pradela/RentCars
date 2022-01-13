package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

    Person p = new Person();
    static String email;
    static String numer, markCar, modelCar, date1, date2, colorCar;
    static double priceCar;
    private double priceAll;
    Date d1, d2;
    @FXML
    private Label mark, price, datew, datez;
    @FXML
    private ImageView image;
    @FXML
    Button buttonOK;
    long days;
    int days2;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void getData(Person p){
        email = p.getEmail();
    }
    public static void numerOK(String num, String mark, String model, double price, String data1, String data2, String color){
        numer = num;
        markCar = mark;
        modelCar = model;
        priceCar = price;
        date1 = data1;
        date2 = data2;
        colorCar = color;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mark.setText(markCar +" " + modelCar);
        try {
            d1=sdf.parse(date1);
            d2=sdf.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        days = Math.round((d2.getTime() - d1.getTime()) / 86400000.0);
        days2 = (int) days;
        priceAll = priceCar * days;
        String priceCars = String.valueOf(priceAll);
        price.setText(priceCars);
        datew.setText(date1);
        datez.setText(date2);

        File bfile2 = new File("image/"+numer+".jpg");
        Image image2 = new Image(bfile2.toURI().toString());
        image.setImage(image2);

    }

    public void addToDataBase(MouseEvent event){
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();

        String insertFields = "insert into rented_cars (mark, model, color, price, date1, date2, num, email, days) values ('";
        String insertValue = markCar + "','" + modelCar + "','" + colorCar + "','" + priceAll + "','" +date1 + "','" + date2+"','" + numer + "','" +email  +"',"+days2+")";
        String insertToRegister = insertFields + insertValue;

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertToRegister);

        }catch(Exception e){
            e.printStackTrace();
        }
        Stage stage = (Stage) buttonOK.getScene().getWindow();
        stage.close();
    }
}
