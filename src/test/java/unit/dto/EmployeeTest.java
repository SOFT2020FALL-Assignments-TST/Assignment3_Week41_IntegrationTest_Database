package unit.dto;

import dto.Employee;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmployeeTest {

    @Test
    public void testEmployeeDTO(){
        int id = 1;
        String firstName = "Morten";
        String lastName = "Feldt";
        Date birthDate = new Date();

        Employee e = new Employee(id,firstName, lastName, birthDate);

        assertNotNull(e);
        assertEquals(e.getId(), id);
        assertEquals(e.getFirstname(), firstName);
        assertEquals(e.getLastname(), lastName);
        assertEquals(e.getBirthdate(), birthDate);
    }
}
