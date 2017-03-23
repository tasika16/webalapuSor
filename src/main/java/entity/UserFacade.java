/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Doink
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "com.mycompany_Munkanyilvantarto_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    public User findUserByCredentiels(String email, String password) {
        TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class)
                .setParameter("email", email);
        
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (NoResultException e) { ; }
        
        if (user == null || !user.checkPassword(password)) {
            return null;
        }
        
        return user;
    }
}
