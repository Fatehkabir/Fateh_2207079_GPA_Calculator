import java.sql.*;

public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:sample.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connected to SQLite!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS courses (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                course_name TEXT,
                course_code TEXT,
                course_credit REAL,
                grade REAL,
                teacher1 TEXT,
                teacher2 TEXT
            )
        """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertCourse(course c) {
        String sql = "INSERT INTO courses(course_name, course_code, course_credit, grade, teacher1, teacher2) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, c.getCourseName());
            pstmt.setString(2, c.getCourseCode());
            pstmt.setDouble(3, c.getCourseCredit());
            pstmt.setDouble(4, c.getgradepoint());
            pstmt.setString(5, c.getteacher1name());
            pstmt.setString(6, c.getteacher2name());

            pstmt.executeUpdate();
            System.out.println("Data inserted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
