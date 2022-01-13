package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ShowFreeCarsController implements Initializable {

    @FXML
    private TableView<ProductSearch> productTableView;
    @FXML
    private TableColumn<ProductSearch, Integer> num;
    @FXML
    private TableColumn<ProductSearch, String> mark;
    @FXML
    private TableColumn<ProductSearch, String> model;
    @FXML
    private TableColumn<ProductSearch, String> color;
    @FXML
    private TableColumn<ProductSearch, Double> price;
    @FXML
    private TableColumn<ProductSearch, String> rent;
    @FXML
    private Button close;
    @FXML
    private TextField search;

    ObservableList<ProductSearch> observableList = FXCollections.observableArrayList();

    static String data3, data4;
    String marka, modell, kolor, date1, date2;
    Integer numm;
    boolean availability;
    Date d1, d2, d3, d4;
    double priceCar;

    public void closeCars(){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    public void showImage(MouseEvent event){
        ProductSearch product = productTableView.getSelectionModel().getSelectedItem();
        numm = product.getNum();
        marka = product.getMark();
        modell = product.getModel();
        priceCar = product.getPrice();
        kolor = product.getColor();
        String name = String.valueOf(numm);
        ReservationController.numerOK(name, marka, modell,priceCar, data3, data4, kolor);
        try{
            Parent root = FXMLLoader.load(getClass().getResource("reservation.fxml"));
            Stage mainStage = new Stage();
            mainStage.setTitle(marka + " " + modell);
            mainStage.setScene(new Scene(root, 550, 400));
            mainStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Set<Integer> lista = new TreeSet<>();
    Set<Integer> busy = new TreeSet<>();


    public static void dates(String data1, String data2) {
        data3 = data1;
        data4 = data2;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();

        String sql = "select num, mark, model, color, price, rent from allcars";
        String sql2 = "select num, mark, model, color, date1, date2, price from rented_cars";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                numm = resultSet.getInt("num");
                lista.add(numm);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql2);

            while (resultSet.next()) {
                numm = resultSet.getInt("num");
                marka = resultSet.getString("mark");
                modell = resultSet.getString("model");
                kolor = resultSet.getString("color");
                date1 = resultSet.getString("date1");
                date2 = resultSet.getString("date2");

                try {
                    d1=sdf.parse(data3);
                    d2=sdf.parse(data4);
                    d3=sdf.parse(date1);
                    d4=sdf.parse(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(d3.before(d1) && d4.after(d2)){
                    availability = false;
                }else if(d1.before(d3) && d2.after(d4)){
                    availability = false;
                }else if(d1.before(d4) && d1.after(d3)){
                    availability = false;
                }else if(d2.after(d3) && d2.before(d4)){
                    availability = false;
                }else{
                    availability = true;
                }
                if(availability==true){
                    lista.add(numm);
                    Iterator<Integer> iterator2 = lista.iterator();
                    Iterator<Integer> iterator3 = busy.iterator();
                    while (iterator3.hasNext()) {
                        if (iterator3.next() == numm) {
                            while (iterator2.hasNext()) {
                                if (iterator2.next() == numm) {
                                    iterator2.remove();
                                }
                            }
                        }
                    }
                }if(availability==false){
                    busy.add(numm);
                    Iterator<Integer> iterator2 = lista.iterator();
                    while (iterator2.hasNext()) {
                        if (iterator2.next() == numm) {
                            iterator2.remove();
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{

            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(sql);

            while(resultSet2.next()) {
                Integer num = resultSet2.getInt("num");
                marka = resultSet2.getString("mark");
                String model = resultSet2.getString("model");
                String color = resultSet2.getString("color");
                Double price = resultSet2.getDouble("price");
                String rent = resultSet2.getString("rent");
                if(rent.equals("yes")) rent = "tak";
                if(rent.equals("no")) rent = "tak";

                Iterator<Integer> iterator2 = lista.iterator();
                while (iterator2.hasNext()) {
                    if (iterator2.next() == num) {
                        observableList.add(new ProductSearch(num, marka, model, color, price, rent));
                    }
                }
            }

            num.setCellValueFactory(new PropertyValueFactory<>("num"));
            mark.setCellValueFactory(new PropertyValueFactory<>("mark"));
            model.setCellValueFactory(new PropertyValueFactory<>("model"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            color.setCellValueFactory(new PropertyValueFactory<>("color"));
            rent.setCellValueFactory(new PropertyValueFactory<>("rent"));

            productTableView.setItems(observableList);

            FilteredList<ProductSearch> filteredList = new FilteredList<>(observableList, b -> true);
            search.textProperty().addListener((observable, aldValue, newValue) -> {
                filteredList.setPredicate(productSearch -> {
                    if(newValue.isEmpty() || newValue.isBlank() || newValue==null){
                        return true;
                    }
                    String searchK = newValue.toLowerCase();
                    if(productSearch.getMark().toLowerCase().indexOf(searchK) > -1){
                        return true;
                    }else if(productSearch.getModel().toLowerCase().indexOf(searchK) > -1) {
                        return true;
                    }else if(productSearch.getColor().toLowerCase().indexOf(searchK) > -1) {
                        return true;
                    }else if(productSearch.getRent().toLowerCase().indexOf(searchK) > -1) {
                        return true;
                    }else
                        return false;
                });
            });

            SortedList<ProductSearch> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(productTableView.comparatorProperty());
            productTableView.setItems(sortedList);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
