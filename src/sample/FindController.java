package sample;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class FindController implements Initializable {

    @FXML
    ChoiceBox<String> town = new ChoiceBox<>();
    @FXML
    private Button showCar, closeB;
    @FXML
    ChoiceBox<String> townz = new ChoiceBox<>();
    @FXML
    DatePicker date, datez;
    @FXML
    private Label error;
    String date1, date2;

    private String[] allTown = {"Wrocław", "Kraków", "Warszawa", "Poznań", "Katowice", "Gdańsk"};

    ShowFreeCarsController sfc = new ShowFreeCarsController();

    public void goToShowFreeCars(MouseEvent event) throws ParseException {
        date1 = date.getValue().toString();
        date2 = datez.getValue().toString();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        String day = timeStamp.substring(8,10);
        int day2 = Integer.parseInt(day) -1;
        String day3 = String.valueOf(day2);
        String timeStamp2 = timeStamp.substring(0,8);
        timeStamp = timeStamp2 + day3;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdf.parse(date1);
        Date d2 = sdf.parse(date2);
        Date d3 = sdf.parse(timeStamp);
        if(d2.before(d1)) {
            error.setVisible(true);
            error.setText("Data zwrotu musi być po dacie odbioru");
        }else if(d2.after(d1)){
            if(d1.after(d3) && d2.after(d3)) {
                error.setVisible(false);
                ShowFreeCarsController.dates(date1, date2);
                off();
                Stage stage = (Stage) showCar.getScene().getWindow();
                stage.close();
            }else{
                error.setVisible(true);
                error.setText("Samochód można wypożyczać od dzisiejszej daty");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        town.getItems().addAll(allTown);
        townz.getItems().addAll(allTown);

    }
    public void off(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("show_free_cars.fxml"));
            Stage mainStage = new Stage();
            mainStage.setTitle("AutoRent");
            mainStage.setScene(new Scene(root, 550, 440));
            mainStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void closeFind(MouseEvent event){
        Stage stage = (Stage) closeB.getScene().getWindow();
        stage.close();
    }
}
