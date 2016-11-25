
package ua.ukma.nc.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import ua.ukma.nc.entity.Criterion;

/**
 *@author Oleh Khomandiak
*/
public class JsonWrapperReview{
    @JsonProperty
    private List<MarkCommentDto> data;
    @JsonProperty
    private String comment;
    @JsonProperty
    private long meetingId;
    
    
    public JsonWrapperReview() {}

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<MarkCommentDto> getData() {
        return data;
    }

    public void setData(List<MarkCommentDto> data) {
        this.data = data;
    }

	public long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(long meetingId) {
		this.meetingId = meetingId;
	}
}