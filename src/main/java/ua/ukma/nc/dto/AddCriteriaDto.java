package ua.ukma.nc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Алексей on 20.11.2016.
 */
public class AddCriteriaDto {
    private String name;
    private String place;
    private String date;
    @JsonProperty("crit")
    private List<String> criterions;
    @JsonProperty("gr")
    private List<String> groups;

    public AddCriteriaDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getCriterions() {
        return criterions;
    }

    public void setCriterions(List<String> criterions) {
        this.criterions = criterions;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "AddCriteriaDto{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", date='" + date + '\'' +
                ", criterions=" + criterions +
                ", groups=" + groups +
                '}';
    }
}
