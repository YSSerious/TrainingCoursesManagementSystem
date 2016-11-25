
package ua.ukma.nc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Oleh Khomandiak
 */
public class MarkCommentDto {
	@JsonProperty
	private int value;
	@JsonProperty
	private int criterionId;
	@JsonProperty
	private String commentary;

	public int getValue() {
		return value;
	}

	public int getCriterionId() {
		return criterionId;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setCriterionId(int criterionId) {
		this.criterionId = criterionId;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	@Override
	public String toString() {
		return "MarkCommentDto [value=" + value + ", criterionId=" + criterionId + ", commentary=" + commentary + "]";
	}

}
