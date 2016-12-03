package ua.ukma.nc.dto;

import org.springframework.web.multipart.MultipartFile;

public class GroupAttachmentFormDto {
 
    private MultipartFile file;
    private Long groupId;
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

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
}