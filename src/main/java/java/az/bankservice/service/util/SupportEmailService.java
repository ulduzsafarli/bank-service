package java.az.bankservice.service.util;

import java.az.bankservice.model.support.EmailAnswerDto;
import java.az.bankservice.model.support.SupportDto;

public interface SupportEmailService {
    void sendSupportEmail(SupportDto supportDto);
    void sendResponseEmail(String to, EmailAnswerDto emailAnswerDto);
}
