package unit.dto;

import dto.Booking;
import dto.Customer;
import dto.Employee;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Time;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {

    @Mock Customer customerMock;
    @Mock Employee employeeMock;

    @Test
    public void testBookingDTO(){
        int id = 1;
        Date date = new Date();
        Time start = new java.sql.Time(123456789999l);
        Time end = new java.sql.Time(123456789999l);

        Booking b = new Booking(id, customerMock, employeeMock, date, start, end);

        assertNotNull(b);
        assertEquals(b.getId(), id);
        assertEquals(b.getDate(), date);
        assertEquals(b.getStart(), start);
        assertEquals(b.getEnd(), end);
    }
}
