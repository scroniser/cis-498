/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Time;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author scroniser
 */
@Entity
@Table(name = "SECTIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sections.findAll", query = "SELECT s FROM Sections s"),
    @NamedQuery(name = "Sections.findByCallnumber", query = "SELECT s FROM Sections s WHERE s.callnumber = :callnumber"),
    @NamedQuery(name = "Sections.findByDepartment", query = "SELECT s FROM Sections s WHERE s.department = :department"),
    @NamedQuery(name = "Sections.findByCoursenumber", query = "SELECT s FROM Sections s WHERE s.coursenumber = :coursenumber"),
    @NamedQuery(name = "Sections.findByDays", query = "SELECT s FROM Sections s WHERE s.days = :days"),
    @NamedQuery(name = "Sections.findByStarttime", query = "SELECT s FROM Sections s WHERE s.starttime = :starttime"),
    @NamedQuery(name = "Sections.findByEndtime", query = "SELECT s FROM Sections s WHERE s.endtime = :endtime"),
    @NamedQuery(name = "Sections.findByMediarequired", query = "SELECT s FROM Sections s WHERE s.mediarequired = :mediarequired")})
public class Sections implements Serializable {
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sections")
    private Schedule schedule;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sections")
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CALLNUMBER")
    private Long callnumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "DEPARTMENT")
    private String department;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COURSENUMBER")
    private long coursenumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "DAYS")
    private String days;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STARTTIME")
    @Temporal(TemporalType.TIME)
    private Time starttime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENDTIME")
    @Temporal(TemporalType.TIME)
    private Time endtime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "MEDIAREQUIRED")
    private String mediarequired;

    public Sections() {
    }

    public Sections(Long callnumber) {
        this.callnumber = callnumber;
    }

    public Sections(Long callnumber, String department, long coursenumber, String days, Time starttime, Time endtime, String mediarequired) {
        this.callnumber = callnumber;
        this.department = department;
        this.coursenumber = coursenumber;
        this.days = days;
        this.starttime = starttime;
        this.endtime = endtime;
        this.mediarequired = mediarequired;
    }

    public Long getCallnumber() {
        return callnumber;
    }

    public void setCallnumber(Long callnumber) {
        this.callnumber = callnumber;
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

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Time getStarttime() {
        return starttime;
    }

    public void setStarttime(Time starttime) {
        this.starttime = starttime;
    }

    public Time getEndtime() {
        return endtime;
    }

    public void setEndtime(Time endtime) {
        this.endtime = endtime;
    }

    public String getMediarequired() {
        return mediarequired;
    }

    public void setMediarequired(String mediarequired) {
        this.mediarequired = mediarequired;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (callnumber != null ? callnumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sections)) {
            return false;
        }
        Sections other = (Sections) object;
        if ((this.callnumber == null && other.callnumber != null) || (this.callnumber != null && !this.callnumber.equals(other.callnumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Sections[ callnumber=" + callnumber + " ]";
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

  
}
