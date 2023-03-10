/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import ejb.session.stateless.LendAndReturnSessionBeanLocal;
import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import exception.BookNotFoundException;
import exception.EntityManagerException;
import exception.LendingNotFoundException;
import exception.LendingsNotFoundException;
import exception.MemberNotFoundException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author wjahoward
 */
@Named(value = "lendAndReturnManagedBean")
@ViewScoped
public class LendAndReturnManagedBean implements Serializable {

    @EJB
    private LendAndReturnSessionBeanLocal lendAndReturnSessionBeanLocal;

    private Long bId;
    private Date lendDate;
    private Date returnDate;
    private BigDecimal fineAmount;
    private Member member;

    private Book selectedBook;
    private Member selectedMember = null; // default
    private Member newMember = null;

    private LendAndReturn selectedLendAndReturn;
    private List<LendAndReturn> selectedLendAndReturns;
    private String status;
    private BigDecimal totalFine;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    // validation
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    /**
     * Creates a new instance of LendAndReturnManagedBean
     */
    public LendAndReturnManagedBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public LendAndReturnSessionBeanLocal getLendAndReturnSessionBeanLocal() {
        return lendAndReturnSessionBeanLocal;
    }

    public void setLendAndReturnSessionBeanLocal(LendAndReturnSessionBeanLocal lendAndReturnSessionBeanLocal) {
        this.lendAndReturnSessionBeanLocal = lendAndReturnSessionBeanLocal;
    }

    public Date getLendDate() {
        return lendDate;
    }

    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Long getbId() {
        return bId;
    }

    public void setbId(Long bId) {
        this.bId = bId;
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }

    public Member getSelectedMember() {
        return selectedMember;
    }

    public void setSelectedMember(Member selectedMember) {
        this.selectedMember = selectedMember;
    }

    public LendAndReturn getSelectedLendAndReturn() {
        return selectedLendAndReturn;
    }

    public void setSelectedLendAndReturn(LendAndReturn selectedLendAndReturn) {
        this.selectedLendAndReturn = selectedLendAndReturn;
    }

    public List<LendAndReturn> getSelectedLendAndReturns() {
        return selectedLendAndReturns;
    }

    public void setSelectedLendAndReturns(List<LendAndReturn> selectedLendAndReturns) {
        this.selectedLendAndReturns = selectedLendAndReturns;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getNewMember() {
        return newMember;
    }

    public void setNewMember(Member newMember) {
        this.newMember = newMember;
    }

    public BigDecimal getTotalFine() {
        return this.totalFine;
    }

    public void setTotalFine(BigDecimal totalFine) {
        this.totalFine = totalFine;
    }

    public void memberChanged(ValueChangeEvent event) {
        member = (Member) event.getNewValue();
        newMember = (Member) event.getNewValue();
    }

    public String getFormattedLendDate() {
        return dateFormat.format(lendDate);
    }

    public String setFormattedLendDate(Date lendDate) {
        return dateFormat.format(lendDate);
    }

    public String getFormattedLendTime() {
        return timeFormat.format(lendDate);
    }

    public String setFormattedLendTime(Date lendDate) {
        return timeFormat.format(lendDate);
    }

    public String getFormattedLendMaxDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lendDate);
        calendar.add(Calendar.DAY_OF_YEAR, 14);
        return dateFormat.format(calendar.getTime());
    }

    public String setFormattedLendMaxDate(Date lendDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lendDate);
        calendar.add(Calendar.DAY_OF_YEAR, 14);
        return dateFormat.format(calendar.getTime());
    }

    public BigDecimal getFormattedFineAmount() {
        return fineAmount.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal setFormattedFineAmount(BigDecimal fineAmount) {
        return fineAmount.setScale(2, RoundingMode.HALF_UP);
    }

    public String viewMember() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Long mId = Long.parseLong(externalContext.getRequestParameterMap().get("mId"));
        return "/secret/admin/member/viewMember.xhtml?faces-redirect=true&mId=" + mId;
    }

    public boolean checkBookings() {
        return selectedLendAndReturns == null;
    }

    public String getGender() {
        return selectedMember.getGender() == 'M' ? "Male" : "Female";
    }

    public String showFine(BigDecimal fineAmount) {
        return "Pay a Fine of $" + fineAmount.setScale(2, RoundingMode.HALF_UP);
    }

    public String showTotalFine(BigDecimal fineAmount) {
        return "Pay a Total Fine of $" + fineAmount.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalFine(List<LendAndReturn> lendAndReturns) {
        BigDecimal currentTotalFine = new BigDecimal(0);

        if (lendAndReturns.isEmpty()) {
            return new BigDecimal(0);
        }

        for (LendAndReturn lAR : lendAndReturns) {
            currentTotalFine = currentTotalFine.add(lAR.getFineAmount());
        }

        this.totalFine = currentTotalFine;

        return currentTotalFine;
    }

    public void addLendAndReturn() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            ExternalContext externalContext = context.getExternalContext();
            Long bId = Long.parseLong(externalContext.getRequestParameterMap().get("bId"));

            LendAndReturn lAR = new LendAndReturn();
            // testing purpose
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR, 2023);
//        cal.set(Calendar.MONTH, Calendar.MARCH);
//        cal.set(Calendar.DAY_OF_MONTH, 20);
//        Date ld = cal.getTime();
//        lAR.setLendDate(ld);
            lAR.setLendDate(new Date());
            lAR.setReturnDate(null);
            lAR.setFineAmount(new BigDecimal(0));

            Set<ConstraintViolation<LendAndReturn>> constraintViolations = validator.validate(lAR);

            if (!constraintViolations.isEmpty()) {
                displayValidationErrors(constraintViolations);
                return;
            }

            lendAndReturnSessionBeanLocal.createLendAndReturn(lAR, bId, newMember.getMemberId());
        } catch (EntityManagerException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Entity Manager Error"));
        } catch (MemberNotFoundException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Member record not found"));
        } catch (BookNotFoundException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Book record not found"));
        }

    }

    private void displayValidationErrors(Set<ConstraintViolation<LendAndReturn>> constraintViolations) {
        FacesContext context = FacesContext.getCurrentInstance();
        for (ConstraintViolation<LendAndReturn> violation : constraintViolations) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Backend Validation Error", violation.getMessage()));
        }
    }

    public void returnBook() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            ExternalContext externalContext = context.getExternalContext();
            Long bId = Long.parseLong(externalContext.getRequestParameterMap().get("bId"));
            
            lendAndReturnSessionBeanLocal.returnBook(bId);
        } catch (EntityManagerException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Entity manager error"));
        } catch (LendingNotFoundException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Lending record not found"));
        }
    }

    public void returnAllBooks() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            ExternalContext externalContext = context.getExternalContext();
            Long mId = Long.parseLong(externalContext.getRequestParameterMap().get("mId"));

            lendAndReturnSessionBeanLocal.returnAllBooks(mId);
        } catch (EntityManagerException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Entity manager error"));
        } catch (LendingsNotFoundException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Lending records not found"));
        }

    }

    public void updatedSelectedBook() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Long bookId = Long.parseLong(externalContext.getRequestParameterMap().get("bId"));
        setbId(bookId);
    }

    public String getStatus(String boId) {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean checkIfLend = false;
        try {
            Long bookId = Long.parseLong(boId);
            checkIfLend = lendAndReturnSessionBeanLocal.checkIfLend(bookId);
        } catch (EntityManagerException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Entity manager error"));
        } catch (LendingNotFoundException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Lend record not found"));
        }

        if (checkIfLend) {
            return "Unavailable";
        }

        return "Available";
    }

    public void loadSelectedLendAndReturn() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            ExternalContext externalContext = context.getExternalContext();
            Long bId = Long.parseLong(externalContext.getRequestParameterMap().get("bId"));

            this.selectedLendAndReturn = lendAndReturnSessionBeanLocal.getLendAndReturn(bId);

            if (this.selectedLendAndReturn == null) {
                return;
            }

            lendDate = this.selectedLendAndReturn.getLendDate();
            fineAmount = this.selectedLendAndReturn.getFineAmount();
            selectedMember = this.selectedLendAndReturn.getMember();
            status = "Unavailable";
        } catch (EntityManagerException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Entity manager error"));
        } catch (LendingNotFoundException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Lend record not found"));
        }
    }

    public void loadSelectedLendAndReturns() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            ExternalContext externalContext = context.getExternalContext();
            Long mId = Long.parseLong(externalContext.getRequestParameterMap().get("mId"));

            this.selectedLendAndReturns = lendAndReturnSessionBeanLocal.getLendAndReturns(mId);
        } catch (EntityManagerException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Entity manager error"));
        } catch (LendingsNotFoundException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Lend records not found"));
        }
    }
}
