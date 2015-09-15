/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author scroniser
 */
@Entity
@Table(name = "EVENTLOG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Eventlog.findAll", query = "SELECT e FROM Eventlog e"),
    @NamedQuery(name = "Eventlog.findByLogid", query = "SELECT e FROM Eventlog e WHERE e.logid = :logid"),
    @NamedQuery(name = "Eventlog.findByDatetime", query = "SELECT e FROM Eventlog e WHERE e.datetime = :datetime"),
    @NamedQuery(name = "Eventlog.findByLogitem", query = "SELECT e FROM Eventlog e WHERE e.logitem = :logitem")})
public class Eventlog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOGID")
    private Long logid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "LOGITEM")
    private String logitem;

    public Eventlog() {
    }

    public Eventlog(Long logid) {
        this.logid = logid;
    }

    public Eventlog(Long logid, Date datetime, String logitem) {
        this.logid = logid;
        this.datetime = datetime;
        this.logitem = logitem;
    }

    public Long getLogid() {
        return logid;
    }

    public void setLogid(Long logid) {
        this.logid = logid;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getLogitem() {
        return logitem;
    }

    public void setLogitem(String logitem) {
        this.logitem = logitem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logid != null ? logid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eventlog)) {
            return false;
        }
        Eventlog other = (Eventlog) object;
        if ((this.logid == null && other.logid != null) || (this.logid != null && !this.logid.equals(other.logid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Eventlog[ logid=" + logid + " ]";
    }
    
}
