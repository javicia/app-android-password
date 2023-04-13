package com.javier.passlive.Model;
//Establecemos atributos para realizar la obtención de datos en la lista

public class Web {

    String id, tittle, account, username, password, websites, note, image, t_record, t_update;

    //Constructor

    public Web(String id, String tittle, String account, String username,
               String password, String websites, String note,
               String image, String t_record, String t_update) {
        this.id = id;
        this.tittle = tittle;
        this.account = account;
        this.username = username;
        this.password = password;
        this.websites = websites;
        this.note = note;
        this.image = image;
        this.t_record = t_record;
        this.t_update = t_update;
    }
    //Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWebsites() {
        return websites;
    }

    public void setWebsites(String websites) {
        this.websites = websites;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getT_record() {
        return t_record;
    }

    public void setT_record(String t_record) {
        this.t_record = t_record;
    }

    public String getT_update() {
        return t_update;
    }

    public void setT_update(String t_update) {
        this.t_update = t_update;
    }
}
