/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Member;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wjahoward
 */
@Stateless
public class MemberSessionBean implements MemberSessionBeanLocal {

    @PersistenceContext(unitName = "LibraryManagementSystem-ejbPU")
    private EntityManager em;

    public MemberSessionBean() {
    }

    @Override
    public void createNewMember(Member newMember) {
        em.persist(newMember);
        em.flush();
    }

    @Override
    public List<Member> searchMembers() {
        Query query = em.createQuery("SELECT m FROM Member m");
        return query.getResultList();
    }

    @Override
    public Member find(Long mId) {
        Member m = em.find(Member.class, mId);
        return m;
    }

}
