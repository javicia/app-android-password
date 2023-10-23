package com.javier.passlive.Model;

public class Bank extends Record{

    String id, title, bank, title_account_bank, account_bank, number, websites, notes, image, record_time, update_time;


    public Bank(String id, String title, String bank, String title_account_bank, String account_bank, String number, String websites, String notes, String image, String record_time, String update_time) {
        this.id = id;
        this.title = title;
        this.bank = bank;
        this.title_account_bank = title_account_bank;
        this.account_bank = account_bank;
        this.number = number;
        this.websites = websites;
        this.notes = notes;
        this.image = image;
        this.record_time = record_time;
        this.update_time = update_time;
    }
public Bank(){
        super();
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

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getTitle_account_bank() {
        return title_account_bank;
    }

    public void setTitle_account_bank(String title_account_bank) {
        this.title_account_bank = title_account_bank;
    }

    public String getAccount_bank() {
        return account_bank;
    }

    public void setAccount_bank(String account_bank) {
        this.account_bank = account_bank;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWebsites() {
        return websites;
    }

    public void setWebsites(String websites) {
        this.websites = websites;
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
        int titleCompare = this.title.compareTo(other.getTitle());
        if (titleCompare == 0) {
            return this.getRecord_time().compareTo(other.getRecord_time());
        } else {
            return titleCompare;
        }
    }
}