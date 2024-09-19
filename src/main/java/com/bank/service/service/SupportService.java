package com.bank.service.service;


import com.bank.service.model.support.EmailAnswerDto;
import com.bank.service.model.support.SupportDto;
import com.bank.service.model.support.SupportResponseDto;
import java.util.List;

public interface SupportService {
    void sendRequest(SupportDto supportDto);

    void sendResponse(Long supportID, EmailAnswerDto emailAnswerDto);

    List<SupportResponseDto> getRequests();

    List<SupportResponseDto> getUnAnsweredRequests();
}
