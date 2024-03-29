/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import exception.BookNotFoundException;
import exception.EntityManagerException;
import exception.LendingNotFoundException;
import exception.LendingsNotFoundException;
import exception.MemberNotFoundException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wjahoward
 */
@Stateless
public class LendAndReturnSessionBean implements LendAndReturnSessionBeanLocal {

    @PersistenceContext(unitName = "LibraryManagementSystem-ejbPU")
    private EntityManager em;

    private final int MAXIMUM_DAYS = 14;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void createLendAndReturn(LendAndReturn lAR, Long bId, Long mId) throws EntityManagerException, MemberNotFoundException, BookNotFoundException {
        Member m = em.find(Member.class, mId);

        Book b = em.find(Book.class, bId);

        em.persist(lAR);

        m.getLendAndReturns().add(lAR);
        lAR.setMember(m);

        b.getLendAndReturns().add(lAR);
        lAR.setBook(b);

        em.flush();
    }

    @Override
    public boolean checkIfLend(Long bookId) throws EntityManagerException, LendingNotFoundException {
        Query query = em.createQuery("SELECT lAR FROM LendAndReturn lAR WHERE lAR.book.bookId = :inBId AND lAR.returnDate IS NULL ORDER BY lAR.lendDate DESC");
        query.setParameter("inBId", bookId);

        if (query.getResultList().isEmpty()) {
            return false;
        }

        LendAndReturn lAR = (LendAndReturn) query.getResultList().get(0);

        return lAR.getReturnDate() == null;
    }

    @Override
    public LendAndReturn getLendAndReturn(Long bookId) throws EntityManagerException, LendingNotFoundException {
        Query query = em.createQuery("SELECT lAR FROM LendAndReturn lAR WHERE lAR.book.bookId = :inBId ORDER BY lAR.lendDate DESC");
        query.setParameter("inBId", bookId);

        if (query.getResultList().isEmpty()) {
            return null;
        }

        LendAndReturn lAR = (LendAndReturn) query.getResultList().get(0);

        if (lAR.getReturnDate() == null) {
            withinFourteenDays(lAR);
        }

        return lAR;
    }

    @Override
    public List<LendAndReturn> getLendAndReturns(Long memberId) throws EntityManagerException, LendingsNotFoundException {
        Query query = em.createQuery("SELECT lAR FROM LendAndReturn lAR WHERE lAR.member.memberId = :inMId AND lAR.returnDate IS NULL");
        query.setParameter("inMId", memberId);

        if (query.getResultList().isEmpty()) {
            return null;
        }

        List<LendAndReturn> lAR = (List<LendAndReturn>) query.getResultList();

        for (int i = 0; i < lAR.size(); i++) {
            if (lAR.get(i).getReturnDate() == null) {
                withinFourteenDays(lAR.get(i));
            }
        }

        return lAR;
    }

    private void withinFourteenDays(LendAndReturn lAR) throws EntityManagerException {
        Date lendDate = lAR.getLendDate();

        // testing purpose
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR, 2023);
//        cal.set(Calendar.MONTH, Calendar.APRIL);
//        cal.set(Calendar.DAY_OF_MONTH, 1);
//        Date currentDate = cal.getTime();
//        long diffInMillies = Math.abs(currentDate.getTime() - lendDate.getTime());
//        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        
        Date currentDate = new Date();
        long diffInMilliseconds = currentDate.getTime() - lendDate.getTime();
        long diffInDays = diffInMilliseconds / (24 * 60 * 60 * 1000);
        if (diffInDays > MAXIMUM_DAYS) {
            BigDecimal fineAmount = new BigDecimal((diffInDays - 14) * 0.5);
            lAR.setFineAmount(fineAmount);
        } else {
            lAR.setFineAmount(new BigDecimal(0));
        }
    }

    @Override
    public void returnBook(Long bookId) throws EntityManagerException, LendingNotFoundException {
        Query query = em.createQuery("SELECT lAR FROM LendAndReturn lAR WHERE lAR.book.bookId = :inBId ORDER BY lAR.lendDate DESC");
        query.setParameter("inBId", bookId);

        LendAndReturn lAR = (LendAndReturn) query.getResultList().get(0);
        lAR.setReturnDate(new Date());

        em.flush();
    }

    @Override
    public void returnAllBooks(Long memberId) throws EntityManagerException, LendingsNotFoundException {
        Query query = em.createQuery("SELECT lAR FROM LendAndReturn lAR WHERE lAR.member.memberId = :inMId AND lAR.returnDate IS NULL");
        query.setParameter("inMId", memberId);

        List<LendAndReturn> lendAndReturns = (List<LendAndReturn>) query.getResultList();

        for (LendAndReturn lAR : lendAndReturns) {
            lAR.setReturnDate(new Date());
        }

        em.flush();
    }
}
