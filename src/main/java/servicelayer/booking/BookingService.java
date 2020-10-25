package servicelayer.booking;

import dto.Booking;
import exceptions.BookingServiceException;
import exceptions.CustomerServiceException;
import exceptions.EmployeeServiceException;

import java.sql.Time;
import java.util.Collection;
import java.util.Date;

public interface BookingService {
    int createBooking(int customerId, int employeeId, Date date, Time start, Time end) throws EmployeeServiceException, CustomerServiceException, BookingServiceException;
    Collection<Booking> getBookingsForCustomer(int customerId) throws BookingServiceException;
    Collection<Booking> getBookingsForEmployee(int employeeId) throws BookingServiceException;
}
