package ua.ukma.nc.entity.impl.real;

import ua.ukma.nc.entity.Category;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Project;

import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public class CriterionImpl implements Criterion{

    private static final long serialVersionUID = 6658492320620701651L;
    private Long id;
    private String title;
    private Category category;

    public CriterionImpl() {
    }

    public CriterionImpl(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public CriterionImpl(String title) {
        this.title = title;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CriterionImpl criterion = (CriterionImpl) o;

        if (id != null ? !id.equals(criterion.id) : criterion.id != null) return false;
        if (title != null ? !title.equals(criterion.title) : criterion.title != null) return false;
        return category != null ? category.equals(criterion.category) : criterion.category == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CriterionImpl{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category=" + category +
                '}';
    }
}
