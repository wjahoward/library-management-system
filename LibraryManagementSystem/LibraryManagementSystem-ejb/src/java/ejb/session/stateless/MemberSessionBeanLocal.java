/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Member;
import exception.EntityManagerException;
import exception.MemberNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wjahoward
 */
@Local
public interface MemberSessionBeanLocal {
    public void createNewMember(Member newMember) throws EntityManagerException;
    
    public List<Member> searchMembers() throws EntityManagerException;
    
    public Member getMember(Long mId) throws EntityManagerException, MemberNotFoundException;
   
}
