# Multi-Version Build Guide

This project is configured to generate multiple JARs, each with a different version of the Oracle JDBC driver (ojdbc8).

## Version Summary

- **73 versions** available in Maven Central
- **30 versions** pre-configured as profiles in `pom.xml`
- **All versions** can be compiled using the `custom` profile

To see the complete list of available versions, check [AVAILABLE_VERSIONS.md](AVAILABLE_VERSIONS.md).

## Building a Specific Version

### Using Pre-configured Profiles

```bash
# Default version (23.26.0.0.0 - Oracle 23ai latest)
mvn clean package

# 23.x series (Oracle 23ai/23c)
mvn clean package -Pojdbc-23.26.0.0.0
mvn clean package -Pojdbc-23.9.0.25.07
mvn clean package -Pojdbc-23.3.0.23.09

# 21.x series (Oracle 21c)
mvn clean package -Pojdbc-21.20.0.0
mvn clean package -Pojdbc-21.13.0.0
mvn clean package -Pojdbc-21.1.0.0

# 19.x series (Oracle 19c)
mvn clean package -Pojdbc-19.29.0.0
mvn clean package -Pojdbc-19.21.0.0
mvn clean package -Pojdbc-19.3.0.0

# 18.x series (Oracle 18c)
mvn clean package -Pojdbc-18.15.0.0

# 12.x series (Oracle 12c)
mvn clean package -Pojdbc-12.2.0.1
```

### Using the Custom Profile (For any version)

To compile with **any version** of the driver (including those not listed as profiles):

```bash
# General syntax
mvn clean package -Pcustom -Dojdbc.version=<VERSION>

# Examples
mvn clean package -Pcustom -Dojdbc.version=21.18.0.0
mvn clean package -Pcustom -Dojdbc.version=19.27.0.0
mvn clean package -Pcustom -Dojdbc.version=23.8.0.25.04
```

The generated JAR will have the format: `jdbc-tester-1.5.1-<driver-version>.jar`

**Examples of generated names:**
- `jdbc-tester-1.5.1-23.26.0.0.0.jar`
- `jdbc-tester-1.5.1-21.18.0.0.jar`
- `jdbc-tester-1.5.1-19.29.0.0.jar`

## Building Multiple Versions

### Using the Provided Scripts

To generate 10 JARs with the most representative versions of each series:

#### On Windows:
```cmd
build-all-versions.bat
```

#### On Linux/Mac:
```bash
./build-all-versions.sh
```

**Versions compiled by the scripts:**
1. 23.26.0.0.0 (23ai - Latest)
2. 23.9.0.25.07
3. 23.3.0.23.09
4. 21.20.0.0 (21c - Latest)
5. 21.13.0.0
6. 21.1.0.0
7. 19.29.0.0 (19c - Latest)
8. 19.21.0.0
9. 18.15.0.0 (18c - Latest)
10. 12.2.0.1 (12c)

The JARs will be generated in the `target/` directory.

### Manual Multi-Version Compilation

You can also manually compile multiple versions in sequence:

```bash
# Windows (PowerShell)
mvn clean package -Pojdbc-23.26.0.0.0; mvn clean package -Pojdbc-21.20.0.0; mvn clean package -Pojdbc-19.29.0.0

# Linux/Mac
mvn clean package -Pojdbc-23.26.0.0.0 && mvn clean package -Pojdbc-21.20.0.0 && mvn clean package -Pojdbc-19.29.0.0
```

## Adding New Driver Versions

### As a Profile in pom.xml

To add a new version as a permanent profile, edit the `pom.xml`:

```xml
<profile>
    <id>ojdbc-<VERSION></id>
    <properties>
        <oracle.ojdbc8.version><VERSION></oracle.ojdbc8.version>
        <driver.version.classifier>-<VERSION></driver.version.classifier>
    </properties>
</profile>
```

### Using the Custom Profile (Recommended)

You don't need to modify the `pom.xml`. Simply use:

```bash
mvn clean package -Pcustom -Dojdbc.version=<NUEVA_VERSION>
```

## Verifying Driver Version in JAR

To verify which version of the Oracle JDBC driver is included in a JAR:

```bash
# Extract and view driver properties
unzip -p target/jdbc-tester-1.5.1-23.26.0.0.0.jar META-INF/MANIFEST.MF

# View included Oracle classes
jar -tf target/jdbc-tester-1.5.1-23.26.0.0.0.jar | grep oracle/jdbc
```

## Project Structure

```
oracle-jdbc-tester/
├── pom.xml                      # 30 pre-configured profiles + custom
├── build-all-versions.bat       # Windows script to compile 10 versions
├── build-all-versions.sh        # Linux/Mac script to compile 10 versions
├── BUILD_GUIDE.md               # This guide
├── AVAILABLE_VERSIONS.md        # Complete list of 73 versions
├── README.md                    # General project documentation
└── target/                      # Compiled JARs
```

## Version Compatibility

| Series | Oracle DB Version | Driver Versions | Minimum Java |
|-------|------------------|------------------|-------------|
| 23.x  | 23ai (23c)       | 23.2.0.0 - 23.26.0.0.0 | Java 8 |
| 21.x  | 21c              | 21.1.0.0 - 21.20.0.0 | Java 8 |
| 19.x  | 19c              | 19.3.0.0 - 19.29.0.0 | Java 8 |
| 18.x  | 18c              | 18.3.0.0 - 18.15.0.0 | Java 8 |
| 12.x  | 12c              | 12.2.0.1 | Java 8 |

## Technical Features

- **Java:** All versions are compiled with Java 8
- **JAR Type:** Fat JAR (includes all dependencies)
- **Plugin:** maven-assembly-plugin with jar-with-dependencies
- **Main Class:** dev.bernalvarela.JdbcTester
- **Included Dependencies:**
  - Log4j 2.22.0
  - PicoCLI 4.7.5
  - Oracle JDBC Driver (variable depending on profile)

## Troubleshooting

### Error: Driver version not found

If you get an error indicating that the driver version is not found:

1. Verify that the version exists in Maven Central: https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc8/
2. Make sure to write the version correctly (including all dots)
3. Clean Maven cache: `mvn clean -U`

### Multiple JARs in target/

If you have multiple old JARs in `target/`, clean it before compiling:

```bash
mvn clean
# Or manually
rm -rf target/*.jar  # Linux/Mac
del target\*.jar     # Windows
```

## Additional Resources

- **Maven Central - ojdbc8:** https://repo1.maven.org/maven2/com/oracle/database/jdbc/ojdbc8/
- **Oracle JDBC Downloads:** https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html
- **Oracle Maven Central Guide:** https://www.oracle.com/database/technologies/maven-central-guide.html

---

For the complete list of 73 available versions, see [AVAILABLE_VERSIONS.md](AVAILABLE_VERSIONS.md).
