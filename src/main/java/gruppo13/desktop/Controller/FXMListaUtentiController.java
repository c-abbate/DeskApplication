package gruppo13.desktop.Controller;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import gruppo13.desktop.ApplicationClass.ListaUtenti;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import gruppo13.desktop.ApplicationClass.ListaUtenti;
import gruppo13.desktop.Model.Utenti;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;

public class FXMListaUtentiController implements Initializable {

    private Firestore database = FirestoreClient.getFirestore();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private int riga_selezionata = -1;
    private List<String> id_utenti;

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

        if(riga_selezionata == -1){
            JOptionPane.showMessageDialog(null,"nessun utente selezionato");
            return;
        }

        String id_utente = id_utenti.get(riga_selezionata);


    //Metodo firebase richiesta sospensione account
        UserRecord.UpdateRequest request= new UserRecord.UpdateRequest(id_utente).setDisabled(true);
        try {
            if(mAuth.getUser(id_utente).isDisabled()){
                JOptionPane.showMessageDialog(null,"L'utente è già sospeso");
            }else{
                mAuth.updateUser(request);
                JOptionPane.showMessageDialog(null,"L'utente è stato sospeso");
            }
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
        riga_selezionata = -1;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ApiFuture<QuerySnapshot> query = database.collection("Utenti").get();

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
        id_utenti = new LinkedList<String>();

        for (QueryDocumentSnapshot document : documents) {
            id_utenti.add(document.getString("idUtente"));
            observableList.add(new Utenti(document.getString("nome"),document.getString("cognome"),document.getString("nickname")));
        }
        tabella.setItems(observableList);
        tabella.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                riga_selezionata = tabella.getSelectionModel().getSelectedIndex();
            }
        });
    }


}
