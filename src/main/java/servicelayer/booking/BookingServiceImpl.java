package servicelayer.booking;

import datalayer.booking.BookingStorage;
import dto.*;
import exceptions.BookingServiceException;
import exceptions.CustomerServiceException;
import exceptions.EmployeeServiceException;
import servicelayer.customer.CustomerService;
import servicelayer.employee.EmployeeService;
import servicelayer.notifications.SmsService;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.util.Date;

public class BookingServiceImpl implements BookingService, SmsService {

    private BookingStorage bookingStorage;
    private EmployeeService employeeService;
    private CustomerService customerService;

    public BookingServiceImpl(BookingStorage bookingStorage, EmployeeService employeeService, CustomerService customerService) {
        this.bookingStorage = bookingStorage;
        this.employeeService = employeeService;
        this.customerService = customerService;
    }

    @Override
    public int createBooking(int customerId, int employeeId, Date date, Time start, Time end) throws EmployeeServiceException, CustomerServiceException, BookingServiceException {
        Employee e = employeeService.getEmployeeById(employeeId);
        Customer c = customerService.getCustomerById(customerId);
        try {
            return bookingStorage.createBooking(new BookingCreation(c, e, date, start, end));
        } catch (SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) throws BookingServiceException {
        try {
            return bookingStorage.getBookingsForCustomer(customerId);
        } catch (SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) throws BookingServiceException {
        try {
            return bookingStorage.getBookingsForCustomer(employeeId);
        } catch (SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
    }

    @Override
    public boolean sendSms(SmsMessage message) {
        return false;
    }
}
