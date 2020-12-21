package main.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "disciplines")
public class Discipline {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private int id;

    @Column ( name = "name" )
    private String name;

    @ManyToMany
    @JoinTable( name = "champ_disciplines",
            joinColumns = @JoinColumn(name = "discipline_id"),
            inverseJoinColumns = @JoinColumn(name = "championship_id"))
    private List<Championship> championships;
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Discipline(){

    }
    public Discipline ( String name ) {
        this.name = name;
    }
}
