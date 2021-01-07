package main.models;

public class Champ {
    public Integer number;
    public String name;
    public String year;
    public String country;
    public String city;
    public String field;
    public Integer countParticipant;
    public String description;

    public Champ() {}

    public Champ(Integer number, String name, String description, String year,
                 String country, String city, Integer countParticipant) {
        this.number = number;
        this.name = name;
        this.country = country;
        this.city = city;
        this.description = description;
        this.year = year;
        this.field = country + ", " + city;
        this.countParticipant = countParticipant;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
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
