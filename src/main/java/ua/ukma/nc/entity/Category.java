package ua.ukma.nc.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Алексей on 30.10.2016.
 */
public interface Category extends Serializable {
	Long getId();

	void setId(Long id);

	String getName();

	void setName(String name);

	String getDescription();

	void setDescription(String description);

	List<Criterion> getCriteria();

	void setCriteria(List<Criterion> criteria);
}
