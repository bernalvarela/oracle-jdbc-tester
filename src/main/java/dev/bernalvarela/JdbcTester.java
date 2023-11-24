package dev.bernalvarela;

import oracle.jdbc.OracleConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.Callable;

@CommandLine.Command(
    name = "Oracle jdbc tester",
    version = "1.5.0",
    description = "Checks an Oracle database server connection"
)
public class JdbcTester implements Callable<Integer> {

    final static Logger LOG = LoggerFactory.getLogger(JdbcTester.class);

    @CommandLine.Option(names = {"-u", "--username"}, description = "Username to connect to database", required = true)
    private String username;

    @CommandLine.Option(
        names = {"-p", "--password"},
        description = "Password to connect to database",
        required = true,
        defaultValue = "password"
    )
    private String password;

    @CommandLine.Option(
        names = {"-h", "--host"},
        description = "Database host. Default value localhost.",
        defaultValue = "localhost"
    )
    private String host;

    @CommandLine.Option(
        names = {"-po", "--port"},
        description = "Database port. Default value 1521.",
        defaultValue = "1521"
    )
    private String port;

    @CommandLine.Option(names = {"-s", "--sid"}, description = "Database sid. Default value xe.", defaultValue = "xe")
    private String sid;

    public static void main(String... args) {
        int exitCode = new CommandLine(new JdbcTester()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {

        Class.forName("oracle.jdbc.driver.OracleDriver");

        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        properties.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CONNECT_TIMEOUT, "2000");

        try {
            LOG.info("****** Starting JDBC Connection test *******");
            String sqlQuery = "SELECT * FROM v$version";

            Connection conn = DriverManager.getConnection(
                String.format("jdbc:oracle:thin:@%s:%s/%s", host, port, sid),
                properties
            );
            conn.setAutoCommit(false);
            Statement statement = conn.createStatement();
            LOG.info("Running SQL query: [{}]", sqlQuery);
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            LOG.info("Result of SQL query");
            while (resultSet.next()) {
                LOG.info("{}", resultSet.getString(1));
            }

            statement.close();
            conn.close();

            LOG.info("JDBC connection test successful!");
        } catch (SQLException ex) {
            LOG.error("Exception occurred connecting to database: {}", ex.getMessage());
            return -1;
        }
        return 0;
    }
}
