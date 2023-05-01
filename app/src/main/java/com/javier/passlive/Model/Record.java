package com.javier.passlive.Model;


public class Record implements Comparable<Record> {
    private String title;
    private String Record_time;

    public Record(String title, String Record_time) {
        this.title = title;
        this.Record_time = Record_time;
    }

    public Record() {

    }

    public String getTitle() {
        return title;
    }

    public String getRecord_time() {
        return Record_time;
    }

    // Sobrescribir compareTo() para ordenar por título
    @Override
    public int compareTo(Record other) {
        int titleCompare = this.title.compareTo(other.title);
        if (titleCompare == 0) {
            return this.Record_time.compareTo(other.Record_time);
        } else {
            return titleCompare;
        }
    }

}