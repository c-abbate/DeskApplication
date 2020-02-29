package gruppo13.desktop.Model;

import javafx.beans.property.SimpleStringProperty;

public class Segnalazioni {

    private SimpleStringProperty Struttura;
    private SimpleStringProperty Testo;
    private SimpleStringProperty Nickname;

    public Segnalazioni(String nickname, String struttura, String testo) {
        Testo = new SimpleStringProperty(testo);
        Struttura = new SimpleStringProperty(struttura);
        Nickname = new SimpleStringProperty(nickname);
    }

    public String getStruttura() {
        return Struttura.get();
    }

    public SimpleStringProperty strutturaProperty() {
        return Struttura;
    }

    public void setStruttura(String struttura) {
        this.Struttura.set(struttura);
    }

    public String getTesto() {
        return Testo.get();
    }

    public SimpleStringProperty testoProperty() {
        return Testo;
    }

    public void setTesto(String testo) {
        this.Testo.set(testo);
    }

    public String getNickname() {
        return Nickname.get();
    }

    public SimpleStringProperty nicknameProperty() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        this.Nickname.set(nickname);
    }
}
