package main.models;

public class Champ {
    public Integer number;
    public String year;
    public String field;
    public Integer countParticipant;

    public Champ() {}

    public Champ(Integer number, String year, String field, Integer countParticipant) {
        this.number = number;
        this.year = year;
        this.field = field;
        this.countParticipant = countParticipant;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setCountParticipant(Integer countParticipant) {
        this.countParticipant = countParticipant;
    }

    public Integer getCountParticipant() {
        return countParticipant;
    }
}
