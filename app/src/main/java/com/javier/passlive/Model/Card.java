package com.javier.passlive.Model;

public class Card extends Record {

    String id, title, username, number, date, cvc, notes, image, record_time, update_time;

    public Card(String id, String title, String username, String number, String date, String cvc, String notes, String image, String record_time, String update_time) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.number = number;
        this.date = date;
        this.cvc = cvc;
        this.notes = notes;
        this.image = image;
        this.record_time = record_time;
        this.update_time = update_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRecord_time() {
        return record_time;
    }

    public void setRecord_time(String record_time) {
        this.record_time = record_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public int compareTo(Record other) {
        return this.title.compareTo(other.getTitle());
    }
}
