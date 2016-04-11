package com.ruslan.mentoring.jpa.controllers;

import com.ruslan.mentoring.jpa.models.Project;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/project")
public class ProjectController extends AbstractController {
    private static final String ENTITY_KEY = "project";

    /*   F I N D   */

    @RequestMapping(method = RequestMethod.GET, path = "read")
    public String find(Model model, @RequestParam Long id) {
        Project project = entityManager.find(Project.class, id);
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

    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "create")
    public String create(Model model, @ModelAttribute Project project) {
        entityManager.persist(project);
        model.addAttribute(MESSAGE_KEY, "Project created");
        return "index";
    }

    /*   U P D A T E   */

    @RequestMapping(method = RequestMethod.GET, path = "update")
    public String getUpdateForm(Model model, Project project, @RequestParam Long id) {
        project = entityManager.find(Project.class, id);
        if (project == null) {
            model.addAttribute(MESSAGE_KEY, "Project not found");
            return "index";
        } else {
            model.addAttribute(ENTITY_KEY, project);
            return "project/project.update";
        }
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "update")
    public String update(Model model, @ModelAttribute Project project) {
        Project unitById = entityManager.find(Project.class, project.getId());
        if (unitById != null) {
            entityManager.merge(project);
            model.addAttribute(MESSAGE_KEY, "Project updated");
        } else {
            model.addAttribute(MESSAGE_KEY, "Project not found");
        }
        return "index";
    }

    /*   D E L E T E   */

    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "delete")
    public String delete(Model model, @RequestParam Long id) {
        Project project = entityManager.find(Project.class, id);
        if (project != null) {
            entityManager.remove(project);
            model.addAttribute(MESSAGE_KEY, "Project deleted");
        } else {
            model.addAttribute(MESSAGE_KEY, "Project not found");
        }
        return "index";
    }
}
