/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import ejb.session.stateless.MemberSessionBeanLocal;
import entity.Member;
import exception.EntityManagerException;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.Converter;
import javax.inject.Inject;

/**
 *
 * @author wjahoward
 */
@Named(value = "memberTestConverter")
@RequestScoped
public class MemberTestConverter implements Converter {

    /**
     * Creates a new instance of MemberTestConverter
     */
    @Inject
    private MemberSessionBeanLocal msb;

    public MemberTestConverter() {
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        if (submittedValue == null || submittedValue.length() == 0 || submittedValue.equals("null")) {
            return null;
        }

        try {
            Long objLong = Long.parseLong(submittedValue);
            List<Member> members = msb.searchMembers();

            for (Member member : members) {
                if (member.getMemberId().equals(objLong)) {
                    return member;
                }
            }
        } catch (EntityManagerException ex) {
            System.out.println("Entity manager error");
        } catch (NumberFormatException e) {
            System.out.println("Number format error");
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object modelValue) {
        if (modelValue == null) {
            return ""; // Never return null here!
        }

        if (modelValue instanceof Member) {
            return String.valueOf(((Member) modelValue).getMemberId());
        } else {
            throw new ConverterException(new FacesMessage(modelValue + " is not a valid Member"));
        }
    }

}
