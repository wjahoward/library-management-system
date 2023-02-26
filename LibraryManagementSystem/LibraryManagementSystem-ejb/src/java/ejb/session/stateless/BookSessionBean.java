/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Book;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    
    @Override
    public List<Book> searchBooks() {
        Query query = em.createQuery("SELECT b FROM Book b");
        return query.getResultList();
    }
    
    @Override
    public Book getBook(long bId) {
        Query query = em.createQuery("SELECT b FROM Book b WHERE b.bookId = :inBId");
        query.setParameter("inBId", bId);
        return (Book) query.getSingleResult();
    }
}
