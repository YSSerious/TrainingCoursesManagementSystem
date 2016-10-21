package ua.ukma.nc.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Алексей on 15.10.2016.
 */
public interface User extends Serializable{

    void setId(Long id);

    Long getId();

    void setFirstName(String fName);

    String getFirstName();

    void setSecondName(String fName);

    String getSecondName();

    void setLastName(String lName);

    String getLastName();

    void setRegistrationDate(Timestamp registrationDate);

    Timestamp getRegistrationDate();

    void setPassword(String password);

    String getPassword();

    void setEmail(String email);

    String getEmail();

    void setActive(boolean active);

    boolean isActive();

}
