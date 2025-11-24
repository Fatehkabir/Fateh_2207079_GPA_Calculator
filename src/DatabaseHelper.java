import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:courses.db";

    public static void initDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS courses (" +
                    "courseCode INTEGER PRIMARY KEY," +
                    "courseName TEXT NOT NULL," +
                    "teacher1Name TEXT," +
                    "teacher2Name TEXT," +
                    "creditValue REAL," +
                    "gradeValue TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertCourse(course c) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO courses(courseCode, courseName, teacher1Name, teacher2Name, creditValue, gradeValue) VALUES(?,?,?,?,?,?)")) {
            ps.setInt(1, c.getCourseCode());
            ps.setString(2, c.getCourseName());
            ps.setString(3, c.getTeacher1Name());
            ps.setString(4, c.getTeacher2Name());
            ps.setDouble(5, c.getCreditValue());
            ps.setString(6, c.getGradeValue());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCourse(course c) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE courses SET courseName=?, teacher1Name=?, teacher2Name=?, creditValue=?, gradeValue=? WHERE courseCode=?")) {
            ps.setString(1, c.getCourseName());
            ps.setString(2, c.getTeacher1Name());
            ps.setString(3, c.getTeacher2Name());
            ps.setDouble(4, c.getCreditValue());
            ps.setString(5, c.getGradeValue());
            ps.setInt(6, c.getCourseCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCourse(int courseCode) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement("DELETE FROM courses WHERE courseCode=?")) {
            ps.setInt(1, courseCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<course> getAllCourses() {
        ObservableList<course> list = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM courses")) {
            while (rs.next()) {
                list.add(new course(
                        rs.getString("courseName"),
                        rs.getInt("courseCode"),
                        rs.getString("teacher1Name"),
                        rs.getString("teacher2Name"),
                        rs.getDouble("creditValue"),
                        rs.getString("gradeValue")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
