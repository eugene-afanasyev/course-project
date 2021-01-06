package main.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "disсiplines")
public class Discipline {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private int id;

    @Column ( name = "name" )
    private String name;

    @Column (name = "description")
    private String description;

    @Column (name = "discipline_code")
    private String disciplineCode;
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

    public List<Championship> getChampionships ( ) {
        return championships;
    }

    public void setChampionships ( List<Championship> championships ) {
        this.championships = championships;
    }

    public String getDescription ( ) {
        return description;
    }

    public void setDescription ( String description ) {
        this.description = description;
    }

    public Discipline(){

    }
    public Discipline ( String name ) {
        this.name = name;
        this.championships = new ArrayList<>();
    }

    public String getDisciplineCode ( ) {
        return disciplineCode;
    }

    public void setDisciplineCode ( String disciplineCode ) {
        this.disciplineCode = disciplineCode;
    }

}
