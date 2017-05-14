/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Doink
 */
@Named(value = "projectEditController")
@ManagedBean
@ViewScoped
public class ProjectEditController implements Serializable {
    @EJB
    private entity.ProjectFacade projectFacade;
    private Integer id;
    private Project project;
    private ProjectPhase selectedPhase;
    
    @PostConstruct
    public void init(){
        this.id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
        this.project = this.projectFacade.findById(this.id);
    }
    
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ProjectPhase getSelectedPhase() {
        return selectedPhase;
    }

    public void setSelectedPhase(ProjectPhase selectedPhase) {
        this.selectedPhase = selectedPhase;
    }
    
    public void removePhase(ProjectPhase phase) {
        this.project.getProjectPhaseCollection().remove(phase);
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProjectFacade getFacade() {
        return projectFacade;
    }

    public void setFacade(ProjectFacade ejbFacade) {
        this.projectFacade = ejbFacade;
    }
    
}
