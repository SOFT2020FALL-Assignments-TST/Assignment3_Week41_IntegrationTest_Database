package servicelayer.customer;

import dto.Customer;
import exceptions.CustomerServiceException;

import java.util.Collection;
import java.util.Date;

public interface CustomerService {
    int createCustomer(String firstName, String lastName, Date birthdate, String phoneNumber) throws CustomerServiceException;
    Customer getCustomerById(int id) throws CustomerServiceException;
    Collection<Customer> getCustomersByFirstName(String firstName) throws CustomerServiceException;
}
