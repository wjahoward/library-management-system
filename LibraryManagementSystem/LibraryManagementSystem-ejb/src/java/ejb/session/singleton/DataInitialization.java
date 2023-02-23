/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.BookSessionBeanLocal;
import ejb.session.stateless.MemberSessionBeanLocal;
import ejb.session.stateless.StaffSessionBeanLocal;
import entity.Book;
import entity.Member;
import entity.Staff;
import exception.EntityManagerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wjahoward
 */
@Singleton
@LocalBean
@Startup
public class DataInitialization {

    @EJB
    private BookSessionBeanLocal bookSessionBean;

    @EJB
    private MemberSessionBeanLocal memberSessionBean;
    
    @EJB
    private StaffSessionBeanLocal staffSessionBean;
    
    @PersistenceContext(unitName = "LibraryManagementSystem-ejbPU")
    private EntityManager em;
    
    public DataInitialization() {
        
    }
    
    @PostConstruct
    public void postConstruct() {
        try {      
            mainFunction();
        } catch (EntityManagerException ex) {
            Logger.getLogger(DataInitialization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void mainFunction() throws EntityManagerException {
        if (em.find(Member.class, 1L) == null) {
            initializeData();
        } 
    }
    
    private void initializeData() {
        Staff s1 = new Staff("Eric", "Some", "eric", "password");
        Staff s2 = new Staff("Sarah", "Brightman", "sarah", "password");
        
        staffSessionBean.createNewStaff(s1);
        staffSessionBean.createNewStaff(s2);
        
        Book b1 = new Book("Anna Karenina", "0451528611", "Leo Tolstoy");
        Book b2 = new Book("Madame Bovary", "979-8649042031", "Gustave Flaubert");
        Book b3 = new Book("Hamlet", "1980625026", "William Shakespeare");
        Book b4 = new Book("The Hobbit", "9780007458424", "J R R Tolkien");
        Book b5 = new Book("Great Expectations", "1521853592", "Charles Dickens");
        Book b6 = new Book("Pride and Prejudice", "979-8653642272", "Jane Austen");
        Book b7 = new Book("Wuthering Heights", "3961300224", "Emily Brontë");
        
        bookSessionBean.createNewBook(b1);
        bookSessionBean.createNewBook(b2);
        bookSessionBean.createNewBook(b3);
        bookSessionBean.createNewBook(b4);
        bookSessionBean.createNewBook(b5);
        bookSessionBean.createNewBook(b6);
        bookSessionBean.createNewBook(b7);
        
        Member m1 = new Member("Tony", "Shade", 'M', 31, "S8900678A", "83722773", "13 Jurong East, Ave 3");
        Member m2 = new Member("Dewi", "Tan", 'F', 35, "S8581028X", "94602711", "15 Computing Dr");
        
        memberSessionBean.createNewMember(m1);
        memberSessionBean.createNewMember(m2);
    }
}
