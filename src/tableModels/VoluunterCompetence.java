package tableModels;

import javafx.scene.control.CheckBox;

public class VoluunterCompetence {
    private CheckBox check;
    private String name;
    private String sex;
    private String age;
    private String region;

    private int id;

    public void setCheck ( CheckBox check ) {
        this.check = check;
    }

    public int getId ( ) {
        return id;
    }

    public void setId ( int id ) {
        this.id = id;
    }

    public VoluunterCompetence( String name, String sex, String age, String region, int userId) {
        check = new CheckBox();
        this.name = name;
        this.id = userId;
        this.sex = sex;
        this.age = age;
        this.region = region;
    }

    public CheckBox getCheck() {
        return check;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getAge() {
        return age;
    }

    public String getRegion() {
        return region;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
