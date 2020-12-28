package main.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table ( name = "users" )
public class User {
    public User ( String firstName , String lastName , Date birthdayDate , String phoneNumber , String password , String login , String email , Role role , Region region ) {
        this.firstName    = firstName;
        this.lastName     = lastName;
        this.birthdayDate = birthdayDate;
        this.phoneNumber  = phoneNumber;
        this.password     = password;
        this.login        = login;
        this.email        = email;
        this.role         = role;
        this.region       = region;
    }

    public User(){

    }

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private int id;

    @Column ( name = "first_name" )
    private String firstName;

    @Column ( name = "last_name" )
    private String lastName;

    @Column ( name = "date_of_birthday" )
    private Date birthdayDate;

    @Column ( name = "phone_number" )
    private String phoneNumber;

    @Column ( name = "password" )
    private String password;

    @Column ( name = "login" )
    private String login;

    @Column ( name = "email" )
    private String email;

    @OneToOne ( cascade = CascadeType.ALL )
    @JoinColumn ( name = "role_id" )
    private Role role;

    @OneToOne ( cascade = CascadeType.ALL )
    @JoinColumn ( name = "region_id" )
    private Region region;

    @ManyToMany
    @JoinTable(name = "user_championships",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "champ_id"))
    private List<Championship> championships;

    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Result> results;

    public int getId ( ) {
        return id;
    }

    public void setId ( int id ) {
        this.id = id;
    }

    public String getFirstName ( ) {
        return firstName;
    }

    public void setFirstName ( String firstName ) {
        this.firstName = firstName;
    }

    public String getLastName ( ) {
        return lastName;
    }

    public void setLastName ( String lastName ) {
        this.lastName = lastName;
    }

    public Date getBirthdayDate ( ) {
        return birthdayDate;
    }

    public void setBirthdayDate ( Date birthdayDate ) {
        this.birthdayDate = birthdayDate;
    }

    public String getPhoneNumber ( ) {
        return phoneNumber;
    }

    public void setPhoneNumber ( String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword ( ) {
        return password;
    }

    public void setPassword ( String password ) {
        this.password = password;
    }

    public String getLogin ( ) {
        return login;
    }

    public void setLogin ( String login ) {
        this.login = login;
    }

    public String getEmail ( ) {
        return email;
    }

    public void setEmail ( String email ) {
        this.email = email;
    }

    public Role getRole ( ) {
        return role;
    }

    public void setRole ( Role role ) {
        this.role = role;
    }

    public Region getRegion ( ) {
        return region;
    }

    public void setRegion ( Region region ) {
        this.region = region;
    }

    public List<Championship> getChampionships ( ) {
        return championships;
    }

    public void setChampionships ( List<Championship> championships ) {
        this.championships = championships;
    }

    public List<Result> getResults ( ) {
        return results;
    }

    public void setResults ( List<Result> results ) {
        this.results = results;
    }
}
