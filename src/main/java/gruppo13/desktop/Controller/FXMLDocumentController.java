package gruppo13.desktop.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import gruppo13.desktop.ApplicationClass.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;


public class FXMLDocumentController {
    private static Stage GestioneVisitatori = null;

    private static GestioneVisitatori GestioneVisitatoriController;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private PasswordField passwd;
    @FXML
    public void btnpressed(ActionEvent event) {

        String password= passwd.getText();
        boolean flag=false;



        DesktopApplication.btnpressed( );


    }

    @FXML
    void initialize() {

    }




    private static FXMLLoader getFxml(String name) {
        return new FXMLLoader(gruppo13.desktop.ApplicationClass.GestioneVisitatori.class.getResource(
                String.format("%s.fxml", name)));
    }

    private static Stage loadStage(FXMLLoader loader) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        return stage;
    }
}
