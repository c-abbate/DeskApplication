package gruppo13.desktop.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import gruppo13.desktop.Model.Segnalazione;
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

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnelimina;

    @FXML
    private Button btnannulla;

    @FXML
    private TableView<Segnalazione> tablesegnalazioni;

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

        Firestore database = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> query_segnalazioni = database.collection("Segnalazioni").get();
        ApiFuture<QuerySnapshot> query_strutture = database.collection("Strutture").get();

        QuerySnapshot querySnapshot_segnalazioni = null;
        QuerySnapshot querySnapshot_strutture = null;
        try {
            querySnapshot_segnalazioni = query_segnalazioni.get();
            querySnapshot_strutture = query_strutture.get();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }

        nickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        struttura.setCellValueFactory(new PropertyValueFactory<>("struttura"));
        testo.setCellValueFactory(new PropertyValueFactory<>("testo"));

        ObservableList<Segnalazione> observableList = FXCollections.observableArrayList();

        List<QueryDocumentSnapshot> documents_segnalazioni = querySnapshot_segnalazioni.getDocuments();
        List<QueryDocumentSnapshot> documents_strutture;
        for (QueryDocumentSnapshot document : documents_segnalazioni) {
            documents_strutture = querySnapshot_strutture.getDocuments();
            for (QueryDocumentSnapshot document_strutture: documents_strutture) {
                if(document_strutture.getId().equals(document.getString("struttura"))){
                    observableList.add(new Segnalazione(document.getString("nickname"),document_strutture.getString("nome"),document.getString("testo")));
                    break;
                }

            }

        }

        tablesegnalazioni.setItems(observableList);
    }

}