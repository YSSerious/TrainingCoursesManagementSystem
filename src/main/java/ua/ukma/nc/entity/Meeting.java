package ua.ukma.nc.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface Meeting extends Serializable {
    Long getId();

    void setId(Long id);

    Group getGroup();

    void setGroup(Group group);

    String getName();

    void setName(String name);

    Timestamp getTime();

    void setTime(Timestamp time);

    String getPlace();

    void setPlace(String place);

    List<Criterion> getCriterions();

    void setCriterions(List<Criterion> criterions);
}
