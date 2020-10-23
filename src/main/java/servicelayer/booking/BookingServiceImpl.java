package servicelayer.booking;

import java.util.Date;

public class BookingServiceImpl implements BookingService{
    @Override
    public int createBooking(int customerId, int employeeId, Date date, int start, int end) {
        return 0;
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) {
        return null;
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) {
        return null;
    }
}
