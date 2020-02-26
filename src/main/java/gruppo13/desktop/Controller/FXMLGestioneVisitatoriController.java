package gruppo13.desktop.Controller;

import gruppo13.desktop.ApplicationClass.GestioneVisitatori;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FXMLGestioneVisitatoriController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btniscritti;

    @FXML
    private Button btnsegnalazioni;

    @FXML
    private Button btncancellazioni;

    @FXML
    void clickcancellazioni(ActionEvent event) {
        GestioneVisitatori.clickcancellazioni();
    }

    @FXML
    void clickiscritti(ActionEvent event) {
        GestioneVisitatori.clickiscritti();
    }

    @FXML
    void clicksegnalazioni(ActionEvent event) {
        GestioneVisitatori.clicksegnalazioni();

    }


}
