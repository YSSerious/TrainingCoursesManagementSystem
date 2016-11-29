package ua.ukma.nc.vo;

/**
 *
 * @author michael
 */
public class GroupVo {
    
    private String name;
    private Long projectId;

    public GroupVo() {
    }

    public GroupVo(String name, Long projectId) {
        this.name = name;
        this.projectId = projectId;
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
