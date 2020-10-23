package servicelayer.employee;

public interface EmployeeService {
    int createEmployee(Employee employee);
    Employee getEmployeeById(int employeeId);

}
