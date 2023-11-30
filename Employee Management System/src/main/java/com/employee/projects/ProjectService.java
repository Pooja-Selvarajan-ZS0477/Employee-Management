package com.employee.projects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectsRepository;

    public List<Project> getAllProjects() {
        return projectsRepository.findAll();
    }

    public Optional<Project> getProjectById(int id) {
        return projectsRepository.findById(id);
    }

    public Project createProject(Project project) {
        return projectsRepository.save(project);
    }

    public Project updateProject(int id, Project updatedProject) {
        Optional<Project> optionalProject = projectsRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project existingProject = optionalProject.get();
            existingProject.setProject_name(updatedProject.getProject_name());
            existingProject.setCustomer_name(updatedProject.getCustomer_name());
            
            return projectsRepository.save(existingProject);
        } else {
            throw new RuntimeException("Project not found with id: " + id);
        }
    }

    public void deleteProject(int id) {
        projectsRepository.deleteById(id);
    }
}
