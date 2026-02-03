# oracle-jdbc-tester

[![Maven Build](https://github.com/bernalvarela/oracle-jdbc-tester/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/bernalvarela/oracle-jdbc-tester/actions/workflows/maven-publish.yml)

A simple command line application to test JDBC connection to Oracle Database.

## How to run

### Option 1: Download Pre-built JARs (Recommended)

Download the JAR file from the [releases](https://github.com/bernalvarela/oracle-jdbc-tester/releases) page. Each release includes **10 different JARs**, each compiled with a different Oracle JDBC driver version (from Oracle 12c to 23ai). Choose the one that matches your Oracle Database version.

### Option 2: Build from Source

Clone this repository and run the Maven command to build the executable JAR file:

```bash
# Build with default driver version (23.26.0.0.0)
mvn clean package

# Build with specific driver version
mvn clean package -Pojdbc-21.20.0.0
``` 

Execute the JAR file with the following 5 parameters:

* -u / --username. Database username. Required.
* -p / --password. Database password. Required.
* -h / --host. Database host. Optional. localhost as default value.
* -po / --port. Database port. Optional. 1521 as default value.
* -s / --sid. Database SID. Optional. xe as default value.

```sh
java -jar target/jdbc-tester-1.5.1-23.26.0.0.0.jar -u <username> -p <password> -h <host> -po <port> -s <SIS>
```

## Multiple Oracle JDBC Driver Versions

This project supports building with **73 different versions** of the Oracle JDBC driver (ojdbc8), from Oracle Database 12c through 23ai.

### Quick Start

```bash
# Build with default version (23.26.0.0.0)
mvn clean package

# Build with specific version using pre-configured profiles
mvn clean package -Pojdbc-21.20.0.0
mvn clean package -Pojdbc-19.29.0.0

# Build with any version using custom profile
mvn clean package -Pcustom -Dojdbc.version=21.18.0.0

# Build multiple versions at once
./build-all-versions.sh    # Linux/Mac
build-all-versions.bat     # Windows
```

### Available Versions

- **23.x series** (Oracle 23ai/23c): 9 versions
- **21.x series** (Oracle 21c): 24 versions
- **19.x series** (Oracle 19c): 36 versions
- **18.x series** (Oracle 18c): 2 versions
- **12.x series** (Oracle 12c): 1 version

For complete documentation on building with different driver versions:
- [BUILD_GUIDE.md](BUILD_GUIDE.md) - Complete build guide
- [AVAILABLE_VERSIONS.md](AVAILABLE_VERSIONS.md) - List of all 73 available versions
- [.github/workflows/README.md](.github/workflows/README.md) - GitHub Actions workflow documentation

## Automated Releases

Every release automatically builds and publishes **10 JARs** with different Oracle JDBC driver versions through GitHub Actions. The workflow compiles the project in parallel for maximum efficiency and uploads all artifacts to the release page.

**Versions included in releases:**
- Oracle 23ai: 23.26.0.0.0 (latest), 23.9.0.25.07, 23.3.0.23.09
- Oracle 21c: 21.20.0.0 (latest), 21.13.0.0, 21.1.0.0
- Oracle 19c: 19.29.0.0 (latest), 19.21.0.0
- Oracle 18c: 18.15.0.0 (latest)
- Oracle 12c: 12.2.0.1

See [Workflow Documentation](.github/workflows/README.md) for details on how releases are created.

### Secure your credentials

When running this tool ad-hoc, a good security practice would be read the DB username and password into a variable by using `read` command in Linux (or similar) and then execute the JAR file.

This prevents DB credentials being stored in `~/.bash_history`.


```sh
java -jar target/jdbc-tester-1.5.1-23.26.0.0.0.jar -u "$DB_USER" -p "$DB_PASS" -h <host> -po <port> -s <SIS>
```

## How it works

The application connects to the Oracle database and executes a single SQL query: `SELECT * FROM v$version` and prints the output. 

If it cannot connect for whatever reason, it will fail by logging an error message.

There is a hardcoded connection timeout set to 2 seconds.

## License

(The MIT License)

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
