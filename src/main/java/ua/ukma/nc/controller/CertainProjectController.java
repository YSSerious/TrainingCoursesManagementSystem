package ua.ukma.nc.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import ua.ukma.nc.dto.*;
import ua.ukma.nc.entity.Criterion;
import ua.ukma.nc.entity.Group;
import ua.ukma.nc.entity.Meeting;
import ua.ukma.nc.entity.ProjectAttachment;
import ua.ukma.nc.service.CriterionService;
import ua.ukma.nc.service.GroupService;
import ua.ukma.nc.service.MeetingService;
import ua.ukma.nc.service.ProjectAttachmentService;
import ua.ukma.nc.service.ProjectService;
import ua.ukma.nc.util.exception.CriteriaDeleteException;

@Controller
public class CertainProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private GroupService groupService;
    @Autowired
    private CriterionService criterionService;
    
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private ProjectAttachmentService attachmentService;

    private Long project_id;

    @RequestMapping(value = "/certainProject/{id}", method = RequestMethod.GET)
    public ModelAndView viewProject(@PathVariable("id") Long id) {
        ModelAndView model = new ModelAndView();
        project_id = id;

        ProjectDto prDto = new ProjectDto(projectService.getById(id));
        model.addObject("project", prDto);

        //Group set
        List<Group> groupList = groupService.getByProjectId(id);
        List<GroupProjectDto> groupDtos = new ArrayList();
        for (Group group: groupList) {
            Meeting upcomingMeeting = meetingService.getUpcomingByGroup(group.getId());
            Long studentsAmount = groupService.getStudentsAmount(group.getId());
            GroupProjectDto groupDto = new GroupProjectDto(group, upcomingMeeting, studentsAmount);
            groupDtos.add(groupDto);
        }
        model.addObject("groups", groupDtos);

        //Criteria set
        List<CriterionDto> criterionDtos = new ArrayList<>();
        for(Criterion criterion: criterionService.getByProject(id)){
            criterionDtos.add(new CriterionDto(criterion.getId(), criterion.getTitle(), criterionService.isRatedInProject(id, criterion)));
        }
        model.addObject("criterions", criterionDtos);
        //Attachment set
        List<ProjectAttachment> attachmentList = attachmentService.getAllById(id);
        model.addObject("attachments", attachmentList);

        model.setViewName("certainProject");
        return model;
    }

    @RequestMapping(value = "/addProjectAttachment", method = RequestMethod.POST)
    public void addGroupAttachment
            (@RequestParam("attachmentName") String attachmentName,
             @RequestParam("attachmentLink") String attachmentLink) {

        ProjectAttachment att = new ProjectAttachment();
        att.setName(attachmentName);
        att.setAttachmentScope(attachmentLink);
        att.setProject(projectService.getById(project_id));
        attachmentService.createProjectAttachment(att);

    }

    @RequestMapping(value = "/removeProjectAttachment", method = RequestMethod.POST)
    public void removeGroupAttachment(@RequestParam("id_attachment") Long id) {
        attachmentService.deleteProjectAttachment(attachmentService.getById(id));
    }

    
    
    @RequestMapping(value = "/getAvailableCriteria", method = RequestMethod.GET)
    @ResponseBody
    public List<CriterionDto> getAvailableCriteria(@RequestParam Long projectId) {
        List<CriterionDto> criterionDtos = new ArrayList<>();
        for (Criterion criterion : criterionService.getProjectUnusedCriteria(projectId)) {
            criterionDtos.add(new CriterionDto(criterion));
        }
        return criterionDtos;
    }

    @RequestMapping(value = "/addCriteria", method = RequestMethod.POST)
    @ResponseBody
    public CriterionDto addCriteria(@RequestParam Long projectId, @RequestParam String criteriaTitle) {
        Criterion criterion = criterionService.getByName(criteriaTitle);
        projectService.addCriteria(projectId, criterion);
        return new CriterionDto(criterion.getId(), criterion.getTitle(), criterionService.isRatedInProject(projectId, criterion));
    }

    @RequestMapping(value = "/deleteProjectCriteria", method = RequestMethod.POST)
    @ResponseBody
    public String deleteProjectCriteria(@RequestParam Long projectId, @RequestParam String criteriaTitle) throws CriteriaDeleteException {
        if(criterionService.isRatedInProject(projectId, criterionService.getByName(criteriaTitle)))
            throw new CriteriaDeleteException("This criteria was rated and cannot be deleted");
        projectService.deleteProjectCriterion(projectId, criterionService.getByName(criteriaTitle));
        return "success";
    }

    @RequestMapping(value = "/getCriteriaAndGroups", method = RequestMethod.GET)
    @ResponseBody
    public ProjectsCriteriaGroupsDto getCriteriaAndGroups(@RequestParam Long projectId) {
        return new ProjectsCriteriaGroupsDto(groupService.getByProjectId(projectId), criterionService.getByProject(projectId));
    }

    @RequestMapping(value = "/saveMeeting", method = RequestMethod.POST)
    @ResponseBody
    public int saveMeeting(@RequestBody AddCriteriaDto dto) {
        System.out.println(dto);
        return meetingService.addMeetings(dto);
    }


}
