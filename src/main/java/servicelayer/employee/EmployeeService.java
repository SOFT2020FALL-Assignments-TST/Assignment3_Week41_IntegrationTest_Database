package servicelayer.employee;

import dto.Employee;
import dto.EmployeeCreation;
import exceptions.EmployeeServiceException;

public interface EmployeeService {
    int createEmployee(EmployeeCreation employeeToCreate) throws EmployeeServiceException;
    Employee getEmployeeById(int employeeId) throws EmployeeServiceException;

}
