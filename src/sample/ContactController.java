package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactController implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private Button closeB;

    public void close(MouseEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("main_page.fxml"));
            Stage mainStage = new Stage();
            mainStage.setTitle("AutoRent");
            mainStage.setScene(new Scene(root, 550, 400));
            mainStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
        Stage stage = (Stage) closeB.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File bfile = new File("image/car.png");
        Image image = new Image(bfile.toURI().toString());
        logo.setImage(image);
    }
}
