/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.LendAndReturn;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wjahoward
 */
@Local
public interface LendAndReturnSessionBeanLocal {
    public void createLendAndReturn(LendAndReturn lAR, Long bId, Long mId);
    
    public boolean checkIfLend(Long bookId);
    
    public LendAndReturn getLendAndReturn(Long bookId);
    
    public List<LendAndReturn> getLendAndReturns(Long memberId);
    
    public void returnBook(Long bookId);
    
    public void returnAllBooks(Long memberId);
}
