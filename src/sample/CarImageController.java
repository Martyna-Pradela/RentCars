package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CarImageController implements Initializable {

    @FXML
    private ImageView zdjecie;
    static String numer;
    @FXML
    private Button closeB;

    public static void numerOK(String num){
        numer = num;
    }

    public void close(MouseEvent event){
        Stage stage = (Stage) closeB.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File bfile4 = new File("image/"+numer+".jpg");
        Image image4 = new Image(bfile4.toURI().toString());
        zdjecie.setImage(image4);
    }
}
