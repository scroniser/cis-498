/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author scroniser
 */
@Embeddable
public class EnrollmentPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "DEPARTMENT")
    private String department;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COURSENUMBER")
    private long coursenumber;

    public EnrollmentPK() {
    }

    public EnrollmentPK(String department, long coursenumber) {
        this.department = department;
        this.coursenumber = coursenumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public long getCoursenumber() {
        return coursenumber;
    }

    public void setCoursenumber(long coursenumber) {
        this.coursenumber = coursenumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (department != null ? department.hashCode() : 0);
        hash += (int) coursenumber;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EnrollmentPK)) {
            return false;
        }
        EnrollmentPK other = (EnrollmentPK) object;
        if ((this.department == null && other.department != null) || (this.department != null && !this.department.equals(other.department))) {
            return false;
        }
        if (this.coursenumber != other.coursenumber) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.EnrollmentPK[ department=" + department + ", coursenumber=" + coursenumber + " ]";
    }
    
}
