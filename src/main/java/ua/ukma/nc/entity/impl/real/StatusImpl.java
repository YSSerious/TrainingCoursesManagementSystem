package ua.ukma.nc.entity.impl.real;

import ua.ukma.nc.entity.Status;

/**
 * Created by Алексей on 30.10.2016.
 */
public class StatusImpl implements Status{

    private static final long serialVersionUID = 1863308556139452608L;
    private Long id;
    private String title;
    private String description;

    public StatusImpl() {
    }

    public StatusImpl(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusImpl status = (StatusImpl) o;

        if (id != null ? !id.equals(status.id) : status.id != null) return false;
        if (title != null ? !title.equals(status.title) : status.title != null) return false;
        return description != null ? description.equals(status.description) : status.description == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StatusImpl{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
