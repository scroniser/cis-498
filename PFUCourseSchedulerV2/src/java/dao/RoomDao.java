package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Preferences;
import model.Rooms;
import model.Schedule;
import util.DbUtil;

public class RoomDao {

	private Connection connection;

	public RoomDao() {
		connection = DbUtil.getConnection();
	}

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

		} catch (SQLException e) {
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
	
	public void updateRooms(Rooms rooms) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update Userid.Rooms set department=?, room=?, campus=?, capacity=?, mediaavailable=?" +
							"where department=? and room=?");
			// Parameters start with 1
			preparedStatement.setString(1, rooms.getDepartment());
			preparedStatement.setShort(2, rooms.getRoom());
			preparedStatement.setString(3, rooms.getCampus());
			preparedStatement.setShort(4, rooms.getCapacity());
			preparedStatement.setBoolean(5, rooms.getMediaavailable());
                        preparedStatement.setString(6, rooms.getDepartment());
                        preparedStatement.setShort(7, rooms.getRoom());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    /**
     *
     * @return
     */
    public List<Rooms> getAllRooms() {
		List<Rooms> rooms = new ArrayList<Rooms>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from Userid.Rooms");
			while (rs.next()) {
				Rooms room = new Rooms();
                                room.setDepartment(rs.getString("department"));
                                room.setRoom(rs.getShort("room"));
                                room.setCampus(rs.getString("campus"));
				room.setCapacity(rs.getShort("capacity"));
                                room.setMediaavailable(rs.getBoolean("mediaavailable"));
                                rooms.add(room);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rooms;
	}
	
    /**
     *
     * @param building
     * @param room
     * @return
     */
    public Rooms getSpecificRoom(String building, Short room) {
		Rooms rooms = new Rooms();
		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from Userid.Rooms where department=? and room=?");
			preparedStatement.setString(1, building);
                        preparedStatement.setShort(2, room);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				rooms.setDepartment(rs.getString("department"));
                                rooms.setRoom(rs.getShort("room"));
                                rooms.setCampus(rs.getString("campus"));
				rooms.setCapacity(rs.getShort("capacity"));
                                rooms.setMediaavailable(rs.getBoolean("mediaavailable"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rooms;
	}
}
