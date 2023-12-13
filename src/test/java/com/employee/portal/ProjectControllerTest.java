package com.employee.portal;
import com.employee.controller.ProjectController;
import com.employee.projects.Project;
import com.employee.projects.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Project Controller Test")
public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test Get All Projects")
    public void testGetAllProjects() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(/* project details */));
        projects.add(new Project(/* project details */));

        when(projectService.getAllProjects()).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getAllProjects();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    @DisplayName("Test Get Project By Id - Project Found")
    public void testGetProjectById_ProjectFound() {
        Project project = new Project(/* project details */);
        int projectId = 1;

        when(projectService.getProjectById(projectId)).thenReturn(Optional.of(project));

        ResponseEntity<Project> response = projectController.getProjectById(projectId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(project, response.getBody());
    }

    @Test
    @DisplayName("Test Get Project By Id - Project Not Found")
    public void testGetProjectById_ProjectNotFound() {
        int projectId = 1;

        when(projectService.getProjectById(projectId)).thenReturn(Optional.empty());

        ResponseEntity<Project> response = projectController.getProjectById(projectId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    @DisplayName("Test Create Project")
    public void testCreateProject() {
        Project newProject = new Project(/* project details */);

        when(projectService.createProject(newProject)).thenReturn(newProject);

        ResponseEntity<Project> response = projectController.createProject(newProject);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newProject, response.getBody());
    }

    @Test
    @DisplayName("Test Update Project")
    public void testUpdateProject() {
        int projectId = 1;
        Project updatedProject = new Project(/* updated project details */);

        when(projectService.updateProject(projectId, updatedProject)).thenReturn(updatedProject);

        ResponseEntity<Project> response = projectController.updateProject(projectId, updatedProject);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProject, response.getBody());
    }

    @Test
    @DisplayName("Test Delete Project")
    public void testDeleteProject() {
        int projectId = 1;

        ResponseEntity<Void> response = projectController.deleteProject(projectId);

        verify(projectService, times(1)).deleteProject(projectId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}
