/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Member;
import javax.ejb.Local;

/**
 *
 * @author wjahoward
 */
@Local
public interface MemberSessionBeanLocal {
    public void createNewMember(Member m);
}
