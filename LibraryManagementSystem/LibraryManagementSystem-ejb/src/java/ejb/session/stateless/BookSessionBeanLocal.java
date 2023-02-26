/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Book;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wjahoward
 */
@Local
public interface BookSessionBeanLocal {
    public void createNewBook(Book b);
    
    public List<Book> searchBooks();
    
    public Book getBook(long bId);
}
