package dto;

import java.sql.Time;
import java.util.Date;

public class BookingCreation {
    private final Customer customer;
    private final Employee employee;
    private final Date date;
    private final Time start;
    private final Time end;

    public BookingCreation(Customer customer, Employee employee, Date date, Time start, Time end) {
        this.customer = customer;
        this.employee = employee;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Date getDate() {
        return date;
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }
}
