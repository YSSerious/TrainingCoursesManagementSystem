package ua.ukma.nc.dto;

import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.impl.real.CriterionImpl;
import ua.ukma.nc.entity.impl.real.GroupImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Алексей on 19.11.2016.
 */
public class ProjectsCriteriaGroupsDto {
    private List<Group> groupList;
    private List<Criterion> criterions;

    public ProjectsCriteriaGroupsDto() {
    }

    public ProjectsCriteriaGroupsDto(List<Group> groupList, List<Criterion> criterions) {
        this.groupList=new ArrayList<>();
        this.criterions=new ArrayList<>();
        for(Group group:groupList){
            this.groupList.add(new GroupImpl(group.getId(), group.getName()));
        }

        for(Criterion criterion: criterions){
            this.criterions.add(new CriterionImpl(criterion.getId(), criterion.getTitle()));
        }

    }

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
