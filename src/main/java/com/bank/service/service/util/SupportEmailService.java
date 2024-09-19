package com.bank.service.service.util;

import com.bank.service.model.support.EmailAnswerDto;
import com.bank.service.model.support.SupportDto;

public interface SupportEmailService {
    void sendSupportEmail(SupportDto supportDto);
    void sendResponseEmail(String to, EmailAnswerDto emailAnswerDto);
}
