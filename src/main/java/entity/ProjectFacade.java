/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Doink
 */
@Stateless
public class ProjectFacade extends AbstractFacade<Project> {

    @PersistenceContext(unitName = "com.mycompany_Munkanyilvantarto_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjectFacade() {
        super(Project.class);
    }
    
    public Long findProjectCountByStatus(boolean isClosed){
        if (isClosed) {
            return (Long) em.createQuery("SELECT count(p) FROM Project p WHERE p.closedAt is not null").getSingleResult();
        } else {
            return (Long) em.createQuery("SELECT count(p) FROM Project p WHERE p.closedAt is null").getSingleResult();
        }
    }
    
    public List<Project> findOpenProjects(){
        return em.createQuery("SELECT p FROM Project p WHERE p.closedAt is null", Project.class)
                .getResultList();
    }
    
    public List<Project> findClosedProjects(){
        return em.createQuery("SELECT p FROM Project p WHERE p.closedAt is not null", Project.class)
                .getResultList();
    }
    public Project findById(Integer id){
        return em.createQuery("SELECT p FROM Project p WHERE p.id = :id", Project.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
