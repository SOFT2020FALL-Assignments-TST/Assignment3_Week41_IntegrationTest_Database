package datalayer.employee;

import dto.Employee;
import dto.EmployeeCreation;

import java.sql.SQLException;

public interface EmployeeStorage {
    int createEmployee(EmployeeCreation employeeToCreate) throws SQLException;
    Employee getEmployeeWithId(int employeeId) throws SQLException;
}
