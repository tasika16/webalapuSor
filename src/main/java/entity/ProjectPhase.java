/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Doink
 */

@Entity
@Table(name = "PROJECT_PHASE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectPhase.findAll", query = "SELECT p FROM ProjectPhase p")
    , @NamedQuery(name = "ProjectPhase.findById", query = "SELECT p FROM ProjectPhase p WHERE p.id = :id")
    , @NamedQuery(name = "ProjectPhase.findByName", query = "SELECT p FROM ProjectPhase p WHERE p.name = :name")})
public class ProjectPhase implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "NAME", nullable = false, length = 512)
    private String name;
    
    @Column(name = "COMPLETED")
    private Boolean completed;
    
    @ManyToMany(mappedBy = "projectPhaseCollection")
    private Collection<Employee> employeeCollection;

    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private Project projectId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectPhaseId")
    private Collection<PayItem> payItemCollection;
    
    @Column(name = "ESTIMATED_PRICE")
    private Integer estimatedPrice;

    public ProjectPhase() {
    }

    public ProjectPhase(Integer id) {
        this.id = id;
    }

    public ProjectPhase(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(Integer estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @XmlTransient
    public Collection<Employee> getEmployeeCollection() {
        return employeeCollection;
    }

    public void setEmployeeCollection(Collection<Employee> employeeCollection) {
        this.employeeCollection = employeeCollection;
    }

    @XmlTransient
    public Collection<PayItem> getPayItemCollection() {
        return payItemCollection;
    }

    public void setPayItemCollection(Collection<PayItem> payItemCollection) {
        this.payItemCollection = payItemCollection;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProjectPhase)) {
            return false;
        }
        ProjectPhase other = (ProjectPhase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProjectPhase[ id=" + id + " ]";
    }
    
}
