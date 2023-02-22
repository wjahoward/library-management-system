/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Member;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wjahoward
 */
@Stateless
public class MemberSessionBean implements MemberSessionBeanLocal {

    @PersistenceContext(unitName = "LibraryManagementSystem-ejbPU")
    private EntityManager em;
    
    @Override
    public void createNewMember(Member m) {
        em.persist(m);
    }

}
