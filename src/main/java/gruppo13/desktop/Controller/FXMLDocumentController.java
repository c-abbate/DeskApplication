package gruppo13.desktop.Controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import gruppo13.desktop.ApplicationClass.DesktopApplication;
import gruppo13.desktop.ApplicationClass.GestioneVisitatori;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;


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
        if(password.length()==28){
            Firestore db = FirestoreClient.getFirestore();

            ApiFuture<QuerySnapshot> query = db.collection("Utenti")
                    .whereEqualTo("idUtente",password)
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
                if(document.getString("idUtente").equals(password)){
                    if(document.getBoolean("admin")){
                        JOptionPane.showMessageDialog(null,"Accesso riuscito");
                        flag=true;
                        DesktopApplication.btnpressed( );
                    }else
                        JOptionPane.showMessageDialog(null,"L'account non possiede i permessi");
                    flag=true;
                }
            }
            if(flag==false)
                JOptionPane.showMessageDialog(null,"Accesso fallito");
        }else
            JOptionPane.showMessageDialog(null,"Accesso fallito");






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
