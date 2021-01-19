package main.models;

import jdk.jfr.Enabled;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "results")
public class Result {

    public Result ( User user , double score, String modules ) {
        this.user         = user;
        this.score        = score;
        this.modules = modules;
    }

    public Result(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne (cascade = CascadeType.ALL )
    @JoinColumn ( name = "user_id" )
    private User user;

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
    public List<Double> getModulesScores(){
        return null;
    }
}
