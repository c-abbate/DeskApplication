package gruppo13.desktop.Model;

import javafx.beans.property.SimpleStringProperty;

public class Utenti {
    private SimpleStringProperty Nome;
    private SimpleStringProperty Cognome;
    private SimpleStringProperty Nickname;
    private SimpleStringProperty Status;

    public Utenti(String nome,String cognome,String nickname,String status){
        Nome = new SimpleStringProperty(nome);
        Cognome = new SimpleStringProperty(cognome);
        Nickname = new SimpleStringProperty(nickname);
        Status = new SimpleStringProperty(status);
    }

    public String getStatus() {
        return Status.get();
    }

    public SimpleStringProperty statusProperty() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status.set(status);
    }

    public String getNome() {
        return Nome.get();
    }

    public SimpleStringProperty nomeProperty() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome.set(nome);
    }

    public String getCognome() {
        return Cognome.get();
    }

    public SimpleStringProperty cognomeProperty() {
        return Cognome;
    }

    public void setCognome(String cognome) {
        this.Cognome.set(cognome);
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
