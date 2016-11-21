package ua.ukma.nc.dto;

import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Group;

import java.util.List;

/**
 * Created by Алексей on 19.11.2016.
 */
public class ProjectsCriteriaGroupsDto {
    private List<Group> groupList;
    private List<Criterion> criterions;

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public List<Criterion> getCriterions() {
        return criterions;
    }

    public void setCriterions(List<Criterion> criterions) {
        this.criterions = criterions;
    }
}
