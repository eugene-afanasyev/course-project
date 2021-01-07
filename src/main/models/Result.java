package main.models;

import jdk.jfr.Enabled;

import javax.persistence.*;

@Entity
@Table(name = "results")
public class Result {

    public Result ( User user , Championship championship , Discipline discipline, double score, String modules ) {
        this.user         = user;
        this.championship = championship;
        this.discipline   = discipline;
        this.score        = score;
        this.modules = modules;
    }

    public Result(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne ( cascade = CascadeType.ALL )
    @JoinColumn ( name = "user_id" )
    private User user;

    @OneToOne ( cascade = CascadeType.ALL )
    @JoinColumn ( name = "championship_id" )
    private Championship championship;

    @OneToOne ( cascade = CascadeType.ALL )
    @JoinColumn ( name = "discipline_id" )
    private Discipline discipline;

    @Column(name = "score")
    private double score;

    @Column(name = "modules")
    private String modules;

    public int getId ( ) {
        return id;
    }

    public void setId ( int id ) {
        this.id = id;
    }

    public User getUser ( ) {
        return user;
    }

    public void setUser ( User user ) {
        this.user = user;
    }

    public Championship getChampionship ( ) {
        return championship;
    }

    public void setChampionship ( Championship championship ) {
        this.championship = championship;
    }

    public Discipline getDiscipline ( ) {
        return discipline;
    }

    public void setDiscipline ( Discipline discipline ) {
        this.discipline = discipline;
    }

    public double getScore ( ) {
        return score;
    }

    public void setScore ( double score ) {
        this.score = score;
    }

    public String getModules ( ) {
        return modules;
    }

    public void setModules ( String modules ) {
        this.modules = modules;
    }
}
