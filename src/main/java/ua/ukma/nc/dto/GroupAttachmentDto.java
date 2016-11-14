package ua.ukma.nc.dto;

import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.GroupAttachment;

public class GroupAttachmentDto {
	Long id;
	GroupDto group;
	String name;
	String attachmentScope;
	public GroupAttachmentDto() {
		
	}
	public GroupAttachmentDto(GroupAttachment ga){
		setId(ga.getId());
		setGroup(new GroupDto(ga.getGroup()));
		setName(ga.getName());
		setAttachmentScope(ga.getAttachmentScope());
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public GroupDto getGroup() {
		return group;
	}
	public void setGroup(GroupDto group) {
		this.group = group;
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
}
