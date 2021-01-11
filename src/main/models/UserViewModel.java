package main.models;

public class UserViewModel {
    private String firstName;
    private String secondName;
    private String birthdayDate;

    public UserViewModel ( String firstName , String secondName , String birthdayDate ) {
        this.firstName    = firstName;
        this.secondName   = secondName;
        this.birthdayDate = birthdayDate;
    }

    public String getFirstName ( ) {
        return firstName;
    }

    public void setFirstName ( String firstName ) {
        this.firstName = firstName;
    }

    public String getSecondName ( ) {
        return secondName;
    }

    public void setSecondName ( String secondName ) {
        this.secondName = secondName;
    }

    public String getBirthdayDate ( ) {
        return birthdayDate;
    }

    public void setBirthdayDate ( String birthdayDate ) {
        this.birthdayDate = birthdayDate;
    }
}
