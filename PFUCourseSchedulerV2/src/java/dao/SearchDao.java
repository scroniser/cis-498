package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import model.Preferences;
import model.Sections;
import model.Schedule;
import util.DbUtil;

public class SearchDao {

    private Connection connection;

    public SearchDao() {
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
	
	public List<Sections> getSectionByNum(Long callnumber) {
		List<Sections> sections = new ArrayList<Sections>();
		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from Userid.Sections where callnumber=?");
			preparedStatement.setLong(1, callnumber);
                        
			ResultSet rs = preparedStatement.executeQuery();
			
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sections;
        }
        
        public List<Sections> getSectionByDpt(String department) {
		List<Sections> sections = new ArrayList<Sections>();
		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from Userid.Sections where department=?");
			preparedStatement.setString(1, department);
                        
			ResultSet rs = preparedStatement.executeQuery();
			
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sections;
        }
        
        public List<Sections> getSectionByCourseNum(Long coursenumber) {
		List<Sections> sections = new ArrayList<Sections>();
		try {
                        
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from Userid.Sections where coursenumber=?");
			preparedStatement.setLong(1, coursenumber);
                        
			ResultSet rs = preparedStatement.executeQuery();
			
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sections;
        }
        
        public List<Sections> getSectionByDays(String days) {
            List<Sections> sections = new ArrayList<Sections>();
            try {
                PreparedStatement preparedStatement = connection.
                                prepareStatement("select * from Userid.Sections where days=?");
                preparedStatement.setString(1, days);

                ResultSet rs = preparedStatement.executeQuery();
			
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sections;
        }
        
        public List<Sections> getSectionByStart(Time starttime) {
            List<Sections> sections = new ArrayList<Sections>();
            try {
                PreparedStatement preparedStatement = connection.
                                prepareStatement("select * from Userid.Sections where starttime=?");
                preparedStatement.setTime(1, starttime);

                ResultSet rs = preparedStatement.executeQuery();
			
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sections;
        }
        
    public List<Sections> getSectionByEnd(Time endtime) {
            List<Sections> sections = new ArrayList<Sections>();
            try {
                PreparedStatement preparedStatement = connection.
                                prepareStatement("select * from Userid.Sections where endtime=?");
                preparedStatement.setTime(1, endtime);

                ResultSet rs = preparedStatement.executeQuery();
			
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sections;
        }
    
    public List<Sections> getSectionByMedia(String mediarequired) {
            List<Sections> sections = new ArrayList<Sections>();
            try {
                PreparedStatement preparedStatement = connection.
                                prepareStatement("select * from Userid.Sections where mediarequired=?");
                preparedStatement.setString(1, mediarequired);

                ResultSet rs = preparedStatement.executeQuery();
			
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sections;
        }
}
