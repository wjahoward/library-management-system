/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import ejb.session.stateless.BookSessionBeanLocal;
import ejb.session.stateless.LendAndReturnSessionBeanLocal;
import entity.Book;
import exception.BookNotFoundException;
import exception.EntityManagerException;
import exception.LendingNotFoundException;
import java.util.ArrayList;
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

    @EJB
    private LendAndReturnSessionBeanLocal lendAndReturnSessionBeanLocal;

    private String title;
    private String isbn;
    private String author;

    private List<Book> books;

    private long bId;
    private Book selectedBook;

    private String filter = "All";

    /**
     * Creates a new instance of BookManagedBean
     */
    public BookManagedBean() {
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            setBooks(bookSessionBeanLocal.searchBooks());
        } catch (EntityManagerException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Entity Manager Error"));
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

    public String getImage(String titleOfBook) {
        switch (titleOfBook) {
            case "Anna Karenina":
                return "anna-karenina.jpeg";
            case "Madame Bovary":
                return "madame-bovary.jpeg";
            case "Hamlet":
                return "hamlet.jpeg";
            case "The Hobbit":
                return "the-hobbit.jpeg";
            case "Great Expectations":
                return "great-expectations.jpeg";
            case "Pride and Prejudice":
                return "pride-and-prejudice.jpeg";
            case "Wuthering Heights":
                return "wuthering-heights.jpeg";
        }

        return "";
    }
       
    public void loadSelectedBook() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            this.selectedBook
                    = bookSessionBeanLocal.getBook(bId);
            title = this.selectedBook.getTitle();
            isbn = this.selectedBook.getIsbn();
            author = this.selectedBook.getAuthor();
        } catch (EntityManagerException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Entity Manager Error"));
        } catch (BookNotFoundException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Book record not found"));
        } 
    }

    public List<Book> getFilteredBooks() {
        List<Book> filteredBooks = new ArrayList<>();
        FacesContext context = FacesContext.getCurrentInstance();
        
        switch (filter) {
            case "Unavailable":
                for (Book book : books) {
                    try {
                        if (lendAndReturnSessionBeanLocal.checkIfLend(book.getBookId())) {
                            filteredBooks.add(book);
                        }
                    } catch (EntityManagerException ex) {
                         context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Entity Manager Error"));
                    } catch (LendingNotFoundException ex) {
                         context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Lending record not found"));
                    }
                }
                
                return filteredBooks;
            case "Available":
                for (Book book : books) {
                    try {
                        if (lendAndReturnSessionBeanLocal.checkIfLend(book.getBookId())) {
                            filteredBooks.add(book);
                        }
                    } catch (EntityManagerException ex) {
                        // Handle the exception here
                         context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Entity Manager Error"));
                    } catch (LendingNotFoundException ex) {
                         context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Lending record not found"));
                    }
                }
                
               return filteredBooks;
            default:
                return books; // show all books
        }
    }

}
