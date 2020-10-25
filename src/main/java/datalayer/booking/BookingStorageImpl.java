package datalayer.booking;

import dto.Booking;
import dto.BookingCreation;
import dto.Customer;
import dto.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class BookingStorageImpl implements BookingStorage{
    private String connectionString;
    private String username, password;

    public BookingStorageImpl(String conStr, String user, String pass) {
        connectionString = conStr;
        username = user;
        password = pass;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Override
    public int createBooking(BookingCreation bookingCreation) throws SQLException {
        var sql = "insert into Bookings(customerId, employeeId, date, start, end) values (?, ?, ?, ?, ?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, bookingCreation.getCustomer().getId());
            stmt.setInt(2, bookingCreation.getEmployee().getId());
            stmt.setDate(3, new java.sql.Date(bookingCreation.getDate().getTime()));
            stmt.setTime(4, bookingCreation.getStart());
            stmt.setTime(5, bookingCreation.getEnd());

            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                int newId = resultSet.getInt(1);
                return newId;
            }
        }
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException {
        var sql = "SELECT b.id, b.date, b.start, b.end," +
                "c.id AS c_id, c.firstname AS c_firstname, c.lastname AS c_lastname, c.birthdate AS c_birthdate, c.phonenumber AS c_phonenumber," +
                "e.id AS e_id, e.firstname AS e_firstname, e.lastname AS e_lastname, e.birthdate AS e_birthdate " +
                "FROM Bookings b " +
                "INNER JOIN Customers c ON c.id = b.customerId " +
                "INNER JOIN Employees e ON e.id = b.employeeId " +
                "WHERE b.customerId = ?";
        try (
                var con = getConnection();
                var stmt = con.prepareStatement(sql)
        ) {
            stmt.setInt(1, customerId);
            var results = new ArrayList<Booking>();

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int b_id = resultSet.getInt("id");
                java.util.Date b_date = resultSet.getDate("date");
                Time b_start = resultSet.getTime("start");
                Time b_end = resultSet.getTime("end");
                int c_id = resultSet.getInt("c_id");
                String c_firstname = resultSet.getString("c_firstname");
                String c_lastname = resultSet.getString("c_lastname");
                Date c_birthDate = resultSet.getDate("c_birthdate");
                String c_phoneNumber = resultSet.getString("c_phonenumber");
                int e_id = resultSet.getInt("e_id");
                String e_firstname = resultSet.getString("e_firstname");
                String e_lastname = resultSet.getString("e_lastname");
                Date e_birthDate = resultSet.getDate("e_birthdate");
                Customer c = new Customer(c_id, c_firstname, c_lastname, c_birthDate, c_phoneNumber);
                Employee e = new Employee(e_id, e_firstname, e_lastname, e_birthDate);
                Booking b = new Booking(b_id, c, e, b_date, b_start, b_end);
                results.add(b);
            }

            return results;
        }
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) throws SQLException {
        var sql = "SELECT b.id, b.date, b.start, b.end," +
                "c.id AS c_id, c.firstname AS c_firstname, c.lastname AS c_lastname, c.birthdate AS c_birthdate, c.phonenumber AS c_phonenumber," +
                "e.id AS e_id, e.firstname AS e_firstname, e.lastname AS e_lastname, e.birthdate AS e_birthdate " +
                "FROM Bookings b " +
                "INNER JOIN Customers c ON c.id = b.customerId " +
                "INNER JOIN Employees e ON e.id = b.employeeId " +
                "WHERE b.employeeId = ?";
        try (
                var con = getConnection();
                var stmt = con.prepareStatement(sql)
        ) {
            stmt.setInt(1, employeeId);
            var results = new ArrayList<Booking>();

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int b_id = resultSet.getInt("id");
                java.util.Date b_date = resultSet.getDate("date");
                Time b_start = resultSet.getTime("start");
                Time b_end = resultSet.getTime("end");
                int c_id = resultSet.getInt("c_id");
                String c_firstname = resultSet.getString("c_firstname");
                String c_lastname = resultSet.getString("c_lastname");
                Date c_birthDate = resultSet.getDate("c_birthdate");
                String c_phoneNumber = resultSet.getString("c_phonenumber");
                int e_id = resultSet.getInt("e_id");
                String e_firstname = resultSet.getString("e_firstname");
                String e_lastname = resultSet.getString("e_lastname");
                Date e_birthDate = resultSet.getDate("e_birthdate");
                Customer c = new Customer(c_id, c_firstname, c_lastname, c_birthDate, c_phoneNumber);
                Employee e = new Employee(e_id, e_firstname, e_lastname, e_birthDate);
                Booking b = new Booking(b_id, c, e, b_date, b_start, b_end);
                results.add(b);
            }

            return results;
        }
    }
}
