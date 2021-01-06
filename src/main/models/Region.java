package main.models;

import javax.persistence.*;

@Entity
@Table(name = "regions")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "capital", length = 100)
    private String capital;

    public Region(){

    }

    public Region(String name, String capital){
        this.name = name;
        this.capital = capital;
    }

    public Region ( int id , String name , String capital ) {
        this.id      = id;
        this.name    = name;
        this.capital = capital;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }


}
