/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Doink
 */
@Entity
@Table(name = "PAY_ITEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PayItem.findAll", query = "SELECT p FROM PayItem p")
    , @NamedQuery(name = "PayItem.findById", query = "SELECT p FROM PayItem p WHERE p.id = :id")
    , @NamedQuery(name = "PayItem.findByName", query = "SELECT p FROM PayItem p WHERE p.name = :name")
    , @NamedQuery(name = "PayItem.findByValue", query = "SELECT p FROM PayItem p WHERE p.value = :value")})
public class PayItem implements Serializable {

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
    
    @Column(name = "VALUE")
    private Integer value;
    
    @JoinColumn(name = "PROJECT_PHASE_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private ProjectPhase projectPhaseId;

    public PayItem() {
    }

    public PayItem(Integer id) {
        this.id = id;
    }

    public PayItem(Integer id, String name) {
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public ProjectPhase getProjectPhaseId() {
        return projectPhaseId;
    }

    public void setProjectPhaseId(ProjectPhase projectPhaseId) {
        this.projectPhaseId = projectPhaseId;
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
        if (!(object instanceof PayItem)) {
            return false;
        }
        PayItem other = (PayItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PayItem[ id=" + id + " ]";
    }
    
}
