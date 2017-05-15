/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Doink
 */
@Stateless
public class EmployeeFacade extends AbstractFacade<Employee> {

    @PersistenceContext(unitName = "com.mycompany_Munkanyilvantarto_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeFacade() {
        super(Employee.class);
    }

    @Override
    public void remove(Employee entity) {
        entity.setSoftDeleted(Boolean.TRUE);
        edit(entity);
    }

    @Override
    public List<Employee> findAll() {
        return em.createNamedQuery("Employee.findAll", Employee.class).getResultList();
    }
    
    
    public List<Employee> findFreeEmployees() {
        List<Employee> ret = findAll();
        List<ProjectPhase> ppList = em.createQuery("select pp from ProjectPhase pp").getResultList();
        
        for(ProjectPhase pp: ppList) {
            if (!pp.getCompleted()) {
                for(Employee e: pp.getEmployeeCollection()) {
                    ret.remove(e);
                }
            }
        }

        return ret;
    }
    
}
