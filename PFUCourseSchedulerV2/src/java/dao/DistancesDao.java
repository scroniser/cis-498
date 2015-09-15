package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Distances;
import model.Preferences;
import model.Sections;
import util.DbUtil;

public class DistancesDao {

    private Connection connection;

    public DistancesDao() {
            connection = DbUtil.getConnection();
    }

    public Distances getDistance(String campus) {
        Distances distances = new Distances();
        try {
                PreparedStatement preparedStatement = connection.
                                prepareStatement("SELECT * FROM USERID.DISTANCES WHERE CAMPUS = ?");
                preparedStatement.setString(1, campus);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                        distances.setNorthdistance(rs.getDouble("northdistance"));
                        distances.setSouthdistance(rs.getDouble("southdistance"));
                        distances.setEastdistance(rs.getDouble("eastdistance"));
                        distances.setWestdistance(rs.getDouble("westdistance"));
                }
        } catch (SQLException e) {
                e.printStackTrace();
        }

        return distances;
    }
}
