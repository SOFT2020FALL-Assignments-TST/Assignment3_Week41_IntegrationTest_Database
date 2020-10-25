package servicelayer.notifications;

import dto.SmsMessage;

public class NotificationsServiceImpl implements SmsService{
    @Override
    public boolean sendSms(SmsMessage message) {
        if(message.getRecipient() == null || message.getMessage() == null){
            return false;
        }
        if(message.getRecipient().equals("") || message.getMessage().equals("")){
            return false;
        }
        return true;
    }
}
