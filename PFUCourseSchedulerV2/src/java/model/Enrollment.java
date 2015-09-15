/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author scroniser
 */
@Entity
@Table(name = "ENROLLMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Enrollment.findAll", query = "SELECT e FROM Enrollment e"),
    @NamedQuery(name = "Enrollment.findByDepartment", query = "SELECT e FROM Enrollment e WHERE e.enrollmentPK.department = :department"),
    @NamedQuery(name = "Enrollment.findByCoursenumber", query = "SELECT e FROM Enrollment e WHERE e.enrollmentPK.coursenumber = :coursenumber"),
    @NamedQuery(name = "Enrollment.findByNumberenrolled", query = "SELECT e FROM Enrollment e WHERE e.numberenrolled = :numberenrolled")})
public class Enrollment implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EnrollmentPK enrollmentPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMBERENROLLED")
    private short numberenrolled;

    public Enrollment() {
    }

    public Enrollment(EnrollmentPK enrollmentPK) {
        this.enrollmentPK = enrollmentPK;
    }

    public Enrollment(EnrollmentPK enrollmentPK, short numberenrolled) {
        this.enrollmentPK = enrollmentPK;
        this.numberenrolled = numberenrolled;
    }

    public Enrollment(String department, long coursenumber) {
        this.enrollmentPK = new EnrollmentPK(department, coursenumber);
    }

    public EnrollmentPK getEnrollmentPK() {
        return enrollmentPK;
    }

    public void setEnrollmentPK(EnrollmentPK enrollmentPK) {
        this.enrollmentPK = enrollmentPK;
    }

    public short getNumberenrolled() {
        return numberenrolled;
    }

    public void setNumberenrolled(short numberenrolled) {
        this.numberenrolled = numberenrolled;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (enrollmentPK != null ? enrollmentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Enrollment)) {
            return false;
        }
        Enrollment other = (Enrollment) object;
        if ((this.enrollmentPK == null && other.enrollmentPK != null) || (this.enrollmentPK != null && !this.enrollmentPK.equals(other.enrollmentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Enrollment[ enrollmentPK=" + enrollmentPK + " ]";
    }
    
}
