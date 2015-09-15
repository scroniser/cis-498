package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Preferences;
import util.DbUtil;

public class PreferencesDao {

    private Connection connection;

    public PreferencesDao() {
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

    public Preferences specificProfPreferences(String userid){
        Preferences preference = new Preferences();
		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from USERID.PREFERENCES WHERE userid = ?");
			preparedStatement.setString(1, userid);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
                                preference.setUserid(rs.getLong("userid"));
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
    
    public List<Preferences> getAllPreferences() {
    List<Preferences> preferences = new ArrayList<Preferences>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from Userid.Preferences");
            while (rs.next()) {
                Preferences preference = new Preferences();
                preference.setDepartment(rs.getString("department"));
                preference.setInstructor(rs.getString("instructor"));
                preference.setSections(rs.getString("sections"));
                preference.setNorthcampus(rs.getString("northcampus"));
                preference.setSouthcampus(rs.getString("southcampus"));
                preference.setWestcampus(rs.getString("westcampus"));
                preference.setEastcampus(rs.getString("eastcampus"));
                preference.setWeekend(rs.getString("weekend"));
                preferences.add(preference);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return preferences;
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
