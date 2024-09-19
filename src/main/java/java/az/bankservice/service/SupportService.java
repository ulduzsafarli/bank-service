package java.az.bankservice.service;


import java.az.bankservice.model.support.EmailAnswerDto;
import java.az.bankservice.model.support.SupportDto;
import java.az.bankservice.model.support.SupportResponseDto;
import java.util.List;

public interface SupportService {
    void sendRequest(SupportDto supportDto);

    void sendResponse(Long supportID, EmailAnswerDto emailAnswerDto);

    List<SupportResponseDto> getRequests();

    List<SupportResponseDto> getUnAnsweredRequests();
}
