package gruppo13.desktop.Controller;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import gruppo13.desktop.Model.Cancellazioni;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;

public class FXMLCancellazioniController implements Initializable {

    private int riga_selezionata = -1;
    private List<String> id_utenti;
    private List<String> Documents_richieste;
    private Firestore database = FirestoreClient.getFirestore();
    private Cancellazioni richiesta_selezionata;

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
        if(riga_selezionata == -1){
            JOptionPane.showMessageDialog(null,"nessuna riga selezionata");
            return;
        }

        String id_utente = id_utenti.get(riga_selezionata);
        String id_richiesta = Documents_richieste.get(riga_selezionata);

        CollectionReference recensioni = database.collection("Recensione");
        CollectionReference richieste = database.collection("Cancellazioni");
        ApiFuture<QuerySnapshot> query = recensioni.get();
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }


        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        for (QueryDocumentSnapshot document: documents) {
            if(document.getString("idAutore").equals(id_utente)){
                recensioni.document(document.getId()).delete();
            }
        }

        try {
            FirebaseAuth.getInstance().deleteUser(id_utente);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }

        delete_user_reporting(id_utente);
        delete_user_document(id_utente);
        richieste.document(id_richiesta).delete();
        Documents_richieste.remove(id_richiesta);
        tablecancellazioni.getItems().remove(richiesta_selezionata);
        richiesta_selezionata = null;
        riga_selezionata = -1;
        JOptionPane.showMessageDialog(null,"Utente eliminato");

    }

    private void delete_user_reporting(String id_utente) {
        CollectionReference segnalazioni = database.collection("Segnalazioni");
        ApiFuture<QuerySnapshot> query = segnalazioni.get();

        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }

        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            if(document.getString("idAutore").equals(id_utente)){
                segnalazioni.document(document.getId()).delete();
            }
        }
    }


    private void delete_user_document(String id_utente) {
        CollectionReference utenti = database.collection("Utenti");
        ApiFuture<QuerySnapshot> query = utenti.get();

        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }

        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            if(document.getString("idUtente").equals(id_utente)){
                utenti.document(document.getId()).delete();
                break;
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {



        ApiFuture<QuerySnapshot> query = database.collection("Cancellazioni").get();

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
        id_utenti = new LinkedList<>();
        Documents_richieste = new LinkedList<>();
        for (QueryDocumentSnapshot document : documents) {
            id_utenti.add(document.getString("idUtente"));
            Documents_richieste.add(document.getId());
            observableList.add(new Cancellazioni(document.getString("email"), document.getString("motivazione"), document.getString("nickname")));
        }
        tablecancellazioni.setItems(observableList);
        tablecancellazioni.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                riga_selezionata = tablecancellazioni.getSelectionModel().getSelectedIndex();
                richiesta_selezionata = tablecancellazioni.getSelectionModel().getSelectedItem();

            }
        });
    }

}

