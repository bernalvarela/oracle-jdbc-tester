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
    version = "1.6.0",
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
        names = {"--url"},
        description = "Database url. host:port:/service_name or host:port:SID",
        required = true
    )
    private String url;

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
                String.format("jdbc:oracle:thin:@%s", url),
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
