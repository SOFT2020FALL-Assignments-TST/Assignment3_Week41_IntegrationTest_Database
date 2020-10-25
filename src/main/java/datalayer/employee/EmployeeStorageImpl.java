package datalayer.employee;

import dto.Employee;
import dto.EmployeeCreation;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeStorageImpl implements EmployeeStorage {
    private String connectionString;
    private String username, password;

    public EmployeeStorageImpl(String conStr, String user, String pass) {
        connectionString = conStr;
        username = user;
        password = pass;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Override
    public int createEmployee(EmployeeCreation employeeToCreate) throws SQLException {
        var sql = "insert into Employees(firstname, lastname, birthdate) values (?, ?, ?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, employeeToCreate.getFirstname());
            stmt.setString(2, employeeToCreate.getLastname());
            stmt.setDate(3, new java.sql.Date(employeeToCreate.getBirthdate().getTime()));

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
    public Employee getEmployeeWithId(int employeeId) throws SQLException {
        try (var con = getConnection();
             var stmt = con.createStatement()) {
            var results = new ArrayList<Employee>();

            ResultSet resultSet = stmt.executeQuery("select ID, firstname, lastname, birthdate from Employees");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                Date birthDate = resultSet.getDate("birthdate");
                Employee e = new Employee(id, firstname, lastname, birthDate);
                return e;
            }

            return null;
        }
    }
}
