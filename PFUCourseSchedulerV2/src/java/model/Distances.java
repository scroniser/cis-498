/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author scroniser
 */
@Entity
@Table(name = "DISTANCES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Distances.findAll", query = "SELECT d FROM Distances d"),
    @NamedQuery(name = "Distances.findByCampus", query = "SELECT d FROM Distances d WHERE d.campus = :campus"),
    @NamedQuery(name = "Distances.findByNorthdistance", query = "SELECT d FROM Distances d WHERE d.northdistance = :northdistance"),
    @NamedQuery(name = "Distances.findBySouthdistance", query = "SELECT d FROM Distances d WHERE d.southdistance = :southdistance"),
    @NamedQuery(name = "Distances.findByEastdistance", query = "SELECT d FROM Distances d WHERE d.eastdistance = :eastdistance"),
    @NamedQuery(name = "Distances.findByWestdistance", query = "SELECT d FROM Distances d WHERE d.westdistance = :westdistance")})
public class Distances implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "CAMPUS")
    private String campus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NORTHDISTANCE")
    private double northdistance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SOUTHDISTANCE")
    private double southdistance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EASTDISTANCE")
    private double eastdistance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WESTDISTANCE")
    private double westdistance;

    public Distances() {
    }

    public Distances(String campus) {
        this.campus = campus;
    }

    public Distances(String campus, double northdistance, double southdistance, double eastdistance, double westdistance) {
        this.campus = campus;
        this.northdistance = northdistance;
        this.southdistance = southdistance;
        this.eastdistance = eastdistance;
        this.westdistance = westdistance;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public double getNorthdistance() {
        return northdistance;
    }

    public void setNorthdistance(double northdistance) {
        this.northdistance = northdistance;
    }

    public double getSouthdistance() {
        return southdistance;
    }

    public void setSouthdistance(double southdistance) {
        this.southdistance = southdistance;
    }

    public double getEastdistance() {
        return eastdistance;
    }

    public void setEastdistance(double eastdistance) {
        this.eastdistance = eastdistance;
    }

    public double getWestdistance() {
        return westdistance;
    }

    public void setWestdistance(double westdistance) {
        this.westdistance = westdistance;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (campus != null ? campus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Distances)) {
            return false;
        }
        Distances other = (Distances) object;
        if ((this.campus == null && other.campus != null) || (this.campus != null && !this.campus.equals(other.campus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Distances[ campus=" + campus + " ]";
    }
    
}
