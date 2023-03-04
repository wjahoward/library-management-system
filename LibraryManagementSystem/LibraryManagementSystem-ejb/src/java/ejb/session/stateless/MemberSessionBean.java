/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Member;
import exception.EntityManagerException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
