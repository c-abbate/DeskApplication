package gruppo13.desktop.ApplicationClass;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Cancellazioni {
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../../../resources/fxml/FXMLCancellazioni.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Cancellazioni");
        stage.show();
    }
}
