package datalayer.employee;

public interface EmployeeStorage {
    int createEmployee(Employee employee);
    Collection<Employee> getEmployeeWithId(int employeeId);
}
