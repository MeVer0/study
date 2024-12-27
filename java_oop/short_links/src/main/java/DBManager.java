import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBManager {
    String host;
    String port;
    String dbName;
    String user;
    String password;
    String connectionUrl;

    public DBManager(String host, String port, String dbName, String user, String password) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.user = user;
        this.password = password;
        this.connectionUrl = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;
    }



    class DBQuery{

        public

    };

};
