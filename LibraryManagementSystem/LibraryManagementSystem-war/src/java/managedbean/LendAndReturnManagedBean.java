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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author wjahoward
 */
@Named(value = "lendAndReturnManagedBean")
@RequestScoped
public class LendAndReturnManagedBean {

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
    private String status;

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

    public void memberChanged(ValueChangeEvent event) {
        member = (Member) event.getNewValue();
        newMember = (Member) event.getNewValue();
    }

    public String getFormattedLendDate() {
        return dateFormat.format(lendDate);
    }

    public String getFormattedLendTime() {
        return timeFormat.format(lendDate);
    }

    public String getFormattedLendMaxDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lendDate);
        calendar.add(Calendar.DAY_OF_YEAR, 14);
        return dateFormat.format(calendar.getTime());
    }

    public BigDecimal getFormattedFineAmount() {
        return fineAmount.setScale(2, RoundingMode.HALF_UP);
    }

    public void addLendAndReturn() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Long bId = Long.parseLong(externalContext.getRequestParameterMap().get("bId"));

        LendAndReturn lAR = new LendAndReturn();
        lAR.setLendDate(new Date());
        lAR.setReturnDate(null);
        lAR.setFineAmount(new BigDecimal(0));
        
        Set<ConstraintViolation<LendAndReturn>> constraintViolations = validator.validate(lAR);

        if (!constraintViolations.isEmpty()) {
            displayValidationErrors(constraintViolations);
            return;
        }

        lendAndReturnSessionBeanLocal.createLendAndReturn(lAR, bId, newMember.getMemberId());
    }
    
    private void displayValidationErrors(Set<ConstraintViolation<LendAndReturn>> constraintViolations) {
        FacesContext context = FacesContext.getCurrentInstance();
        for (ConstraintViolation<LendAndReturn> violation : constraintViolations) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Backend Validation Error", violation.getMessage()));
        }
    }

    public void returnBook() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Long bId = Long.parseLong(externalContext.getRequestParameterMap().get("bId"));

        lendAndReturnSessionBeanLocal.returnBook(bId);

        setSelectedMember(null);
    }

    public void updatedSelectedBook() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Long bookId = Long.parseLong(externalContext.getRequestParameterMap().get("bId"));
        setbId(bookId);
    }

    public String getStatus(String boId) {
        Long bookId = Long.parseLong(boId);
        if (lendAndReturnSessionBeanLocal.checkIfLend(bookId)) {
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
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load lendAndReturn"));
        }
    }
}
