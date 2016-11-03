package ua.ukma.nc.entity.impl.real;

import ua.ukma.nc.entity.Project;

import java.sql.Timestamp;

/**
 * Created by Алексей on 30.10.2016.
 */
public class ProjectImpl implements Project {

    private static final long serialVersionUID = -8554114623958649016L;
    private Long id;
    private String name;
    private String description;
    private Timestamp startDate;
    private Timestamp finishDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Timestamp finishDate) {
        this.finishDate = finishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectImpl project = (ProjectImpl) o;

        if (id != null ? !id.equals(project.id) : project.id != null) return false;
        if (name != null ? !name.equals(project.name) : project.name != null) return false;
        if (description != null ? !description.equals(project.description) : project.description != null) return false;
        if (startDate != null ? !startDate.equals(project.startDate) : project.startDate != null) return false;
        return finishDate != null ? finishDate.equals(project.finishDate) : project.finishDate == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (finishDate != null ? finishDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProjectImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                '}';
    }
}
