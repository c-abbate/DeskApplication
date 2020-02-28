package gruppo13.desktop.Controller;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import gruppo13.desktop.Model.Cancellazioni;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLCancellazioniController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Cancellazioni> tablecancellazioni;

    @FXML
    private TableColumn<?, ?> email;

    @FXML
    private TableColumn<?, ?> nickname;

    @FXML
    private TableColumn<?, ?> motivazione;

    @FXML
    void confermaclicked(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> query = db.collection("Cancellazioni").get();

        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }

        nickname.setCellValueFactory(new PropertyValueFactory<>("Nickname"));
        motivazione.setCellValueFactory(new PropertyValueFactory<>("Motivazione"));
        email.setCellValueFactory(new PropertyValueFactory<>("Email"));

        ObservableList<Cancellazioni> observableList = FXCollections.observableArrayList();

        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            observableList.add(new Cancellazioni(document.getString("email"), document.getString("motivazione"), document.getString("nickname")));
        }
        tablecancellazioni.setItems(observableList);
    }

    }

