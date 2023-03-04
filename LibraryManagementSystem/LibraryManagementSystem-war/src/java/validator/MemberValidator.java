/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author wjahoward
 */
@FacesValidator("memberValidator")

public class MemberValidator implements Validator {

    public MemberValidator() {

    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        System.out.println("validate member");
        
        String text = (String)value;

        if (text == null || text.isEmpty() || text.equals("")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "First Name is required.", "First Name is required."));
        }
    }
}
