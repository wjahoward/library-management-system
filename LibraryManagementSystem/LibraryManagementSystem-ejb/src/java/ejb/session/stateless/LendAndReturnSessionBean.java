/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wjahoward
 */
@Stateless
public class LendAndReturnSessionBean implements LendAndReturnSessionBeanLocal {

    @PersistenceContext(unitName = "LibraryManagementSystem-ejbPU")
    private EntityManager em;


    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void createLendAndReturn(LendAndReturn lAR, Long bId, Long mId) {        
        Member m = em.find(Member.class, mId);
        Book b = em.find(Book.class, bId);
        
        em.persist(lAR);
        
        m.getLendAndReturns().add(lAR);
        lAR.setMember(m);
        
        b.getLendAndReturns().add(lAR);
        lAR.setBook(b);
       
        em.flush();
    }
}
