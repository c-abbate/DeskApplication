package gruppo13.desktop.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import gruppo13.desktop.Model.Segnalazioni;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLSegnalazioniController implements Initializable {
    Firestore database = FirestoreClient.getFirestore();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnelimina;

    @FXML
    private Button btnannulla;

    @FXML
    private TableView<Segnalazioni> tablesegnalazioni;

    @FXML
    private TableColumn<?, ?> nickname;

    @FXML
    private TableColumn<?, ?> struttura;

    @FXML
    private TableColumn<?, ?> testo;

    @FXML
    void annullapressed(ActionEvent event) {

    }

    @FXML
    void eliminapressed(ActionEvent event) {

    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> query = db.collection("Segnalazioni").get();

        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }

        nickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        struttura.setCellValueFactory(new PropertyValueFactory<>("struttura"));
        testo.setCellValueFactory(new PropertyValueFactory<>("testo"));

        ObservableList<Segnalazioni> observableList = FXCollections.observableArrayList();

        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
           
            observableList.add(new Segnalazioni(document.getString("nickname"),document.getString("struttura"),document.getString("testo")));
        }

        tablesegnalazioni.setItems(observableList);
    }


    private String structure (String struttura){
        DocumentReference doc_struttura=database.collection("Strutture").document(struttura);
        ApiFuture<DocumentSnapshot>future=doc_struttura.get();
        DocumentSnapshot document= null;
        try{
            document=future.get();

    }catch(InterruptedException e){
        e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return  document.getString("nome");
    }


}
