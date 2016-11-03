package ua.ukma.nc.entity;

import java.io.Serializable;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface Mark extends Serializable{
     int getValue();

     void setValue(int value);

     String getDescription();

     void setDescription(String description);
}
