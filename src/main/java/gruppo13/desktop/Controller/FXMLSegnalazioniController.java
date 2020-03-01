package gruppo13.desktop.Controller;

import java.net.URL;
import java.util.LinkedList;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;

public class FXMLSegnalazioniController implements Initializable {

    private Firestore database = FirestoreClient.getFirestore();
    private int riga_selezionata=-1;
    List<String>Id_Sengnalazioni;
    List<String>Id_Recensioni;
    private Segnalazioni segnalazione_selezionata;

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
        if(riga_selezionata == -1){
            JOptionPane.showMessageDialog(null,"nessuna riga selezionata");
            return;
        }
        String id_segnalazione_selezionata = Id_Sengnalazioni.get(riga_selezionata);
        CollectionReference segnalazioni = database.collection("Segnalazioni");
        segnalazioni.document(id_segnalazione_selezionata).delete();



        tablesegnalazioni.getItems().remove(segnalazione_selezionata);
        Id_Sengnalazioni.remove(id_segnalazione_selezionata);
        riga_selezionata = -1;
        segnalazione_selezionata = null;
        JOptionPane.showMessageDialog(null,"Segnalazione eliminata");
    }

    @FXML
    void eliminapressed(ActionEvent event) {
        if(riga_selezionata == -1){
            JOptionPane.showMessageDialog(null,"nessuna riga selezionata");
            return;
        }
        String id_segnalazione_selezionata = Id_Sengnalazioni.get(riga_selezionata);
        String recensione_da_eliminare = Id_Recensioni.get(riga_selezionata);
        String struttura_da_aggiornare = null;

        CollectionReference recensioni = database.collection("Recensione");
        CollectionReference segnalazioni = database.collection("Segnalazioni");
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
            if(document.getId().equals(recensione_da_eliminare)){
                struttura_da_aggiornare = document.getString("struttura");
                recensioni.document(document.getId()).delete();
                break;
            }
        }

        
        if(struttura_da_aggiornare != null){
            aggiorna_valutazione_struttura(struttura_da_aggiornare);
        }
        delete_all_reporting(recensione_da_eliminare);
        tablesegnalazioni.getItems().remove(segnalazione_selezionata);
        Id_Recensioni.remove(recensione_da_eliminare);
        Id_Sengnalazioni.remove(id_segnalazione_selezionata);
        segnalazione_selezionata = null;
        riga_selezionata = -1;
        JOptionPane.showMessageDialog(null,"Recensione eliminata");






    }

    private void aggiorna_valutazione_struttura(String struttura_da_aggiornare) {
        int voto = 0,count = 0,totale = 0;
        double media;
        String valutazione;
        ApiFuture<QuerySnapshot> query = database.collection("Recensione")
                .whereEqualTo("struttura",struttura_da_aggiornare)
                .get();

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
            valutazione = String.valueOf(document.get("voto"));
            voto = Integer.parseInt(valutazione);
            totale = totale + voto;
            count++;
        }
        if(count > 0){
            media = totale/count;
        }else{
            media = 0.0;
        }

        database.collection("Strutture").document(struttura_da_aggiornare).update("valutazione",media);
    }

    private void delete_all_reporting(String recensione_da_eliminare) {
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


        for (QueryDocumentSnapshot document: documents) {
            if(document.getString("recensione").equals(recensione_da_eliminare)){
                segnalazioni.document(document.getId()).delete();
            }
        }
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

        ObservableList<Segnalazioni> observableList = FXCollections.observableArrayList();

        List<QueryDocumentSnapshot> documents_segnalazioni = querySnapshot_segnalazioni.getDocuments();
        List<QueryDocumentSnapshot> documents_strutture;
        Id_Sengnalazioni = new LinkedList<>();
        Id_Recensioni = new LinkedList<>();
        Segnalazioni da_inserire;

        for (QueryDocumentSnapshot document : documents_segnalazioni) {
            documents_strutture = querySnapshot_strutture.getDocuments();
            for (QueryDocumentSnapshot document_strutture: documents_strutture) {
                if(document_strutture.getId().equals(document.getString("struttura"))){
                    da_inserire = new Segnalazioni(document.getString("nickname"),document_strutture.getString("nome"),document.getString("testo"));
                    if(!contains(observableList,da_inserire)){
                        Id_Sengnalazioni.add(document.getId());
                        Id_Recensioni.add(document.getString("recensione"));
                        observableList.add(da_inserire);
                    }


                    break;
                }

            }

        }

        tablesegnalazioni.setItems(observableList);
        tablesegnalazioni.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                riga_selezionata = tablesegnalazioni.getSelectionModel().getSelectedIndex();
                segnalazione_selezionata = tablesegnalazioni.getSelectionModel().getSelectedItem();

            }
        });
    }

    private boolean contains(ObservableList<Segnalazioni> observableList, Segnalazioni da_inserire) {

        for (Segnalazioni inserite: observableList) {
            if(inserite.equals(da_inserire)){
                return true;

            }
        }

        return false;
    }
}



