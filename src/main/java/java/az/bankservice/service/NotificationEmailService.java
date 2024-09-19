package java.az.bankservice.service;

import java.az.bankservice.model.support.EmailAnswerDto;

public interface NotificationEmailService {

    void sendNotificationEmail(String to, EmailAnswerDto emailAnswerDto);

}
