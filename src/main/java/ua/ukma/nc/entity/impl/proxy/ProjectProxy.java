package ua.ukma.nc.entity.impl.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.ukma.nc.entity.Project;
import ua.ukma.nc.entity.impl.real.ProjectImpl;
import ua.ukma.nc.service.ProjectService;

import java.sql.Date;

/**
 * Created by Алексей on 30.10.2016.
 */
@Component
@Scope("prototype")
public class ProjectProxy implements Project {

	private static final long serialVersionUID = 1163813234179514959L;
	private Long id;
	private ProjectImpl project;

	@Autowired
	private ProjectService projectService;

	public ProjectProxy() {
	}

	public ProjectProxy(Long id) {
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
		downloadProject();
		return project.getName();
	}

	@Override
	public void setName(String name) {
		downloadProject();
		project.setName(name);
	}

	@Override
	public String getDescription() {
		downloadProject();
		return project.getDescription();
	}

	@Override
	public void setDescription(String description) {
		downloadProject();
		project.setDescription(description);
	}

	@Override
	public Date getStartDate() {
		downloadProject();
		return project.getStartDate();
	}

	@Override
	public void setStartDate(Date startDate) {
		downloadProject();
		project.setStartDate(startDate);
	}

	@Override
	public Date getFinishDate() {
		downloadProject();
		return project.getFinishDate();
	}

	@Override
	public void setFinishDate(Date finishDate) {
		downloadProject();
		project.setFinishDate(finishDate);
	}

	private void downloadProject() {
		if (project == null) {
			project = (ProjectImpl) projectService.getById(id);
		}
	}

	@Override
	public String toString() {
		return "Proxy " + id;
	}
}
