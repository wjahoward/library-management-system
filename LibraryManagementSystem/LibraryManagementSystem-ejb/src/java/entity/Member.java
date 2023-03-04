/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author wjahoward
 */
@Entity
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    @NotNull
    @NotBlank(message = "First name is required.")
    private String firstName;

    @Column(nullable = false)
    @NotNull
    @NotBlank(message = "Last name is required.")
    private String lastName;

    @Column(nullable = false)
    @NotNull(message = "Gender is required.")
    private Character gender;

    @Column(nullable = false)
    @NotNull(message = "Age is required.")
    @Min(value = 4, message = "Age has to be minimally 4 years old.")
    @Max(value = 120, message = "Age cannot exceed 120 years old.")
    private Integer age;

    @Column(nullable = false, length = 9)
    @NotNull
    @Pattern(regexp = "[stST][0-9]{7,}[a-zA-Z]{1}", message="Invalid NRIC format. Please enter a valid NRIC in the format S1234567A.")
    @NotBlank(message = "NRIC is required.")
    private String identityNo;

    @Column(nullable = false)
    @NotNull
    @Pattern(regexp = "[89]\\d{7}", message="Invalid mobile phone format. Please enter a valid mobile phone in the format 91234567.")
    @NotBlank(message = "Mobile Phone is required.")
    private String phone;

    @Column(nullable = false)
    @NotNull
    @NotBlank(message = "Addres is required.")
    private String address;

    @OneToMany(mappedBy = "member")
    private List<LendAndReturn> lendAndReturns;

    public Member() {
        this.lendAndReturns = new ArrayList<>();
    }

    public Member(String firstName, String lastName, Character gender, Integer age, String identityNo, String phone, String address) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.identityNo = identityNo;
        this.phone = phone;
        this.address = address;
    }

    public List<LendAndReturn> getLendAndReturns() {
        return lendAndReturns;
    }

    public void setLendAndReturns(List<LendAndReturn> lendAndReturns) {
        this.lendAndReturns = lendAndReturns;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memberId != null ? memberId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the memberId fields are not set
        if (!(object instanceof Member)) {
            return false;
        }
        Member other = (Member) object;
        if ((this.memberId == null && other.memberId != null) || (this.memberId != null && !this.memberId.equals(other.memberId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Member[ id=" + memberId + " ]";
    }

}
