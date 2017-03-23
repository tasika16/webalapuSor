/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Doink
 */
@Stateless
public class ProjectPhaseFacade extends AbstractFacade<ProjectPhase> {

    @PersistenceContext(unitName = "com.mycompany_Munkanyilvantarto_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjectPhaseFacade() {
        super(ProjectPhase.class);
    }
    
}
