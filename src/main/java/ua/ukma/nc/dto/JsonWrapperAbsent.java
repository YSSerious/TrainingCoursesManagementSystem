
package ua.ukma.nc.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *@author Oleh Khomandiak
*/
public class JsonWrapperAbsent{

    @JsonProperty
    private long meetingId;

	public JsonWrapperAbsent() {

	}

	public long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(long meetingId) {
		this.meetingId = meetingId;
	}
    
    
   


}