package gruppo13.desktop.Model;

import javafx.beans.property.SimpleStringProperty;

public class Segnalazione {
    private SimpleStringProperty Nickname;
    private SimpleStringProperty Struttura;
    private SimpleStringProperty Testo;

    public Segnalazione(String nickname, String struttura, String testo) {
        Nickname = new SimpleStringProperty(nickname);
        Struttura = new SimpleStringProperty(struttura);
        Testo = new SimpleStringProperty(testo);
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
}
