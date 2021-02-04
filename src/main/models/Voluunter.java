package main.models;

public class Voluunter {
    private Integer id;
    private String name;
    private String sex;
    private String region;
    private String competence;

    public String getRegion ( ) {
        return region;
    }

    public Voluunter ( String name , String sex , String region , String competence ) {
        this.name       = name;
        this.sex        = sex;
        this.region     = region;
        this.competence = competence;
    }

    public Voluunter ( Integer id , String name , String sex , String region , String competence ) {
        this.id         = id;
        this.name       = name;
        this.sex        = sex;
        this.region     = region;
        this.competence = competence;
    }

    public void setId( Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }
}