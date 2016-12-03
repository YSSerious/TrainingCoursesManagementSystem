package ua.ukma.nc.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProjectAttachmentFormDto {
 
    private MultipartFile file;
    private Long projectId;
    private String name;
     
    public MultipartFile getFile() {
        return file;
    }
 
    public void setFile(MultipartFile file) {
        this.file = file;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
}