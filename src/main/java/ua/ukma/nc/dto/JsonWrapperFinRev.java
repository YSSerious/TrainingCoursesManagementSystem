package ua.ukma.nc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.ukma.nc.entity.FinalReviewCriterion;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alex_Frankiv on 20.11.2016.
 */
public class JsonWrapperFinRev{
    @JsonProperty
    private List<FinalReviewCriterion> data;
    @JsonProperty
    private String comment;

    public JsonWrapperFinRev() {}

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<FinalReviewCriterion> getData() {
        return data;
    }

    public void setData(List<FinalReviewCriterion> data) {
        this.data = data;
    }
}