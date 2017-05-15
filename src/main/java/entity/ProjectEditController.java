/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.util.JsfUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.EJBException;
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
    @EJB
    private entity.ProjectPhaseFacade projectPhaseFacade;
    @EJB
    private entity.PayItemFacade payItemFacade;
    @EJB
    private entity.EmployeeFacade employeeFacade;
    
    private Integer id;
    private Project project;
    private ProjectPhase newProjectPhase;
    private PayItem newPayItem;
    private EmployeeGroup newGroup;
    
    @PostConstruct
    public void init(){
        this.id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
        this.reloadModel();
    }
    
    public void reloadModel(){
        this.project = this.projectFacade.findById(this.id);
        this.newPayItem = new PayItem();
        this.newProjectPhase = new ProjectPhase();
    }
    
    public void removePhase(ProjectPhase phase) {
        this.project.getProjectPhaseCollection().remove(phase);
        try {
            projectPhaseFacade.remove(phase);
            projectFacade.edit(project);
        } catch (EJBException ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProjectDeleted"));
    }
    
    public void savePhaseGroup() {
        for (Employee e: newGroup.getEmployeeCollection()) {
            if (!e.getProjectPhaseCollection().contains(this.newProjectPhase)) {
                e.getProjectPhaseCollection().add(this.newProjectPhase);
            }
            this.newProjectPhase.getEmployeeCollection().add(e);
            this.employeeFacade.edit(e);
        }
        this.projectPhaseFacade.edit(this.newProjectPhase);
    }
    
    public void clearAllEmployee(ProjectPhase phase) {
        for (Employee e: phase.getEmployeeCollection()) {
            e.getProjectPhaseCollection().remove(phase);
            this.employeeFacade.edit(e);
        }
        phase.getEmployeeCollection().clear();
        this.projectPhaseFacade.edit(this.newProjectPhase);
    }
    
    public void lockProject(boolean locked) {
        if (locked) {
            this.project.setClosedAt(new Date());
        } else {
            this.project.setClosedAt(null);
        }
        try {
            this.projectFacade.edit(this.project);
        } catch (EJBException ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        JsfUtil.addSuccessMessage("A project lezárásra sikeres!");
    }
    
    public void lockPhase(ProjectPhase phase) {
        phase.setCompleted(Boolean.TRUE);
        try {
            projectPhaseFacade.edit(phase);
        } catch (EJBException ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        JsfUtil.addSuccessMessage("A fázis lezárásra sikeres!");
    }
    
    public void removeEmployee(ProjectPhase phase, Employee employee) {
        phase.getEmployeeCollection().remove(employee);
        try {
            projectPhaseFacade.edit(phase);
        } catch (EJBException ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EmployeeDeleted"));
    }
    
    public void removePayItem(ProjectPhase phase, PayItem payItem) {
        phase.getPayItemCollection().remove(payItem);
        try {
            payItemFacade.remove(payItem);
        } catch (EJBException ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProjectPhaseUpdated"));
    }
    
    public void createPayItem(ProjectPhase phase) {
        this.newPayItem = new PayItem();
        this.newPayItem.setProjectPhaseId(phase);
        selectProjectPhase(phase);
    }
    
    public void saveEmployeePhaseAssoc() {
        try {
            for (Employee e: this.newProjectPhase.getEmployeeCollection()) {
                if (!e.getProjectPhaseCollection().contains(newProjectPhase)) {
                    e.getProjectPhaseCollection().add(newProjectPhase);
                    this.employeeFacade.edit(e);
                }
            }
            this.projectPhaseFacade.edit(this.newProjectPhase);
            reloadModel();
        } catch (EJBException ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        JsfUtil.addSuccessMessage("A dolgozói hozzárendelések sikeresen mentve lettek!");       
    }
    
    public void savePayItem() {
        try {
            this.newProjectPhase = this.projectPhaseFacade.findById(this.newProjectPhase.getId());
            this.newProjectPhase.getPayItemCollection().add(newPayItem);
            this.projectPhaseFacade.edit(this.newProjectPhase);
            reloadModel();
        } catch (EJBException ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        JsfUtil.addSuccessMessage("A tétel sikeresen mentve lett!");       
    }
    
    public void selectProjectPhase(ProjectPhase pp) {
        this.newProjectPhase = pp;
    }
    
    public void createProjectPhase() {
        this.newProjectPhase = new ProjectPhase();
        this.newProjectPhase.setProjectId(this.project);
    }
    
    public void saveProjectPhase() {
        try {
            this.project.getProjectPhaseCollection().add(this.newProjectPhase);
            this.projectFacade.edit(this.project);
            //projectPhaseFacade.edit(this.newProjectPhase);
            reloadModel();
        } catch (EJBException ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        JsfUtil.addSuccessMessage("A munkafázis sikeresen mentve lett!");               
    }
    
    public boolean allowedToLockProject(){
        if (this.project.getClosedAt() != null) {
            return false;
        }
        for(ProjectPhase p: this.project.getProjectPhaseCollection()) {
            if (!p.getCompleted()) {
                return false;
            }
        }
        return true;
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
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ProjectPhase getNewProjectPhase() {
        return newProjectPhase;
    }

    public void setNewProjectPhase(ProjectPhase newProjectPhase) {
        this.newProjectPhase = newProjectPhase;
    }

    public PayItem getNewPayItem() {
        return newPayItem;
    }

    public void setNewPayItem(PayItem newPayItem) {
        this.newPayItem = newPayItem;
    }

    public EmployeeGroup getNewGroup() {
        return newGroup;
    }

    public void setNewGroup(EmployeeGroup newGroup) {
        this.newGroup = newGroup;
    }
    
}
