package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import model.Distances;
import model.Preferences;
import model.Rooms;
import model.Schedule;
import model.Sections;
import util.DbUtil;

public class UnscheduledCoursesDao {

    private Connection connection;

    public UnscheduledCoursesDao() {
	connection = DbUtil.getConnection();
    }

    public List<Sections> getUnscheduledCourses() {
	List<Sections> sections = new ArrayList<Sections>();
	try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT SE.* FROM USERID.SECTIONS SE WHERE SE.CALLNUMBER NOT IN (SELECT SC.CALLNUMBER FROM USERID.SCHEDULE SC)");
            while (rs.next()) {
		Sections section = new Sections();
                section.setCallnumber(rs.getLong("callnumber"));
                section.setDepartment(rs.getString("department"));
		section.setCoursenumber(rs.getLong("coursenumber"));
		section.setDays(rs.getString("days"));
		section.setStarttime(rs.getTime("starttime"));
                section.setEndtime(rs.getTime("endtime"));
                section.setMediarequired(rs.getString("mediarequired"));
                sections.add(section);
            }
	}
        catch (SQLException e) {
            e.printStackTrace();
	}
	return sections;
	}
        
    public Sections getSpecificSection(Long callNumber) {
        Sections specificSection = new Sections();
        try {
            //Get the information of a specific section
            PreparedStatement preparedStatement = connection.
                                 prepareStatement("SELECT * FROM USERID.SECTIONS WHERE CALLNUMBER = ?");
            preparedStatement.setLong(1, callNumber);
            ResultSet rs = preparedStatement.executeQuery();
            while ( rs.next() ) {
                specificSection.setCallnumber(rs.getLong("CALLNUMBER"));
                specificSection.setDepartment(rs.getString("DEPARTMENT"));
                specificSection.setCoursenumber(rs.getLong("COURSENUMBER"));
                specificSection.setDays(rs.getString("DAYS"));
                specificSection.setStarttime(rs.getTime("STARTTIME"));
                specificSection.setEndtime(rs.getTime("ENDTIME"));
                specificSection.setMediarequired(rs.getString("MEDIAREQUIRED"));
            }
        }
        catch ( SQLException e ) {
            e.printStackTrace();
        }
        return specificSection;
    }
    
    public Schedule getSpecificSchedule(Long callNumber) {
        Schedule specificSchedule = new Schedule();
        try {
            //Get schedule of section we are trying to update
            PreparedStatement preparedStatement = connection.
                                 prepareStatement("SELECT * FROM USERID.SCHEDULE WHERE CALLNUMBER = ?");
            preparedStatement.setLong(1, callNumber);
            ResultSet rs = preparedStatement.executeQuery();
            while ( rs.next() ) {
                specificSchedule.setCallnumber(rs.getLong("CALLNUMBER"));
                specificSchedule.setCoursenumber(rs.getLong("COURSENUMBER"));
                specificSchedule.setDepartment(rs.getString("DEPARTMENT"));
                specificSchedule.setDays(rs.getString("DAYS"));
                specificSchedule.setStartime(rs.getTime("STARTIME"));
                specificSchedule.setEndtime(rs.getTime("ENDTIME"));
                specificSchedule.setInstructor(rs.getString("INSTRUCTOR"));
                specificSchedule.setRoom(rs.getShort("ROOM"));
                specificSchedule.setBuilding(rs.getString("BUILDING"));
            }
        }
        catch ( SQLException e ) {
            e.printStackTrace();
        }
        return specificSchedule;
    }
    
    public List<Schedule> getSpecificProfSched(String instructor) {
        List<Schedule> schedule = new ArrayList<Schedule>();
        try {
            PreparedStatement preparedStatement = connection.
                                prepareStatement("select * from Userid.Schedule where instructor=?");
            preparedStatement.setString(1, instructor);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Schedule sched = new Schedule();
                sched.setCallnumber(rs.getLong("callnumber"));
                sched.setCoursenumber(rs.getLong("coursenumber"));
                sched.setDepartment(rs.getString("department"));
                sched.setDays(rs.getString("days"));
                sched.setStartime(rs.getTime("startime"));
                sched.setEndtime(rs.getTime("endtime"));
                sched.setInstructor(rs.getString("instructor"));
                sched.setRoom(rs.getShort("room"));
                sched.setBuilding(rs.getString("building"));
                schedule.add(sched);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return schedule;
    }
    
    public List<Schedule> getSpecificRoomSched(String building, Short room) {
        List<Schedule> schedule = new ArrayList<Schedule>();
        try {
            PreparedStatement preparedStatement = connection.
                            prepareStatement("select * from Userid.Schedule where building=? and room=?");
            preparedStatement.setString(1, building);
            preparedStatement.setString(2, room.toString());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Schedule sched = new Schedule();
                sched.setCallnumber(rs.getLong("callnumber"));
                sched.setCoursenumber(rs.getLong("coursenumber"));
                sched.setDepartment(rs.getString("department"));
                sched.setDays(rs.getString("days"));
                sched.setStartime(rs.getTime("startime"));
                sched.setEndtime(rs.getTime("endtime"));
                sched.setInstructor(rs.getString("instructor"));
                sched.setRoom(rs.getShort("room"));
                sched.setBuilding(rs.getString("building"));
                schedule.add(sched);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    public Schedule doScheduleAssignment (Long callNumber, String instructor, String building, Short roomNum) {
        Sections sectionToAssign = new Sections();
        Schedule updatedSchedule = new Schedule();
        Rooms selectedRoom = new Rooms();
        Rooms scheduledRoom = new Rooms();
        List<Schedule> instructorSched = new ArrayList<Schedule>();
        
        sectionToAssign = getSpecificSection(callNumber);
            
        //Get the selected room and instructor scheduled room information from the Rooms database table
        RoomDao roomDao = new RoomDao();
        selectedRoom = roomDao.getSpecificRoom(building, roomNum);
           
        //Get the selected instructor schedule from the Schedule database table
        instructorSched = getSpecificProfSched(instructor);
        
        //Determine if selected room has an open time slot
        boolean roomAvailable = isRoomAvailable(selectedRoom, sectionToAssign.getStarttime(), sectionToAssign.getEndtime(), sectionToAssign.getDays());
        
        //If the instructor does not have any assigned sections
        if ( instructorSched.isEmpty() ) {
            //If the selected room has an open time slot
            if ( roomAvailable ) {
                //Update the Schedule database table
                insertSchedule(instructor, selectedRoom, sectionToAssign);
                //Get the newly updated section from the Schedule database table
                updatedSchedule = getSpecificSchedule(callNumber);
            }
            else {
                updatedSchedule = null;
            }
        }
        //If the instructor has assigned sections
        else {
            //Get the total number of time distance conflicts
            int numConflicts = timeDistanceConflicts(selectedRoom, sectionToAssign, instructorSched);
            //If there are time distance conflicts then do not reassign the section
            if ( numConflicts > 0 ) {
                updatedSchedule = null;
            }
            //If there are no time distance conflicts
            else {
                //If the room is available
                if ( roomAvailable ) {
                    //Update the Schedule database table
                    insertSchedule(instructor, selectedRoom, sectionToAssign);
                    //Get the newly updated section from the Schedule database table
                    updatedSchedule = getSpecificSchedule(callNumber);
                }
            }
        }
        return updatedSchedule;
    }
    
    public boolean isRoomAvailable(Rooms selectedRoom, Time courseStartime, Time courseEndtime, String days) {
        boolean roomAvailable = false;
        
        //Get the Room Chart for current room using room number and building
        List<Schedule> roomSched = getSpecificRoomSched(selectedRoom.getDepartment(), selectedRoom.getRoom());
                
        //Set the extreme start and end time
        Time earliestStartime = java.sql.Time.valueOf("23:59:59");
        Time latestEndtime = java.sql.Time.valueOf("00:00:00");
                
        //If the Room Chart is NOT empty
        if ( !roomSched.isEmpty() ) {
            for ( Schedule roomSched1 : roomSched ) {
                //If assigned section and scheduled section share at least one day
                if ( (Integer.parseInt(roomSched1.getDays(), 2) & Integer.parseInt(days, 2)) > 0 ) {
                    //If the start time of the section we want to reassign comes after or
                    //is the same as the start time of the instructor's assigned section
                    if ( courseStartime.after(roomSched1.getStartime()) || courseStartime.equals(roomSched1.getStartime()) ) {
                        //If the start time of the section we want to reassign comes
                        //before the end time of the instructor's assigned section
                        if ( courseStartime.before(roomSched1.getEndtime()) ) {
                            //The room is not available because the start time of the section we want to reassign
                            //falls between the start and end time of the instructor's assigned section
                            earliestStartime = java.sql.Time.valueOf("23:59:59");
                            latestEndtime = java.sql.Time.valueOf("00:00:00");
                            roomAvailable = false;
                            break;
                        }
                    }
                    //If the end time of the section we want to reassign comes before or
                    //is the same as the end time of the instructor's assigned section
                    if ( courseEndtime.before(roomSched1.getEndtime()) || courseEndtime.equals(roomSched1.getEndtime()) ) {
                        //If the end time of the section we want to reassign comes
                        //after the start time of the instructor's assigned section
                        if ( courseEndtime.after(roomSched1.getStartime()) ) {
                            //The room is not available because the end time of the section we want to reassign
                            //falls between the start and end time of the instructor's assigned section
                            earliestStartime = java.sql.Time.valueOf("23:59:59");
                            latestEndtime = java.sql.Time.valueOf("00:00:00");
                            roomAvailable = false;
                            break;
                        }
                    }
                    //If the end time of the instructor's assigned section comes before
                    //or is equal to the start time of the section we want to reassign
                    if ( roomSched1.getEndtime().before(courseStartime) || roomSched1.getEndtime().equals(courseStartime) ) {
                        //If the end time of the instructor's assigned section
                        //comes after or is the same as the latest end time
                        if ( roomSched1.getEndtime().after(latestEndtime) || roomSched1.getEndtime().equals(latestEndtime) ) {
                            //Update the latest end time to the end time of the instructor's assigned section
                            latestEndtime = roomSched1.getEndtime();
                        }
                    }
                    //If the start time of the instructor's assigned section comes after
                    //or is equal to the end time of the section we want to reassign
                    if ( roomSched1.getStartime().after(courseEndtime) || roomSched1.getStartime().equals(courseEndtime) ) {
                        //If the start time of the instructor's assigned section
                        //comes before or is the same as the earliest start time
                        if ( roomSched1.getStartime().before(earliestStartime) || roomSched1.getStartime().equals(earliestStartime) ) {
                            //Update the earliest end time to the start time of the instructor's assigned section
                            earliestStartime = roomSched1.getStartime();
                        }
                    }
                }
                else {
                    roomAvailable = true;
                }
            }
            //If the latest end time and earliest start time are both NOT stil set to the extremes
            if ( !(earliestStartime.equals(java.sql.Time.valueOf("23:59:59"))) || !(latestEndtime.equals(java.sql.Time.valueOf("00:00:00"))) ) {
                //If the times of the reassigned section are between the latest
                //end time and the earliest start time then the room is available
                if ( courseStartime.after(latestEndtime) || courseStartime.equals(latestEndtime) ) {
                    if ( courseEndtime.before(earliestStartime) || courseEndtime.equals(earliestStartime) ) {
                        //The room is available
                        roomAvailable = true;
                    }
                }
            }
//            else {
//                roomAvailable = false;
//            }
        }
        //If the Room Chart for the selected room is empty then the room is available
        else if ( roomSched.isEmpty() ) {
            roomAvailable = true;
        } 
        return roomAvailable;
    }
    
    public int timeDistanceConflicts (Rooms selectedRoom, Sections sectionToAssign, List<Schedule> instructorSched) {
        RoomDao roomDao = new RoomDao();
        //Counter to keep track of the number of conflicts
        int conflictCounter = 0;
        
        //Get the time distance matrix for the campus of the selected room
        DistancesDao distanceDao = new DistancesDao();
        Distances timeDistanceMatrix = new Distances();
        timeDistanceMatrix = distanceDao.getDistance(selectedRoom.getCampus());
        
        //If the selected room is on the same campus as the room of
        //the section we want to assign then there are no conflicts
        
        //Determine if the selected instructor will have any time distance conflicts
        Double timeDistanceHours = 0.0;
        //Set up several variables to hold section start and end times in milliseconds
        long timeDistanceMilliSec;
        long sectionToAssignStartTime = sectionToAssign.getStarttime().getTime();
        long sectionToAssignEndTime = sectionToAssign.getEndtime().getTime();
        long instructorSchedStartTime, instructorSchedEndTime;

        //Hold the room information for each section assigned to the instructor
        Rooms instructorSchedRoom = new Rooms();
            
        //Iterate through each section assigned to the instructor
        for ( Schedule instructorSched1 : instructorSched ) {
            //Check to see that the section we want to reassign
            //is not the same as the instructor's assigned section
            if ( !(sectionToAssign.getCallnumber().equals(instructorSched1.getCallnumber())) ) {
                //Do a bitwise AND on the Days fields to see if sections share at least one day
                if ( (Integer.parseInt(sectionToAssign.getDays(), 2) & Integer.parseInt(instructorSched1.getDays(), 2)) > 0 ) {
                    //Get the room information for the instructor's assigned section
                    instructorSchedRoom = roomDao.getSpecificRoom(instructorSched1.getBuilding(), instructorSched1.getRoom());
                    //Get the time distance value for the campus of the instructor's assigned section
                    //from the time distance matrix we retrieved for the campus of the selected room
                    switch ( instructorSchedRoom.getCampus() ) {
                        case "NORTH":
                            timeDistanceHours = (timeDistanceMatrix.getNorthdistance()) * 10;
                            break;
                        case "SOUTH":
                            timeDistanceHours = (timeDistanceMatrix.getSouthdistance()) * 10;
                            break;
                        case "EAST":
                            timeDistanceHours = (timeDistanceMatrix.getEastdistance()) * 10;
                            break;
                        case "WEST":
                            timeDistanceHours = (timeDistanceMatrix.getWestdistance()) * 10;
                            break;
                    }
                    //Convert the time distance hours value into milliseconds
                    timeDistanceMilliSec = (timeDistanceHours.longValue() * 3600000) / 10;
                    //If the end time of the section we want to reassign comes
                    //before the start time the instructor's assigned section
                    if ( sectionToAssign.getEndtime().before(instructorSched1.getStartime()) ) {
                        //Get the start time of the instructor's assigned section in milliseconds
                        instructorSchedStartTime = instructorSched1.getStartime().getTime();
                        //If the end time of the section we want to reassign plus the time distance
                        //is greater than the start time of the instructor's assigned section
                        if ( (sectionToAssignEndTime + timeDistanceMilliSec) > instructorSchedStartTime ) {
                            conflictCounter++;
                        }
                    }
                    //If the start time of the section we want to reassign comes
                    //after the end time of the instructor's assigned section
                    else if ( sectionToAssign.getStarttime().after(instructorSched1.getEndtime()) ) {
                        //Get the end time of the instructor's assigned section in milliseconds
                        instructorSchedEndTime = instructorSched1.getEndtime().getTime();
                        //If the end time of the instructor's assigned section plus the time distance
                        //is greater than the start time of the section we want to reassign
                        if ( (instructorSchedEndTime + timeDistanceMilliSec) > sectionToAssignStartTime ) {
                            conflictCounter++;
                        }
                    }
                }
            }
        }
        return conflictCounter;
    }//end timeDistanceConflicts
    
    public void insertSchedule (String selectedInstructor, Rooms selectedRoom, Sections section) {
        //Insert into the Schedule database table with the selected room and instructor for the given call number
        try {
            PreparedStatement preparedStatement = connection.
                                prepareStatement("INSERT INTO USERID.SCHEDULE (CALLNUMBER, COURSENUMBER, DEPARTMENT, DAYS, STARTIME, ENDTIME, INSTRUCTOR, ROOM, BUILDING) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setLong(1, section.getCallnumber());
            preparedStatement.setLong(2, section.getCoursenumber());
            preparedStatement.setString(3, section.getDepartment());
            preparedStatement.setString(4, section.getDays());
            preparedStatement.setTime(5, section.getStarttime());
            preparedStatement.setTime(6, section.getEndtime());
            preparedStatement.setString(7, selectedInstructor);
            preparedStatement.setShort(8, selectedRoom.getRoom());
            preparedStatement.setString(9, selectedRoom.getDepartment());
            preparedStatement.executeUpdate();
        }
        catch ( SQLException e ) {
            e.printStackTrace();
        }
    }//end insertSchedule
    
    public void addPreferences(Preferences preferences) {
	try {
            PreparedStatement preparedStatement = connection
				.prepareStatement("insert into Userid.Preferences(department=?,instructor=?,sections=?,northcampus=?,southcampus=?,eastcampus=?,westcampus=?,weekend=?) values (?, ?, ?, ?, ?, ?, ?, ? )");
            // Parameters start with 1
            preparedStatement.setString(1, preferences.getDepartment());
            preparedStatement.setString(2, preferences.getInstructor());
            preparedStatement.setString(3, preferences.getSections());
            preparedStatement.setString(4, preferences.getNorthcampus());
            preparedStatement.setString(5, preferences.getSouthcampus());
            preparedStatement.setString(6, preferences.getEastcampus());
            preparedStatement.setString(7, preferences.getWestcampus());
            preparedStatement.setString(8, preferences.getWeekend());
            preparedStatement.executeUpdate();

	}
        catch (SQLException e) {
            e.printStackTrace();
	}
    }
	
	public void deletePreferences(String instructor, String department) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("delete from Userid.Preferences where instructor=? and department=?");
			// Parameters start with 1
                        preparedStatement.setString(1, instructor);
                        preparedStatement.setString(2, department);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updatePreferences(Preferences preferences) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update Userid.Preferences set department=?, instructor=?, sections=?, northcampus=?, southcampus=?, eastcampus=?, westcampus=?, weekend=?" +
							"where department=? and instructor=?");
			// Parameters start with 1
			preparedStatement.setString(1, preferences.getDepartment());
			preparedStatement.setString(2, preferences.getInstructor());
			preparedStatement.setString(3, preferences.getSections());
			preparedStatement.setString(4, preferences.getNorthcampus());
			preparedStatement.setString(5, preferences.getSouthcampus());
                        preparedStatement.setString(6, preferences.getEastcampus());
                        preparedStatement.setString(7, preferences.getWestcampus());
                        preparedStatement.setString(8, preferences.getWeekend());
                        preparedStatement.setString(9, preferences.getDepartment());
                        preparedStatement.setString(10, preferences.getInstructor());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Preferences getPreferencesById(String instructor, String department) {
		Preferences preference = new Preferences();
		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from Userid.Preferences where instructor=? and department=?");
			preparedStatement.setString(1, instructor);
                        preparedStatement.setString(2, department);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				preference.setDepartment(rs.getString("department"));
				preference.setInstructor(rs.getString("instructor"));
				preference.setSections(rs.getString("sections"));
				preference.setNorthcampus(rs.getString("northcampus"));
				preference.setSouthcampus(rs.getString("southcampus"));
                                preference.setWestcampus(rs.getString("westcampus"));
                                preference.setEastcampus(rs.getString("eastcampus"));
                                preference.setWeekend(rs.getString("weekend"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return preference;
	}
}
