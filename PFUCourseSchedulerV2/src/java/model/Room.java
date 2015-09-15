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
public class Room {
    
    private String department;
    private Short room;
    private String campus;
    private Short capacity;
    private String mediaAvailable;

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the room
     */
    public Short getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(short room) {
        this.room = room;
    }

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
     * @return the capacity
     */
    public Short getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(short capacity) {
        this.capacity = capacity;
    }

    /**
     * @return the mediaAvailable
     */
    public String getMediaAvailable() {
        return mediaAvailable;
    }

    /**
     * @param mediaAvailable the mediaAvailable to set
     */
    public void setMediaAvailable(String mediaAvailable) {
        this.mediaAvailable = mediaAvailable;
    }
    
}
