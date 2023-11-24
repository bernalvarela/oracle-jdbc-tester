# oracle-jdbc-tester

[![Maven Build](https://github.com/bernalvarela/oracle-jdbc-tester/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/bernalvarela/oracle-jdbc-tester/actions/workflows/maven-publish.yml)

A simple command line application to test JDBC connection to Oracle Database.

## How to run

Clone this repository and then run below Maven command to build the executable JAR file.

```
mvn clean package
```

Alternatively, download the JAR file from [release](https://github.com/bernalvarela/oracle-jdbc-tester/releases) page. 

Execute the JAR file with the following 5 parameters:

* -u / --username. Database username. Required.
* -p / --password. Database password. Required.
* -h / --host. Database host. Optional. localhost as default value.
* -po / --port. Database port. Optional. 1521 as default value.
* -s / --sid. Database SID. Optional. xe as default value.

```sh
java -jar target/jdbc-tester-1.5.0-jar-with-dependencies.jar -u <username> -p <password> -h <host> -po <port> -s <SIS>
```

### Secure your credentials

When running this tool ad-hoc, a good security practice would be read the DB username and password into a variable by using `read` command in Linux (or similar) and then execute the JAR file.

This prevents DB credentials being stored in `~/.bash_history`.


```sh
java -jar target/jdbc-tester-1.5.0-jar-with-dependencies.jar -u "$DB_USER" -p "$DB_PASS" -h <host> -po <port> -s <SIS>
```

## How it works

The application connects to the Oracle database and executes a single SQL query: `SELECT * FROM v$version` and prints the output. 

If it cannot connect for whatever reason, it will fail by logging an error message.

There is a hardcoded connection timeout set to 2 seconds.

## License

(The MIT License)

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
