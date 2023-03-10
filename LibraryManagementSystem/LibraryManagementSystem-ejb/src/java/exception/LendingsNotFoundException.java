/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author wjahoward
 */
public class LendingsNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>LendingsNotFoundException</code> without
     * detail message.
     */
    public LendingsNotFoundException() {
    }

    /**
     * Constructs an instance of <code>LendingsNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LendingsNotFoundException(String msg) {
        super(msg);
    }
}
