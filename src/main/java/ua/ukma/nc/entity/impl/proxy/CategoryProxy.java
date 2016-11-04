package ua.ukma.nc.entity.impl.proxy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import ua.ukma.nc.entity.Category;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.impl.real.CategoryImpl;
import ua.ukma.nc.service.CategoryService;

/**
 * Created by Алексей on 30.10.2016.
 */
public class CategoryProxy implements Category{

    private static final long serialVersionUID = -7513199305076908631L;

    private Long id;

    private CategoryImpl category;

    @Autowired
    private CategoryService categoryService;

    public CategoryProxy() {
    }

    public CategoryProxy(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return category.getName();
    }

    @Override
    public void setName(String name) {
        downloadCategory();
        category.setName(name);
    }

    @Override
    public String getDescription() {
        return category.getDescription();
    }

    @Override
    public void setDescription(String description) {
        downloadCategory();
        category.setDescription(description);
    }

    private void downloadCategory() {
        if (category == null) {
            category = (CategoryImpl) categoryService.getById(id);
        }
    }

    @Override
    public String toString() {
        return "Proxy "+id;
    }

	@Override
	public List<Criterion> getCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCriteria(List<Criterion> criteria) {
		// TODO Auto-generated method stub
		
	}
}
