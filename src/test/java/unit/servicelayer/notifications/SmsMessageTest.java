package unit.servicelayer.notifications;

import dto.SmsMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.notifications.NotificationsServiceImpl;
import servicelayer.notifications.SmsService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SmsMessageTest {

    // SUT (System Under Test)
    private SmsService smsService;

    @BeforeAll
    public void setup(){
        smsService = new NotificationsServiceImpl();
    }

    @Test
    public void testSuccesfullySmsMessage() {
        // Arrange
        String message = "Test";
        String phoneNumber = "12345678";
        SmsMessage sms = new SmsMessage(phoneNumber, message);

        // Act
        var status = smsService.sendSms(sms);

        // Assert
        assertTrue(status);
    }

    @Test
    public void testErrorSmsMessageMessageNULL() {
        // Arrange
        String message = null;
        String phoneNumber = "12345678";
        SmsMessage sms = new SmsMessage(phoneNumber, message);

        // Act
        var status = smsService.sendSms(sms);

        // Assert
        assertFalse(status);
    }

    @Test
    public void testErrorSmsMessageMessageEmptyString() {
        // Arrange
        String message = "";
        String phoneNumber = "12345678";
        SmsMessage sms = new SmsMessage(phoneNumber, message);

        // Act
        var status = smsService.sendSms(sms);

        // Assert
        assertFalse(status);
    }

    @Test
    public void testErrorSmsMessagePhonenumberNULL() {
        // Arrange
        String message = "Test";
        String phoneNumber = null;
        SmsMessage sms = new SmsMessage(phoneNumber, message);

        // Act
        var status = smsService.sendSms(sms);

        // Assert
        assertFalse(status);
    }

    @Test
    public void testErrorSmsMessagePhonenumberEmptyString() {
        // Arrange
        String message = "Test";
        String phoneNumber = "";
        SmsMessage sms = new SmsMessage(phoneNumber, message);

        // Act
        var status = smsService.sendSms(sms);

        // Assert
        assertFalse(status);
    }

    @Test
    public void testErrorSmsMessageMessageAndPhonenumberBothNULL() {
        // Arrange
        String message = null;
        String phoneNumber = null;
        SmsMessage sms = new SmsMessage(phoneNumber, message);

        // Act
        var status = smsService.sendSms(sms);

        // Assert
        assertFalse(status);
    }
}
