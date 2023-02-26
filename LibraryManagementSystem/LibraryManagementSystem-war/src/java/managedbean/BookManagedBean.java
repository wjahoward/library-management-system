/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import ejb.session.stateless.BookSessionBeanLocal;
import entity.Book;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author wjahoward
 */
@Named(value = "bookManagedBean")
@RequestScoped
public class BookManagedBean {

    @EJB
    private BookSessionBeanLocal bookSessionBeanLocal;

    private String title;
    private String isbn;
    private String author;

    private List<Book> books;

    private long bId;
    private Book selectedBook;

    /**
     * Creates a new instance of BookManagedBean
     */
    public BookManagedBean() {
    }

    @PostConstruct
    public void init() {
        setBooks(bookSessionBeanLocal.searchBooks());
    }

    public void loadSelectedBook() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            this.selectedBook
                    = bookSessionBeanLocal.getBook(bId);
            title = this.selectedBook.getTitle();
            isbn = this.selectedBook.getIsbn();
            author = this.selectedBook.getAuthor();
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load book")); 
        } 
    } 
    
    public void updatedSelectedBook() {
        setSelectedBook(selectedBook);
    }

    public BookSessionBeanLocal getBookSessionBeanLocal() {
        return bookSessionBeanLocal;
    }

    public void setBookSessionBeanLocal(BookSessionBeanLocal bookSessionBeanLocal) {
        this.bookSessionBeanLocal = bookSessionBeanLocal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public long getbId() {
        return bId;
    }

    public void setbId(long bId) {
        this.bId = bId;
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }

}
