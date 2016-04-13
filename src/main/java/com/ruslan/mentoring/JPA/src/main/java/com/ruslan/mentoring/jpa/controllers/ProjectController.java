package com.ruslan.mentoring.jpa.controllers;

import com.ruslan.mentoring.jpa.services.EntityManagerService;
import com.ruslan.mentoring.jpa.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/project")
public class ProjectController extends AbstractController {
    private static final String ENTITY_KEY = "project";

    @Autowired
    private EntityManagerService service;

    /*   F I N D   */

    @RequestMapping(method = RequestMethod.GET, path = "read")
    public String find(Model model, @RequestParam Long id) {
        Project project = service.find(Project.class, id);
        if (project == null) {
            model.addAttribute(MESSAGE_KEY, "Project not found");
            return "index";
        } else {
            model.addAttribute(ENTITY_KEY, project);
            return "project/project.view";
        }
    }

    /*   C R E A T E   */

    @RequestMapping(method = RequestMethod.GET, path = "create")
    public String getCreateForm(Model model, Project project) {
        return "project/project.create";
    }

    @RequestMapping(method = RequestMethod.POST, path = "create")
    public String create(Model model, @ModelAttribute Project project) {
        service.create(project);
        model.addAttribute(MESSAGE_KEY, "Project created");
        return "index";
    }

    /*   U P D A T E   */

    @RequestMapping(method = RequestMethod.GET, path = "update")
    public String getUpdateForm(Model model, @RequestParam Long id) {
        Project project = service.find(Project.class, id);
        if (project == null) {
            model.addAttribute(MESSAGE_KEY, "Project not found");
            return "index";
        } else {
            model.addAttribute(ENTITY_KEY, project);
            return "project/project.update";
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "update")
    public String update(Model model, @ModelAttribute Project project) {
        boolean updated = service.update(project);
        model.addAttribute(MESSAGE_KEY, updated ? "Project updated" : "Project not found");
        return "index";
    }

    /*   D E L E T E   */

    @RequestMapping(method = RequestMethod.POST, path = "delete")
    public String delete(Model model, @RequestParam Long id) {
        boolean deleted = service.delete(Project.class, id);
        model.addAttribute(MESSAGE_KEY, deleted ? "Project deleted" : "Project not found");
        return "index";
    }
}
