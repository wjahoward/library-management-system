/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import ejb.session.stateless.StaffSessionBeanLocal;
import entity.Staff;
import exception.StaffNotFoundException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author wjahoward
 */
@Named(value = "staffManagedBean")
@SessionScoped
public class StaffManagedBean implements Serializable {

    /**
     * Creates a new instance of StaffManagedBean
     */
    @EJB
    StaffSessionBeanLocal staffSessionBeanLocal;

    private String username;
    private String password;
    private Long staffId;

    private static final String INVALID_CREDENTIALS = "Invalid credentials";
    private static final String LOGIN_ERROR = "Log in Error";

    public StaffManagedBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String login() {
        FacesMessage message = null;

        try {
            Staff s = staffSessionBeanLocal.checkStaff(username, password);

            if (s != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", username);
                staffId = s.getStaffId();

                return "/secret/home.xhtml?faces-redirect=true";
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, LOGIN_ERROR, INVALID_CREDENTIALS);
            }
        } catch (StaffNotFoundException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, LOGIN_ERROR, INVALID_CREDENTIALS);
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
        return "/login.xhtml";
    }

    public String logout() {
        username = null;
        password = null;
        staffId = -1L;

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

}
