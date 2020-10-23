package servicelayer.booking;

import java.util.Date;

public interface BookingService {
    int createBooking(int customerId, int employeeId, Date date, int start, int end);
    Collection<Booking> getBookingsForCustomer(int customerId);
    Collection<Booking> getBookingsForEmployee(int employeeId);
}
