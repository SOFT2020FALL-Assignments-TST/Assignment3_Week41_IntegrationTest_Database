package integration.datalayer.employee;

import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Employee;
import dto.EmployeeCreation;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
@Tag("integration")
public class CreateEmployeeTest {
    private EmployeeStorage employeeStorage;

    private static final int PORT = 3306;
    private static final String PASSWORD = "testuser1234";

    @Container
    public static MySQLContainer mysql = (MySQLContainer) new MySQLContainer(DockerImageName.parse("mysql"))
            .withPassword(PASSWORD)
            .withExposedPorts(PORT);

    @BeforeAll
    public void setup() throws SQLException {
        System.err.println("mysql created: " + mysql.isCreated());
        System.err.println("mysql running: " + mysql.isRunning());
        System.err.println("mysql host: " + mysql.getHost());
        String url = "jdbc:mysql://" + mysql.getHost() + ":" + mysql.getFirstMappedPort() + "/";
        String db = "DemoApplicationTest";
        Flyway flyway = new Flyway(
                new FluentConfiguration()
                        .schemas(db)
                        .defaultSchema(db)
                        .createSchemas(true)
                        .target("4")
                        .dataSource(url, "root", PASSWORD)
        );
        flyway.migrate();

        employeeStorage = new EmployeeStorageImpl(url + db, "root", PASSWORD);
    }

    @Test
    public void testCreateEmployeeInDB() throws SQLException{
        // Arrange
        int id = employeeStorage.createEmployee(new EmployeeCreation("Morten", "Feldt", new Date(12345678l)));

        // Act
        Employee employeeCreated = employeeStorage.getEmployeeWithId(id);

        // Assert
        assertEquals(id, employeeCreated.getId());
        assertEquals("Morten", employeeCreated.getFirstname());
        assertEquals("Feldt", employeeCreated.getLastname());
    }

}
