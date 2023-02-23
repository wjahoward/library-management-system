/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Staff;
import exception.StaffNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wjahoward
 */
@Stateless
public class StaffSessionBean implements StaffSessionBeanLocal {

    @PersistenceContext(unitName = "LibraryManagementSystem-ejbPU")
    private EntityManager em;

    @Override
    public void createNewStaff(Staff s) {
        em.persist(s);
    }
    
    @Override
    public Staff checkStaff(String username, String password) throws StaffNotFoundException {
        Query query = em.createQuery("SELECT s FROM Staff s WHERE s.userName = :userName AND s.password = :password");
        query.setParameter("userName", username);
        query.setParameter("password", password);
        
        try
        {
            return (Staff)query.getSingleResult();
        }
        catch(NoResultException ex)
        {
            throw new StaffNotFoundException("Invalid username and/or password!");
        }
    }
        
}
