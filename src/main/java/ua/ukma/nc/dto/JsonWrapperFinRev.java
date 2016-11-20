package ua.ukma.nc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.ukma.nc.entity.FinalReviewCriterion;

import java.util.LinkedList;

/**
 * Created by Alex_Frankiv on 20.11.2016.
 */
public class JsonWrapperFinRev{
    @JsonProperty
    private LinkedList<FinalReviewCriterion> data;
    @JsonProperty
    private String comment;

    JsonWrapperFinRev() {}

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LinkedList<FinalReviewCriterion> getData() {
        return data;
    }

    public void setData(LinkedList<FinalReviewCriterion> data) {
        this.data = data;
    }
}