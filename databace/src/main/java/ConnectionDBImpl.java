import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionDBImpl implements ConnectionDB {

    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "1234";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DRIVER = "org.postgresql.Driver";
    private Connection connection;
    public ConnectionDBImpl() {
        this.connection = createConnectiob();
    }
    private Connection createConnectiob() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void execute(String quaery) throws SQLException {
        connection.createStatement().execute(quaery);
    }
    @Override
    public ResultSet executeQuaery(String quaery) throws SQLException {
        return connection.createStatement().executeQuery(quaery);
    }

}