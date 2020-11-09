package integration.servicelayer.booking;

import com.github.javafaker.Faker;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.*;
import exceptions.BookingServiceException;
import exceptions.CustomerServiceException;
import exceptions.EmployeeServiceException;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceImpl;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceImpl;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceImpl;
import servicelayer.notifications.NotificationsServiceImpl;
import servicelayer.notifications.SmsService;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
public class SvcCreateBookingTest {

    private BookingService svc;
    private BookingStorage storage;

    private CustomerStorage customerStorage;
    private CustomerService svcCustomer;

    private EmployeeStorage employeeStorage;
    private EmployeeService svcEmployee;

    private SmsService svcSms;

    private static final int PORT = 3306;
    private static final String PASSWORD = "testuser1234";

    Customer c = mock(Customer.class);
    Employee e = mock(Employee.class);

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

        customerStorage = new CustomerStorageImpl(url + db, "root", PASSWORD);
        svcCustomer = new CustomerServiceImpl(customerStorage);

        employeeStorage = new EmployeeStorageImpl(url + db, "root", PASSWORD);
        svcEmployee = new EmployeeServiceImpl(employeeStorage);

        svcSms = new NotificationsServiceImpl();

        storage = new BookingStorageImpl(url + db,"root", PASSWORD);
        svc = new BookingServiceImpl(storage, svcEmployee, svcCustomer, svcSms);
    }

    private void addFakeCustomer() throws SQLException {
        Faker faker = new Faker();
        CustomerCreation c = new CustomerCreation(faker.name().firstName(), faker.name().lastName(), "11122233");
        customerStorage.createCustomer(c);
    }

    private void addFakeEmployee() throws SQLException {
        Faker faker = new Faker();
        EmployeeCreation e = new EmployeeCreation(faker.name().firstName(), faker.name().lastName(), new Date());
        employeeStorage.createEmployee(e);
    }

    @Test
    public void mustSaveBookingToDatabaseWhenCallingCreateBooking() throws EmployeeServiceException, SQLException, BookingServiceException, CustomerServiceException {
        // Arrange
        when(c.getId()).thenReturn(1);
        when(e.getId()).thenReturn(1);

        // Act
        addFakeCustomer();
        addFakeEmployee();
        var createdBookingId = svc.createBooking(c.getId(), e.getId(), new Date(123456789l),
                new Time(System.currentTimeMillis()),
                new Time(System.currentTimeMillis()));

        // Assert
        assertEquals(2, createdBookingId);
    }

    @Test
    public void mustGetBookingsFromDatabaseWhenCallingGetBookingsByCustomer() throws SQLException, EmployeeServiceException, BookingServiceException, CustomerServiceException {
        // Arrange
        when(c.getId()).thenReturn(1);
        when(c.getFirstname()).thenReturn("Firstname");
        when(c.getLastname()).thenReturn("Lastname");
        when(c.getBirthDate()).thenReturn(new Date(123456789l));
        when(c.getPhoneNumber()).thenReturn("11223344");
        when(e.getId()).thenReturn(1);
        when(e.getFirstname()).thenReturn("Firstname");
        when(e.getLastname()).thenReturn("Lastname");
        when(e.getBirthdate()).thenReturn(new Date(123456789l));

        // Act
        addFakeCustomer();
        addFakeEmployee();
        var createdBookingId = storage.createBooking(new BookingCreation(new Customer(c.getId(), c.getFirstname(), c.getLastname(), c.getBirthDate(), c.getPhoneNumber()),
                        new Employee(e.getId(), e.getFirstname(), e.getLastname(), e.getBirthdate()),
                        new Date(123456789l),
                        new Time(System.currentTimeMillis()),
                        new Time(System.currentTimeMillis())
                )
        );
        Collection<Booking> bookingsByCustomer = svc.getBookingsForCustomer(c.getId());

        // Assert
        assertEquals(3, bookingsByCustomer.size());
    }

    @Test
    public void mustGetBookingsFromDatabaseWhenCallingGetBookingsByEmployee() throws SQLException, EmployeeServiceException, BookingServiceException, CustomerServiceException {
        // Arrange
        when(c.getId()).thenReturn(1);
        when(c.getFirstname()).thenReturn("Firstname");
        when(c.getLastname()).thenReturn("Lastname");
        when(c.getBirthDate()).thenReturn(new Date(123456789l));
        when(c.getPhoneNumber()).thenReturn("11223344");
        when(e.getId()).thenReturn(1);
        when(e.getFirstname()).thenReturn("Firstname");
        when(e.getLastname()).thenReturn("Lastname");
        when(e.getBirthdate()).thenReturn(new Date(123456789l));

        // Act
        addFakeCustomer();
        addFakeEmployee();
        var createdBookingId = storage.createBooking(new BookingCreation(new Customer(c.getId(), c.getFirstname(), c.getLastname(), c.getBirthDate(), c.getPhoneNumber()),
                        new Employee(e.getId(), e.getFirstname(), e.getLastname(), e.getBirthdate()),
                        new Date(123456789l),
                        new Time(System.currentTimeMillis()),
                        new Time(System.currentTimeMillis())
                )
        );
        Collection<Booking> bookingsByEmployee = svc.getBookingsForEmployee(e.getId());

        // Assert
        assertEquals(1, bookingsByEmployee.size());
    }
}
