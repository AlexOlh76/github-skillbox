import java.sql.*;

public class DBConnection {

    private static Connection connection;

    private static String dbName = "learn";
    private static String dbUser = "root";
    private static String dbPass = "ertghj_360";

    public static  StringBuilder insertQuery = new StringBuilder();

    public static Connection getConnection() {

        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dbName +
                        "?user=" + dbUser + "&password=" + dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "name TINYTEXT NOT NULL, " +
                    "birthDate DATE NOT NULL, " +
                    "`count` INT NOT NULL, " +
                    "PRIMARY KEY(id))");// +
//                    "UNIQUE KEY name_date(name(50), birthDate))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void executeMultiInsert() throws  SQLException{


            String sql = "INSERT INTO voter_count(name, birthDate, `count`) " +
                    "VALUES" + insertQuery.toString() +
                    "ON DUPLICATE KEY UPDATE `count`= `count` + 1";
            DBConnection.getConnection().createStatement().execute(sql);
    }


    public static void printVoterCounts() throws SQLException {
        String sql = "SELECT name, birthDate, COUNT(*) `count` FROM voter_count GROUP BY `name`, `birthDate` HAVING `count` > 1";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while (rs.next()) {
            System.out.println("\t" + rs.getString("name") + " (" +
                rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
    }
}
