package gruppo13.desktop.ApplicationClass;

import java.io.FileNotFoundException;
import java.io.IOException;

import gruppo13.desktop.util.Resource;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GestioneVisitatori {
    private static Stage ListaUtenti;
    private static Stage  Segnalazioni;
    private static Stage Cancellazioni;


    public static void clicksegnalazioni() {
        show_segnalazioni();
    }


    public static void clickiscritti() {
        show_iscritti();

    }

    public static void clickcancellazioni() {
        show_cancellazioni();
    }



    private static void show_iscritti(){
        try {

            FXMLLoader loader = getFxml("FXMListaUtenti");
            ListaUtenti = loadStage(loader);

            //  GestioneVisitatoriController = loader.getController();
            ListaUtenti.setTitle("Utenti iscritti");
            ListaUtenti.show();
        }
        catch (Exception e) {
            System.out.println(e);
        }


    }

    private static void show_cancellazioni(){
        try {

            FXMLLoader loader = getFxml("FXMLCancellazioni");
            Cancellazioni = loadStage(loader);

            //  GestioneVisitatoriController = loader.getController();
            Cancellazioni.setTitle("Richieste canzallazione utente");
            Cancellazioni.show();
        }
        catch (Exception e) {
            System.out.println(e);
        }


    }

    private static void show_segnalazioni(){
        try {

            FXMLLoader loader = getFxml("FXMLSegnalazioni");
            Segnalazioni = loadStage(loader);

            //  GestioneVisitatoriController = loader.getController();
            Segnalazioni.setTitle("Recensioni segnalate");
            Segnalazioni.show();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private static FXMLLoader getFxml(String name) throws FileNotFoundException {
        return new FXMLLoader(new Resource(String.format("fxml/%s.fxml", name)).toURL());
    }

    private static Stage loadStage(FXMLLoader loader) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        return stage;
    }


}
