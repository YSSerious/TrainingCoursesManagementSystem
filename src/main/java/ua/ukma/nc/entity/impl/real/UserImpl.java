package ua.ukma.nc.entity.impl.real;

import ua.ukma.nc.entity.User;

import java.sql.Timestamp;

/**
 * Created by Алексей on 21.10.2016.
 */
public class UserImpl implements User{

    private static final long serialVersionUID = 1046417900828791006L;

    private Long id;
    private String firstName;
    private String secondName;
    private String lastName;
    private Timestamp registrationDate;
    private String password;
    private String email;
    private boolean isActive;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getSecondName() {
        return secondName;
    }

    @Override
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean active) {
        isActive = active;
    }

}
