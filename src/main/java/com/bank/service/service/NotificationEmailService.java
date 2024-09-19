package com.bank.service.service;

import com.bank.service.model.support.EmailAnswerDto;

public interface NotificationEmailService {

    void sendNotificationEmail(String to, EmailAnswerDto emailAnswerDto);

}
