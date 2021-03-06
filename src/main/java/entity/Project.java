/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 *
 * @author Doink
 */
@Entity
@Table(name = "PROJECT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p")
    , @NamedQuery(name = "Project.findById", query = "SELECT p FROM Project p WHERE p.id = :id")
    , @NamedQuery(name = "Project.findByName", query = "SELECT p FROM Project p WHERE p.name = :name")
    , @NamedQuery(name = "Project.findByCreatedAt", query = "SELECT p FROM Project p WHERE p.createdAt = :createdAt")
    , @NamedQuery(name = "Project.findByClosedAt", query = "SELECT p FROM Project p WHERE p.closedAt = :closedAt")})
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATED_AT", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt = new Date();
    
    @Column(name = "CLOSED_AT")
    @Temporal(TemporalType.DATE)
    private Date closedAt;
    
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private Customer customerId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<ProjectPhase> projectPhaseCollection;

    public Project() {
    }

    public Project(Integer id) {
        this.id = id;
    }

    public Project(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.createdAt = new Date();
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public Collection<ProjectPhase> getProjectPhaseCollection() {
        return projectPhaseCollection;
    }

    public void setProjectPhaseCollection(Collection<ProjectPhase> projectPhaseCollection) {
        this.projectPhaseCollection = projectPhaseCollection;
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
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Project[ id=" + id + " ]";
    }
    
    public Integer getProgressPercent(){
        double all = this.projectPhaseCollection.size();
        double done = 0;
        Iterator<ProjectPhase> phases = this.projectPhaseCollection.iterator();
        while(phases.hasNext()) {
            if (phases.next().getCompleted()) { done++; }
        }
        if (all < 1) { return 0; }
        return (int)(done/all*100);
    }
    
    public Integer getFullPrice(){
        Integer ret = 0;
        Iterator<ProjectPhase> phases = this.projectPhaseCollection.iterator();
        while(phases.hasNext()) {
            ret += phases.next().getFullPrice();
        }
        return ret;
    }
    
    public Integer getEstimatedPrice(){
        Integer ret = 0;
        Iterator<ProjectPhase> phases = this.projectPhaseCollection.iterator();
        while(phases.hasNext()) {
            ret += phases.next().getEstimatedPrice();
        }
        return ret;
    }
    
    public Integer getDurationInDays(){
        if (this.closedAt == null) {
            return null;
        }
        return Days.daysBetween(new DateTime(this.createdAt), new DateTime(this.closedAt)).getDays();
    }
    
}
