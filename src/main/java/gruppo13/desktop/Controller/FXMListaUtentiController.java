package gruppo13.desktop.Controller;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import gruppo13.desktop.Model.Utenti;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMListaUtentiController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Utenti> tabella;

    @FXML
    private TableColumn<?, ?> nickname;

    @FXML
    private TableColumn<?, ?> cognome;

    @FXML
    private TableColumn<?, ?> nome;


    public void clicksospendi(javafx.event.ActionEvent actionEvent) {


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> query = db.collection("Utenti").get();

        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }

        nickname.setCellValueFactory(new PropertyValueFactory<>("Nickname"));
        cognome.setCellValueFactory(new PropertyValueFactory<>("Cognome"));
        nome.setCellValueFactory(new PropertyValueFactory<>("Nome"));

        ObservableList<Utenti> observableList = FXCollections.observableArrayList();

        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            observableList.add(new Utenti(document.getString("nome"),document.getString("cognome"),document.getString("nickname")));
        }
        tabella.setItems(observableList);
    }


}
