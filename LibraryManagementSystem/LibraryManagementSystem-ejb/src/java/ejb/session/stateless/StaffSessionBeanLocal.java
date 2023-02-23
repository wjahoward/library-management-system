/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Staff;
import exception.StaffNotFoundException;
import javax.ejb.Local;

/**
 *
 * @author wjahoward
 */
@Local
public interface StaffSessionBeanLocal {
    public void createNewStaff(Staff s);
    
    public Staff checkStaff(String username, String password) throws StaffNotFoundException;
}
