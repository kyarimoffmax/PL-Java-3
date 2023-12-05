import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ConnectionDB {

    Connection getConnection();
    void execute(String quaery) throws SQLException;

    ResultSet executeQuaery(String quaery) throws SQLException;

}
