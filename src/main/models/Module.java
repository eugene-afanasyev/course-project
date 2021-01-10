package main.models;

public class Module {
    public Module(int id, String score){
        this.id = id;
        this.score = score;
    }
    private int id;
    private String score;

    public int getId ( ) {
        return id;
    }

    public void setId ( int id ) {
        this.id = id;
    }

    public String getScore ( ) {
        return score;
    }

    public void setScore ( String score ) {
        this.score = score;
    }
}
