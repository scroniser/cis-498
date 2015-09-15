/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Andres
 */
public class TimeDistance {
    
    private String campus;
    private double northDistance;
    private double southDistance;
    private double eastDistance;
    private double westDistance;

    /**
     * @return the campus
     */
    public String getCampus() {
        return campus;
    }

    /**
     * @param campus the campus to set
     */
    public void setCampus(String campus) {
        this.campus = campus;
    }

    /**
     * @return the northDistance
     */
    public double getNorthDistance() {
        return northDistance;
    }

    /**
     * @param northDistance the northDistance to set
     */
    public void setNorthDistance(double northDistance) {
        this.northDistance = northDistance;
    }

    /**
     * @return the southDistance
     */
    public double getSouthDistance() {
        return southDistance;
    }

    /**
     * @param southDistance the southDistance to set
     */
    public void setSouthDistance(double southDistance) {
        this.southDistance = southDistance;
    }

    /**
     * @return the eastDistance
     */
    public double getEastDistance() {
        return eastDistance;
    }

    /**
     * @param eastDistance the eastDistance to set
     */
    public void setEastDistance(double eastDistance) {
        this.eastDistance = eastDistance;
    }

    /**
     * @return the westDistance
     */
    public double getWestDistance() {
        return westDistance;
    }

    /**
     * @param westDistance the westDistance to set
     */
    public void setWestDistance(double westDistance) {
        this.westDistance = westDistance;
    }
    
}
