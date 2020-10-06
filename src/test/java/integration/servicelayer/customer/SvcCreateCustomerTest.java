package integration.servicelayer.customer;

import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
class SvcCreateCustomerTest {

    private CustomerService svc;
    private CustomerStorage storage;

    private final int PORT = 33071;
    private final String PASSWORD = "testuser1234";
    @Container
    public MySQLContainer mysql = (MySQLContainer) new MySQLContainer(DockerImageName.parse("mysql"))
            .withUsername("root")
            .withPassword(PASSWORD)
            .withExposedPorts(PORT)
            .waitingFor(new WaitAllStrategy());
//    public GenericContainer mysql = new GenericContainer(DockerImageName.parse("mysql"))
//            .withExposedPorts(PORT)
//            .withEnv("MYSQL_ROOT_PASSWORD", PASSWORD);

    @BeforeAll
    public void setup() {
        System.err.println("mysql created: " + mysql.isCreated());
        System.err.println("mysql running: " + mysql.isRunning());
        String url = "jdbc:mysql://localhost:"+PORT+"/";
        String db = "DemoApplicationTest";
        Flyway flyway = new Flyway(
                new FluentConfiguration()
                        .schemas(db)
                        .defaultSchema(db)
                        .createSchemas(true)
                        .target("3")
                        .dataSource(url, "root", PASSWORD)
        );
        flyway.migrate();

        storage = new CustomerStorageImpl(url + db,"root", PASSWORD);
        svc = new CustomerServiceImpl(storage);
    }

    @Test
    public void mustSaveCustomerToDatabaseWhenCallingCreateCustomer() throws CustomerServiceException, SQLException {
        // Arrange
        var firstName = "John";
        var lastName = "Johnson";
        var bday = new Date(1239821l);
        int id = svc.createCustomer(firstName, lastName, bday);

        // Act
        var createdCustomer = storage.getCustomerWithId(id);

        // Assert
        assertEquals(firstName, createdCustomer.getFirstname());
        assertEquals(lastName, createdCustomer.getLastname());
    }
}
