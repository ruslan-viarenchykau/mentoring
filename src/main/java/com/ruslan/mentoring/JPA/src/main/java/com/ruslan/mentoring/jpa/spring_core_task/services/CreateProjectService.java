package com.ruslan.mentoring.jpa.spring_core_task.services;

import com.ruslan.mentoring.jpa.models.Project;
import org.springframework.stereotype.Service;

@Service
public class CreateProjectService {
    public Project createProject() {
        Project project = new Project();
        project.setName("ORM");
        return project;
    }
}
