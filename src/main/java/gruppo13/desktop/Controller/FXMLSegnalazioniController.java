package gruppo13.desktop.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import javafx.fxml.Initializable;
import gruppo13.desktop.ApplicationClass.Segnalazioni;
import javafx.scene.control.TextArea;


public class FXMLSegnalazioniController implements Initializable{

    public TextArea segnalazioni;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
