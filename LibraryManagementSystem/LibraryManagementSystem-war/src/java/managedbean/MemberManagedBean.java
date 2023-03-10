/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import ejb.session.stateless.MemberSessionBeanLocal;
import entity.Member;
import exception.EntityManagerException;
import exception.MemberNotFoundException;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author wjahoward
 */
@Named(value = "memberManagedBean")
@RequestScoped
public class MemberManagedBean {

    @EJB
    private MemberSessionBeanLocal memberSessionBeanLocal;

    private String firstName;
    private String lastName;
    private Character gender;
    private Integer age;
    private String identityNo;
    private String phone;
    private String address;

    private long mId;
    private List<Member> members;
    private Member selectedMember;

    // validation
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    /**
     * Creates a new instance of MemberManagedBean
     */
    public MemberManagedBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            setMembers(memberSessionBeanLocal.searchMembers());
        } catch (EntityManagerException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Entity manager error"));
        }
    }

    public MemberSessionBeanLocal getMemberSessionBeanLocal() {
        return memberSessionBeanLocal;
    }

    public void setMemberSessionBeanLocal(MemberSessionBeanLocal memberSessionBeanLocal) {
        this.memberSessionBeanLocal = memberSessionBeanLocal;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Member getSelectedMember() {
        return selectedMember;
    }

    public void setSelectedMember(Member selectedMember) {
        this.selectedMember = selectedMember;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public void addMember() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
            Member m = new Member();
            m.setFirstName(firstName);
            m.setLastName(lastName);
            m.setGender(gender);
            m.setAge(age);
            m.setIdentityNo(identityNo);
            m.setPhone(phone);
            m.setAddress(address);

            Set<ConstraintViolation<Member>> constraintViolations = validator.validate(m);

            if (!constraintViolations.isEmpty()) {
                displayValidationErrors(constraintViolations);
                return;
            }

            memberSessionBeanLocal.createNewMember(m);
        } catch (EntityManagerException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Entity manager error"));
        }

    }

    private void displayValidationErrors(Set<ConstraintViolation<Member>> constraintViolations) {
        FacesContext context = FacesContext.getCurrentInstance();
        for (ConstraintViolation<Member> violation : constraintViolations) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Backend Validation Error", violation.getMessage()));
        }
    }

    public void loadSelectedMember() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            this.selectedMember
                    = memberSessionBeanLocal.getMember(mId);
            address = this.selectedMember.getAddress();
            age = this.selectedMember.getAge();
            firstName = this.selectedMember.getFirstName();
            lastName = this.selectedMember.getLastName();
            gender = this.selectedMember.getGender();
            identityNo = this.selectedMember.getIdentityNo();
            phone = this.selectedMember.getPhone();
        } catch (EntityManagerException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Entity manager error"));
        } catch (MemberNotFoundException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Member record not found"));
        }
    }
}
