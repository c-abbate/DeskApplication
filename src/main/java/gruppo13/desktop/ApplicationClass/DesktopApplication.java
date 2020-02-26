package gruppo13.desktop.ApplicationClass;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import gruppo13.desktop.util.Resource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DesktopApplication extends Application {
    private static Stage GestioneVisitatori = null;
    private static Stage Login=null;
    private static GestioneVisitatori GestioneVisitatoriController;


    public static void btnpressed() {
        show_gestione();
    }

    @Override
    public void start(Stage stage) throws Exception {
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
            GestioneVisitatori.show();
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }
}
