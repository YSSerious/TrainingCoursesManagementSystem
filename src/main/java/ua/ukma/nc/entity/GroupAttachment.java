package ua.ukma.nc.entity;

import java.io.Serializable;

/**
 * Created by Алексей on 30.10.2016.
 */
public class GroupAttachment implements Serializable{

    private static final long serialVersionUID = -1351713985625012290L;
    private Long id;
    private Group group;
    private String name;
    private String attachmentScope;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttachmentScope() {
        return attachmentScope;
    }

    public void setAttachmentScope(String attachmentScope) {
        this.attachmentScope = attachmentScope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupAttachment that = (GroupAttachment) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return attachmentScope != null ? attachmentScope.equals(that.attachmentScope) : that.attachmentScope == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (attachmentScope != null ? attachmentScope.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GroupAttachment{" +
                "id=" + id +
                ", group=" + group +
                ", name='" + name + '\'' +
                ", attachmentScope='" + attachmentScope + '\'' +
                '}';
    }
}
