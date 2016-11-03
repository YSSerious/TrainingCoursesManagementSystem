package ua.ukma.nc.entity;

import java.io.Serializable;

/**
 * Created by Алексей on 30.10.2016.
 */
public class ProjectAttachment implements Serializable{

    private static final long serialVersionUID = -433930957694971182L;
    private Long id;
    private Project project;
    private String name;
    private String attachmentScope;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

        ProjectAttachment that = (ProjectAttachment) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return attachmentScope != null ? attachmentScope.equals(that.attachmentScope) : that.attachmentScope == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (attachmentScope != null ? attachmentScope.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProjectAttachment{" +
                "id=" + id +
                ", project=" + project +
                ", name='" + name + '\'' +
                ", attachmentScope='" + attachmentScope + '\'' +
                '}';
    }
}
