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
    private int staffId;

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

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String login() throws StaffNotFoundException {
        Staff s = staffSessionBeanLocal.checkStaff(username, password);

        if (s != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", username);
            return "/secret/home.xhtml?faces-redirect=true";
        }

        username = null;
        password = null;
        staffId = -1;
        return "/login.xhtml";
    }

    public String logout() {
        username = null;
        password = null;
        staffId = -1;

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

}
