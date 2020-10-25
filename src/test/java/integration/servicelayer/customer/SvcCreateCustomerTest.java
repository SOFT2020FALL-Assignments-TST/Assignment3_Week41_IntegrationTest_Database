package integration.servicelayer.customer;

import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import servicelayer.customer.CustomerService;
import exceptions.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
class SvcCreateCustomerTest {

    private CustomerService svc;
    private CustomerStorage storage;

    private static final int PORT = 3306;
    private static final String PASSWORD = "testuser1234";

    @Container
    public static MySQLContainer mysql = (MySQLContainer) new MySQLContainer(DockerImageName.parse("mysql"))
            .withPassword(PASSWORD)
            .withExposedPorts(PORT);

    @BeforeAll
    public void setup() {
        System.err.println("mysql created: " + mysql.isCreated());
        System.err.println("mysql running: " + mysql.isRunning());
        System.err.println("mysql host: " + mysql.getHost());
        String url = "jdbc:mysql://"+mysql.getHost()+":"+mysql.getFirstMappedPort()+"/";
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

        storage = new CustomerStorageImpl(url + db,"root", PASSWORD);
        svc = new CustomerServiceImpl(storage);
    }

    @Test
    public void mustGetCustomersFromDatabaseWhenCallingGetCustomerByFirstName() throws CustomerServiceException, SQLException {
        // Arrange
        var firstName = "John";
        var lastName = "Johnson";
        var bday = new Date(1239821l);
        var phoneNumber = "12345678";
        int id = svc.createCustomer(firstName, lastName, bday, phoneNumber);

        // Act
        var createdCustomer = svc.getCustomersByFirstName(firstName);

        // Assert
        assertEquals(3, createdCustomer.size());
    }

    @Test
    public void mustSaveCustomerToDatabaseWhenCallingCreateCustomer() throws CustomerServiceException, SQLException {
        // Arrange
        var firstName = "John";
        var lastName = "Johnson";
        var bday = new Date(1239821l);
        var phoneNumber = "12345678";
        int id = svc.createCustomer(firstName, lastName, bday, phoneNumber);

        // Act
        var createdCustomer = storage.getCustomerWithId(id);

        // Assert
        assertEquals(firstName, createdCustomer.getFirstname());
        assertEquals(lastName, createdCustomer.getLastname());
        assertEquals(phoneNumber, createdCustomer.getPhoneNumber());
    }

    @Test
    public void mustGetCustomerFromDatabaseWhenCallingGetCustomerById() throws CustomerServiceException, SQLException {
        // Arrange
        var firstName = "John";
        var lastName = "Johnson";
        var bday = new Date(1239821l);
        var phoneNumber = "12345678";
        int id = svc.createCustomer(firstName, lastName, bday, phoneNumber);

        // Act
        var createdCustomer = svc.getCustomerById(id);

        // Assert
        assertEquals(firstName, createdCustomer.getFirstname());
        assertEquals(lastName, createdCustomer.getLastname());
        assertEquals(phoneNumber, createdCustomer.getPhoneNumber());
    }
}