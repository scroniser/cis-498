/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


/**
 *
 * @author scroniser
 */
public class Rooms {
    private String department;
    private short room;
    private String campus;
    private short capacity;
    private Boolean mediaavailable;

    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
	this.department = department;
    }

    public short getRoom() {
        return room;
    }

    public void setRoom(short room){
        this.room = room;
    }
    
    public String getCampus() {
        return campus;
    }
    
    public void setCampus(String campus) {
	this.campus = campus;
    }

    public short getCapacity() {
        return capacity;
    }

    public void setCapacity(short capacity) {
        this.capacity = capacity;
    }

    public Boolean getMediaavailable() {
        return mediaavailable;
    }

    public void setMediaavailable(Boolean mediaavailable) {
        this.mediaavailable = mediaavailable;
    }
    
    @Override
    public String toString() {
	return "Room [department=" + department + ", room=" +  Short.toString(room)
				+ ", campus=" + campus + ", capacity="
				+ Short.toString(capacity) + ", mediaavailable="
				+ Boolean.toString(mediaavailable) + "]";
	}
    
    @Override
    public boolean equals(Object o) {
	return (this.department.equals(((Rooms) o).department)
	            && (this.room == ((Rooms) o).room)
                    && this.campus.equals(((Rooms) o).campus)
                    && (this.capacity == ((Rooms) o).capacity)
                    && this.mediaavailable.equals(((Rooms) o).mediaavailable));
    }
}
