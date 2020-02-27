package gruppo13.desktop.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class FXMLSegnalazioniController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnelimina;

    @FXML
    private Button btnannulla;

    @FXML
    private ListView<?> listsegnalazioni;

    @FXML
    public void annullapressed(ActionEvent event) {

    }

    @FXML
    public void eliminapressed(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnelimina != null : "fx:id=\"btnelimina\" was not injected: check your FXML file 'FXMLSegnalazioni.fxml'.";
        assert btnannulla != null : "fx:id=\"btnannulla\" was not injected: check your FXML file 'FXMLSegnalazioni.fxml'.";
        assert listsegnalazioni != null : "fx:id=\"listsegnalazioni\" was not injected: check your FXML file 'FXMLSegnalazioni.fxml'.";

    }
}