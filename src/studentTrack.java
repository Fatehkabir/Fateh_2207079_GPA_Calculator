import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class studentTrack {
    private static final String URL = "jdbc:sqlite:student.db";

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                "roll INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "gpa REAL NOT NULL)";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertStudent(int roll, String name, double gpa) {
        String sql = "INSERT INTO students(roll, name, gpa) VALUES(?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roll);
            pstmt.setString(2, name);
            pstmt.setDouble(3, gpa);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<studentinfo> getAllStudents() {
        ObservableList<studentinfo> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM students";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while(rs.next()) {
                int roll = rs.getInt("roll");
                String name = rs.getString("name");
                double gpa = rs.getDouble("gpa");

                list.add(new studentinfo(roll, name, gpa));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
