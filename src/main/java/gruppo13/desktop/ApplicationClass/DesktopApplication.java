package gruppo13.desktop.ApplicationClass;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import gruppo13.desktop.util.Resource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;

public class DesktopApplication extends Application {
    private static Stage GestioneVisitatori = null;
    private static Stage Login=null;
    private static GestioneVisitatori GestioneVisitatoriController;


    public static void btnpressed() {
        show_gestione();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FileInputStream serviceAccount = null;
        System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));

        try {
            serviceAccount = new FileInputStream("ServiceKey.json");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"File di configurazione non trovato");
        }


        FirebaseOptions options=null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("cv-19-31f8a.firebaseio.com")
                    .build();
        } catch (IOException ex) {

        }

        FirebaseApp.initializeApp(options);


        show_login();
    }


    public static void closeLogin() {
        Login .close();
        Login = null;
    }


    public static void show_login() throws IOException{




        try{
            FXMLLoader root = getFxml("FXMLDocument");
            Login=loadStage(root);
            Login.setTitle("Login");
            Login.show();

        }catch(IOException e){
            System.out.println("Errore login");
            e.printStackTrace();
        }
    }





    public static void main(String[] args) {
        launch(args);
    }

    private static FXMLLoader getFxml(String name) throws FileNotFoundException {
        return new FXMLLoader(new Resource(String.format("fxml/%s.fxml", name)).toURL());
    }

    private static Stage loadStage(FXMLLoader loader) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        return stage;
    }

    private static void show_gestione(){

        try {

            FXMLLoader loader = getFxml("FXMLGestioneVisitatori");
            GestioneVisitatori = loadStage(loader);

            //  GestioneVisitatoriController = loader.getController();
            closeLogin();
            GestioneVisitatori.setTitle("Gestione Visitatori");
            GestioneVisitatori.show();
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }
}
