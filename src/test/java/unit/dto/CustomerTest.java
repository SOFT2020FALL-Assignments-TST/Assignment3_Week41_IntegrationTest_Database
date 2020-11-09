package unit.dto;

import dto.Customer;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerTest {

    @Test
    public void testCustomerDTO(){
        int id = 1;
        String firstName = "Morten";
        String lastName = "Feldt";
        Date birthDate = new Date();
        String phoneNumber = "12345678";

        Customer c = new Customer(id,firstName, lastName, birthDate, phoneNumber);

        assertNotNull(c);
        assertEquals(c.getId(), id);
        assertEquals(c.getFirstname(), firstName);
        assertEquals(c.getLastname(), lastName);
        assertEquals(c.getBirthDate(), birthDate);
        assertEquals(c.getPhoneNumber(), phoneNumber);
    }

}
