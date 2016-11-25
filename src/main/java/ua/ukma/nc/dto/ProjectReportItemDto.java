package ua.ukma.nc.dto;

/**
 * Created by Alex_Frankiv on 25.11.2016.
 */
public class ProjectReportItemDto {

    private long projectId;
    private String projectName;
    private int numOfStarted;
    //altogether left + not invited
    private int numOfNotInvited;
    private int numOfWasInvited;
    private int numOfJobOffer;


    public int getNumOfStarted() {
        return numOfStarted;
    }

    public void setNumOfStarted(int numOfStarted) {
        this.numOfStarted = numOfStarted;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public int getNumOfNotInvited() {
        return numOfNotInvited;
    }

    public void setNumOfNotInvited(int numOfNotInvited) {
        this.numOfNotInvited = numOfNotInvited;
    }

    public int getNumOfWasInvited() {
        return numOfWasInvited;
    }

    public void setNumOfWasInvited(int numOfWasInvited) {
        this.numOfWasInvited = numOfWasInvited;
    }

    public int getNumOfJobOffer() {
        return numOfJobOffer;
    }

    public void setNumOfJobOffer(int numOfJobOffer) {
        this.numOfJobOffer = numOfJobOffer;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
