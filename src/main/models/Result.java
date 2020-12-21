package main.models;

import jdk.jfr.Enabled;

import javax.persistence.*;

@Entity
@Table(name = "results")
public class Result {

    public Result ( User user , Championship championship , Discipline discipline , int place , int score ) {
        this.user         = user;
        this.championship = championship;
        this.discipline   = discipline;
        this.place        = place;
        this.score        = score;
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

    @Column(name = "place")
    private int place;

    @Column(name = "score")
    private int score;

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

    public int getPlace ( ) {
        return place;
    }

    public void setPlace ( int place ) {
        this.place = place;
    }

    public int getScore ( ) {
        return score;
    }

    public void setScore ( int score ) {
        this.score = score;
    }
}
