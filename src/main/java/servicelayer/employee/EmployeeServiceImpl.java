package servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import dto.Employee;
import dto.EmployeeCreation;
import exceptions.EmployeeServiceException;

import java.sql.SQLException;

public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeStorage employeeStorage;

    public EmployeeServiceImpl(EmployeeStorage storage) {
        this.employeeStorage = storage;
    }

    @Override
    public int createEmployee(EmployeeCreation employeeToCreate) throws EmployeeServiceException {
        try {
            return employeeStorage.createEmployee(employeeToCreate);
        } catch (SQLException throwables) {
            throw new EmployeeServiceException(throwables.getMessage());
        }
    }

    @Override
    public Employee getEmployeeById(int employeeId) throws EmployeeServiceException {
        try {
            return employeeStorage.getEmployeeWithId(employeeId);
        } catch (SQLException throwables) {
            throw new EmployeeServiceException(throwables.getMessage());
        }
    }
}
