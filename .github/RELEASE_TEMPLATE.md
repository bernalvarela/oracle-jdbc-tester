## What's New

<!-- Describe the main changes in this release -->

### Features
-

### Bug Fixes
-

### Improvements
-

### Dependencies
-

---

## How to Use

Download the JAR file that matches your Oracle Database version:

**For Oracle 23ai/23c:** Use `jdbc-tester-X.X.X-23.26.0.0.0.jar` (latest) or another 23.x version

**For Oracle 21c:** Use `jdbc-tester-X.X.X-21.20.0.0.jar` (latest) or another 21.x version

**For Oracle 19c:** Use `jdbc-tester-X.X.X-19.29.0.0.jar` (latest) or another 19.x version

**For Oracle 18c:** Use `jdbc-tester-X.X.X-18.15.0.0.jar`

**For Oracle 12c:** Use `jdbc-tester-X.X.X-12.2.0.1.jar`

### Running the Application

```bash
java -jar jdbc-tester-X.X.X-<driver-version>.jar -u <username> -p <password> -h <host> -po <port> -s <SID>
```

**Parameters:**
- `-u` / `--username` - Database username (required)
- `-p` / `--password` - Database password (required)
- `-h` / `--host` - Database host (optional, default: localhost)
- `-po` / `--port` - Database port (optional, default: 1521)
- `-s` / `--sid` - Database SID (optional, default: xe)

---

## Documentation

- [BUILD_GUIDE.md](https://github.com/bernalvarela/oracle-jdbc-tester/blob/master/BUILD_GUIDE.md) - How to build from source
- [AVAILABLE_VERSIONS.md](https://github.com/bernalvarela/oracle-jdbc-tester/blob/master/AVAILABLE_VERSIONS.md) - All 73 available driver versions
- [Workflow Documentation](https://github.com/bernalvarela/oracle-jdbc-tester/blob/master/.github/workflows/README.md) - Release automation details

---

## Technical Details

- **Java Version:** Java 8
- **Build Tool:** Maven
- **Package Type:** Fat JAR (all dependencies included)
- **Main Class:** dev.bernalvarela.JdbcTester

---

<!-- The workflow will automatically append the list of available driver versions below -->
