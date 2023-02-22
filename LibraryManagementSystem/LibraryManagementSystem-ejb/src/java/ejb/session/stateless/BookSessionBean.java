/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Book;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wjahoward
 */
@Stateless
public class BookSessionBean implements BookSessionBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "LibraryManagementSystem-ejbPU")
    private EntityManager em;
    
    @Override
    public void createNewBook(Book b) {
        em.persist(b);
    }
}
