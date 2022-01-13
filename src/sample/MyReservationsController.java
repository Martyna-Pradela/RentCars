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

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyReservationsController implements Initializable {
    @FXML
    private TableView<MyProduct> productTableView;
    @FXML
    private TableColumn<MyProduct, String> mark;
    @FXML
    private TableColumn<MyProduct, String> model;
    @FXML
    private TableColumn<MyProduct, String> color;
    @FXML
    private TableColumn<MyProduct, String> datew;
    @FXML
    private TableColumn<MyProduct, String> datez;
    @FXML
    private TableColumn<MyProduct, Double> price;
    @FXML
    private Button close;
    @FXML
    private TextField search;

    Set<String> lista = new TreeSet<>();

    static String email;
    String date1, date2, modelW, markW;
    int numer;

//    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    ObservableList<MyProduct> observableList = FXCollections.observableArrayList();

    public static void getData(Person p){
        email = p.getEmail();
    }

    public void closeCars(){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();

        String sql = "select mark, model, color, price, date1, date2, num, email from rented_cars where email ='" + email + "'";
//
        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String mark = resultSet.getString("mark");
                String model = resultSet.getString("model");
                String color = resultSet.getString("color");
                double price = resultSet.getDouble("price");
                String date1 = resultSet.getString("date1");
                String date2 = resultSet.getString("date2");
//                String d1 = df.format(date1);
//                String d2 = df.format(date2);
                Integer num = resultSet.getInt("num");

                observableList.add(new MyProduct(mark, model, color, date1, date2, price, num));
            }
//
            mark.setCellValueFactory(new PropertyValueFactory<>("mark"));
            model.setCellValueFactory(new PropertyValueFactory<>("model"));
            color.setCellValueFactory(new PropertyValueFactory<>("color"));
            datew.setCellValueFactory(new PropertyValueFactory<>("datew"));
            datez.setCellValueFactory(new PropertyValueFactory<>("datez"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));

            productTableView.setItems(observableList);

            FilteredList<MyProduct> filteredList = new FilteredList<>(observableList, b -> true);
            search.textProperty().addListener((observable, aldValue, newValue) -> {
                filteredList.setPredicate(productSearch -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchK = newValue.toLowerCase();
                    if (productSearch.getMark().toLowerCase().indexOf(searchK) > -1) {
                        return true;
                    } else if (productSearch.getModel().toLowerCase().indexOf(searchK) > -1) {
                        return true;
                    } else if (productSearch.getColor().toLowerCase().indexOf(searchK) > -1) {
                        return true;
                    } else
                        return false;
                });
            });

            SortedList<MyProduct> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(productTableView.comparatorProperty());
            productTableView.setItems(sortedList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void filteredCars(MouseEvent event){
        for(MyProduct myProduct: productTableView.getItems()){
            String da = myProduct.getDatew();
            da = da.replaceAll("-", "");
            lista.add(da);
        }
        observableList.clear();
        for(String d : lista) {
            DataBaseConnection connectNow = new DataBaseConnection();
            Connection connection = connectNow.getConnection();
            String dataa = d.substring(0,4) + "-" + d.substring(4,6)+"-"+d.substring(6,8);

            String sql = "select mark, model, color, price, date1, date2, num, email from rented_cars where email ='" + email + "' and date1='" + dataa +"'";

            try {

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    String mark = resultSet.getString("mark");
                    String model = resultSet.getString("model");
                    String color = resultSet.getString("color");
                    double price = resultSet.getDouble("price");
                    String date1 = resultSet.getString("date1");
                    String date2 = resultSet.getString("date2");
                    Integer num = resultSet.getInt("num");

                    observableList.add(new MyProduct(mark, model, color, date1, date2, price, num));
                }

                mark.setCellValueFactory(new PropertyValueFactory<>("mark"));
                model.setCellValueFactory(new PropertyValueFactory<>("model"));
                color.setCellValueFactory(new PropertyValueFactory<>("color"));
                datew.setCellValueFactory(new PropertyValueFactory<>("datew"));
                datez.setCellValueFactory(new PropertyValueFactory<>("datez"));
                price.setCellValueFactory(new PropertyValueFactory<>("price"));

                productTableView.setItems(observableList);

                FilteredList<MyProduct> filteredList = new FilteredList<>(observableList, b -> true);
                search.textProperty().addListener((observable, aldValue, newValue) -> {
                    filteredList.setPredicate(productSearch -> {
                        if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                            return true;
                        }
                        String searchK = newValue.toLowerCase();
                        if (productSearch.getMark().toLowerCase().indexOf(searchK) > -1) {
                            return true;
                        } else if (productSearch.getModel().toLowerCase().indexOf(searchK) > -1) {
                            return true;
                        } else if (productSearch.getColor().toLowerCase().indexOf(searchK) > -1) {
                            return true;
                        } else
                            return false;
                    });
                });

                SortedList<MyProduct> sortedList = new SortedList<>(filteredList);
                sortedList.comparatorProperty().bind(productTableView.comparatorProperty());
                productTableView.setItems(sortedList);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void cancelReservation(MouseEvent event){
        MyProduct myProduct = productTableView.getSelectionModel().getSelectedItem();
        date1 = myProduct.getDatew();
        date2 = myProduct.getDatez();
        markW = myProduct.getMark();
        modelW = myProduct.getModel();
        CancelController.dataToCancel(date1, date2, email, markW, modelW);
        try{
            Parent root = FXMLLoader.load(getClass().getResource("cancel.fxml"));
            Stage mainStage = new Stage();
            mainStage.setTitle("Anulowanie rezerwacji");
            mainStage.setScene(new Scene(root, 300, 200));
            mainStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
