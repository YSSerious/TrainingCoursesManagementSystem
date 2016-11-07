package ua.ukma.nc.query;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ProjectSearch {

	private Boolean searchRequest;

	public static final String START = "startType";
	public static final String END = "endType";
	public static final String START_AND_END = "startAndEndType";

	public static final String UPCOMING = "upcoming";
	public static final String ACTIVE = "active";
	public static final String COMPLETED = "completed";

	public static final String NAME_ASC = "nameasc";
	public static final String NAME_DESC = "namedesc";
	public static final String START_ASC = "startasc";
	public static final String START_DESC = "startdesc";
	public static final String END_ASC = "finishasc";
	public static final String END_DESC = "finishdesc";

	private String order;
	private String[] statuses;
	private Long[] criteria;
	private String text;
	private Integer page;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date start;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date end;
	private String dateType;

	public String getDateType() {
		if (dateType == null || dateType.equals(""))
			return null;

		if (isDateTypeValid(dateType))
			return dateType;

		return START;
	}

	private boolean isDateTypeValid(String dateType) {
		return START.equals(dateType) || END.equals(dateType) || START_AND_END.equals(dateType);
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getOrder() {
		if (order == null || order.equals("") || !isOrderValid(order))
			return null;
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String[] getStatuses() {
		if (statuses == null || statuses.length == 0)
			return null;
		return statuses;
	}

	public void setStatuses(String[] statuses) {
		this.statuses = statuses;
	}

	public Long[] getCriteria() {
		if (criteria == null || criteria.length == 0)
			return null;
		return criteria;
	}

	public void setCriteria(Long[] criteria) {
		this.criteria = criteria;
	}

	public String getText() {
		if (text == null || text.equals(""))
			return null;
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	private boolean isOrderValid(String order) {
		return order.equals(END_ASC) || order.equals(NAME_ASC) || order.equals(START_ASC) || order.equals(END_DESC)
				|| order.equals(NAME_DESC) || order.equals(START_DESC);
	}

	public Boolean getSearchRequest() {
		return searchRequest;
	}

	public void setSearchRequest(Boolean searchRequest) {
		this.searchRequest = searchRequest;
	}

	public Integer getPage() {
		if (page == null || searchRequest)
			return 1;
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}
