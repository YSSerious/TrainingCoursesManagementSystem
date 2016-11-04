package ua.ukma.nc.entity.impl.real;

import java.util.List;

import ua.ukma.nc.entity.Category;
import ua.ukma.nc.entity.Criterion;

/**
 * Created by Алексей on 30.10.2016.
 */
public class CategoryImpl implements Category {

	private static final long serialVersionUID = 5701151316943360749L;
	private Long id;
	private String name;
	private String description;
	private List<Criterion> criteria;

	public void setCriteria(List<Criterion> criteria) {
		this.criteria = criteria;
	}

	public List<Criterion> getCriteria() {
		return criteria;
	}

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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		CategoryImpl category = (CategoryImpl) o;

		if (id != null ? !id.equals(category.id) : category.id != null)
			return false;
		if (name != null ? !name.equals(category.name) : category.name != null)
			return false;
		return description != null ? description.equals(category.description) : category.description == null;

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "CategoryImpl{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + '}';
	}
}
