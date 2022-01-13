package sample;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ShowCarsController implements Initializable {

    @FXML
    private ImageView logo;
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

    String marka, name, modell;
    int numer;

    ObservableList<ProductSearch> observableList = FXCollections.observableArrayList();

    public void closeCars(){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
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

    public void showImage(MouseEvent event){
        ProductSearch product = productTableView.getSelectionModel().getSelectedItem();
        numer = product.getNum();
        marka = product.getMark();
        modell = product.getModel();
        name = String.valueOf(numer);
        CarImageController.numerOK(name);
        try{
            Parent root = FXMLLoader.load(getClass().getResource("car_image.fxml"));
            Stage mainStage = new Stage();
            mainStage.setTitle(marka + " " + modell);
            mainStage.setScene(new Scene(root, 450, 300));
            mainStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File bfile = new File("image/car.png");
        Image image = new Image(bfile.toURI().toString());
        logo.setImage(image);
        logo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                TranslateTransition transition = new TranslateTransition();
                transition.setDuration(Duration.seconds(4));
                transition.setToX(91);
                transition.setNode(logo);
                transition.play();

                ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(4), logo);
                scaleTransition.setToX(2);
                scaleTransition.setToY(2);
                scaleTransition.play();
            }
        });

        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();

        String sql = "select num, mark, model, color, price, rent from allcars";

        try{

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                Integer num = resultSet.getInt("num");
                marka = resultSet.getString("mark");
                String model = resultSet.getString("model");
                String color = resultSet.getString("color");
                Double price = resultSet.getDouble("price");
                String rent = resultSet.getString("rent");
                if(rent.equals("yes")) rent = "tak";
                if(rent.equals("no")) rent = "tak";

                observableList.add(new ProductSearch(num, marka, model, color, price, rent));
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
