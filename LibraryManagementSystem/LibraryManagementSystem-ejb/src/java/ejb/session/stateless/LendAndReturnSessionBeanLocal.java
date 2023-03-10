/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.LendAndReturn;
import exception.BookNotFoundException;
import exception.EntityManagerException;
import exception.LendingNotFoundException;
import exception.LendingsNotFoundException;
import exception.MemberNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wjahoward
 */
@Local
public interface LendAndReturnSessionBeanLocal {
    public void createLendAndReturn(LendAndReturn lAR, Long bId, Long mId) throws EntityManagerException, MemberNotFoundException, BookNotFoundException;
    
    public boolean checkIfLend(Long bookId) throws EntityManagerException, LendingNotFoundException;
    
    public LendAndReturn getLendAndReturn(Long bookId) throws LendingNotFoundException, EntityManagerException;
    
    public List<LendAndReturn> getLendAndReturns(Long memberId) throws EntityManagerException, LendingsNotFoundException;
    
    public void returnBook(Long bookId) throws EntityManagerException, LendingNotFoundException;
    
    public void returnAllBooks(Long memberId) throws EntityManagerException, LendingsNotFoundException;
}
