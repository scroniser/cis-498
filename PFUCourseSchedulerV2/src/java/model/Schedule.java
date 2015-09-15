package model;

import java.sql.Time;

public class Schedule {

    private Long callnumber;
    private Long coursenumber;
    private String department;
    private String days;
    private Time startime;
    private Time endtime;
    private String instructor;
    private Short room;
    private String building;

    public Long getCallnumber() {
            return callnumber;
    }
    public void setCallnumber(Long callnumber) {
            this.callnumber = callnumber;
    }
    public Long getCoursenumber() {
            return coursenumber;
    }
    public void setCoursenumber(Long coursenumber) {
            this.coursenumber = coursenumber;
    }
    public String getDepartment() {
            return department;
    }
    public void setDepartment(String department) {
            this.department = department;
    }
    public String getDays() {
        return days;
    }
    public void setDays(String days) {
        this.days = days;
    }
    public Time getStartime() {
        return startime;
    }
    public void setStartime(Time startime){
        this.startime = startime;
    }
    public Time getEndtime() {
        return endtime;
    }
    public void setEndtime(Time endtime){
        this.endtime = endtime;
    }
    public String getInstructor() {
        return instructor;
    }
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Short getRoom() {
        return room;
    }
    public void setRoom(Short room){
        this.room = room;
    }

    public String getBuilding(){
        return building;
    }
    public void setBuilding(String building){
        this.building = building;
    }
    @Override
    public String toString() {
        return "Schedule [callnumber=" + callnumber + ", coursenumber=" + coursenumber
                + ", department=" + department + ", days="
                + days + ", startime="
                + startime + ", endtime="
                + endtime + ", instructor="
                + instructor + ", room="
                + room + ", building=" 
                + building + "]";
    }
	
	
}
