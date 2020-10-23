package datalayer.booking;

public interface BookingStorage {
    int createBooking(Booking booking);
    Collection<Booking> getBookingsForCustomer(int customerId);
}
