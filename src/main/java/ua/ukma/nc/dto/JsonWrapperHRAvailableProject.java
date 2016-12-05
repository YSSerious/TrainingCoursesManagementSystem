package ua.ukma.nc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Alex_Frankiv on 05.12.2016.
 */
public class JsonWrapperHRAvailableProject {
    @JsonProperty
    private ProjectDto project;
    @JsonProperty
    private char available;

    public ProjectDto getProject() {
        return project;
    }

    public void setProject(ProjectDto project) {
        this.project = project;
    }

    public char getAvailable() {
        return available;
    }

    public void setAvailable(char available) {
        this.available = available;
    }
}
