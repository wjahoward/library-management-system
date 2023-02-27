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
            return "/secret/index.xhtml?faces-redirect=true";
        }

        username = null;
        password = null;
        staffId = -1;
        return "/main/login.xhtml";
    }

    public String logout() {
        username = null;
        password = null;
        staffId = -1;

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/main/login.xhtml?faces-redirect=true";
    }

}
// <h:body style="padding-top: 60px; background-color: #dddddd;">
//
//            <b:container style="margin-top: 15em;">
//                <h:form styleClass="form-signin" prependId="false">
//                    <h2 class="form-signin-heading" style="text-align:center">Library Management System</h2>
//                    <b:inputText id="email" placeholder="Username" value="#{staffManagedBean.username}" required="true">
//                        <f:facet name="prepend">
//                            <b:icon name="user" />
//                        </f:facet>
//                    </b:inputText>
//                    <b:inputText id="password" placeholder="Password" type="password" value="#{staffManagedBean.password}" required="true">
//                        <f:facet name="prepend">
//                            <b:iconAwesome name="key" />
//                        </f:facet>
//                    </b:inputText>
//                    <b:commandButton look="primary btn-block" value="Sign In" icon="log-in" size="lg" action="#{staffManagedBean.login}"/>
//                </h:form>
//            </b:container>
//        </h:body>