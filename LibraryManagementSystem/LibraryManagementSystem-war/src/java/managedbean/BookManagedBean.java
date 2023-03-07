/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import ejb.session.stateless.BookSessionBeanLocal;
import ejb.session.stateless.LendAndReturnSessionBeanLocal;
import entity.Book;
import java.util.List;
import java.util.stream.Collectors;
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

    @EJB
    private LendAndReturnSessionBeanLocal lendAndReturnSessionBeanLocal;
    
    private String title;
    private String isbn;
    private String author;

    private List<Book> books;

    private long bId;
    private Book selectedBook;

    private String filter = "All";
    
    private String image;

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

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
    
    public String getImage(String imagePath) {
        imagePath += "/resources/images/";
        
        switch (title) {
            case "Anna Karenina":
                return imagePath + "anna-karenina.jpeg";
            case "Madame Bovary":
                return imagePath + "madame-bovary.jpeg";
            case "Hamlet":
                return imagePath + "hamlet.jpeg";
            case "The Hobbit":
                return imagePath + "the-hobbit.jpeg";
            case "Great Expectations":
                return imagePath + "great-expectations.jpeg";
            case "Pride and Prejudice":
                return imagePath + "pride-and-prejudice.jpeg";
            case "Wuthering Heights":
                return imagePath + "wuthering-heights.jpeg";
        }
        
        return "";
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public List<Book> getFilteredBooks() {
        switch (filter) {
            case "Unavailable":
                return books.stream().filter(b -> lendAndReturnSessionBeanLocal.checkIfLend(b.getBookId())).collect(Collectors.toList());
            case "Available":
                return books.stream().filter(b -> !lendAndReturnSessionBeanLocal.checkIfLend(b.getBookId())).collect(Collectors.toList());
            default:
                return books; // show all books
        }
    }

}
