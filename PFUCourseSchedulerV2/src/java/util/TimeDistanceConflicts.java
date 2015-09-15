/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.GetSingleRoomDAO;
import dao.GetTimeDistanceMatrixDAO;
import model.Room;
import model.Schedule;
import model.TimeDistance;

import java.sql.SQLException;

import java.util.ArrayList;

/**
 *
 * @author Andres
 */
public class TimeDistanceConflicts {
    
    public int TimeDistanceConflicts(Schedule schedule, Room newRoom, ArrayList<Schedule> instructorAssignmentList, TimeDistance timeDistanceMatrix) {
        
        System.out.println("Schedule call number: " + schedule.getCallnumber());
        System.out.println("Schedule building: " + schedule.getBuilding());
        System.out.println("Schedule room: " + schedule.getRoom());
        System.out.println("New building: " + newRoom.getDepartment());
        System.out.println("New room: " + newRoom.getRoom());
        System.out.println("New campus: " + newRoom.getCampus());
        Double timeDistanceHours = 0.0;
        Long timeDistanceMilliSec;
        Long scheduleStartime = schedule.getStartime().getTime();
        System.out.println("Schedule start time: " + scheduleStartime);
        Long scheduleEndtime = schedule.getEndtime().getTime();
        System.out.println("Schedule end time: " + scheduleEndtime);
        long assignedStartime, assignedEndtime;
        //Counter to keep track of time conflicts
        int conflictCounter = 0;
        String assignedCampus = "";
        //Instantiate dao class to make database call
        GetSingleRoomDAO gsrDAO = new GetSingleRoomDAO();
        //Iterate through each section assigned to the instructor
        for ( Schedule assigned : instructorAssignmentList ) {
            //Check to see that it's not the same section
            if ( schedule.getCallnumber() != assigned.getCallnumber() ) {
                //Do a bitwise AND on the Days fields to see if sections share at least one day
                if ( (Integer.parseInt(schedule.getDays(), 2) & Integer.parseInt(assigned.getDays(), 2)) > 0 ) {
                    assignedStartime = assigned.getStartime().getTime();
                    System.out.println("Assigned start time: " + assignedStartime);
                    assignedEndtime = assigned.getEndtime().getTime();
                    System.out.println("Assigned end time: " + assignedEndtime);
                    try {
                        assignedCampus = (gsrDAO.GetSingleRoomDAO(assigned.getBuilding(), assigned.getRoom())).getCampus();
                        System.out.println("Assigned campus is: " + assignedCampus);
                        switch (assignedCampus) {
                            case "NORTH":
                                System.out.println("Assigned section is on the North campus.");
                                timeDistanceHours = (timeDistanceMatrix.getNorthDistance()) * 10;
                                break;
                            case "SOUTH":
                                System.out.println("Assigned section is on the South campus.");
                                timeDistanceHours = (timeDistanceMatrix.getSouthDistance()) * 10;
                                break;
                            case "EAST":
                                System.out.println("Assigned section is on the East campus.");
                                timeDistanceHours = (timeDistanceMatrix.getEastDistance()) * 10;
                                break;
                            case "WEST":
                                System.out.println("Assigned section is on the West campus.");
                                timeDistanceHours = (timeDistanceMatrix.getWestDistance()) * 10;
                                break;
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                    //Get the time distance between the two campuses
                    timeDistanceMilliSec = (timeDistanceHours.longValue() * 3600000) / 10;
                    System.out.println("Distance in milliseconds: " + timeDistanceMilliSec);
                    if ( scheduleStartime < assignedStartime ) {
                        System.out.println("Scheduled start time is less than assigned start time.");
                        if ( (scheduleEndtime + timeDistanceMilliSec) > assignedStartime ) {
                            conflictCounter++;
                        }
                    }
                    else if ( scheduleStartime > assignedStartime ) {
                        System.out.println("Scheduled start time is greater than assigned start time.");
                        if ( (assignedEndtime + timeDistanceMilliSec) > scheduleStartime ) {
                            conflictCounter++;
                        }
                    }
                }
            }
        }
        System.out.println("Number of conflicts: " + conflictCounter);
        return conflictCounter;
    }
}
