package com.bank.service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.bank.service.entities.Support;
import com.bank.service.exception.custom.NotFoundException;
import com.bank.service.mapper.SupportMapper;
import com.bank.service.model.support.EmailAnswerDto;
import com.bank.service.model.support.SupportDto;
import com.bank.service.model.support.SupportResponseDto;
import com.bank.service.repository.SupportRepository;
import com.bank.service.service.SupportService;
import com.bank.service.service.util.SupportEmailService;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {

    private final SupportRepository supportRepository;
    private final SupportMapper supportMapper;
    private final SupportEmailService supportEmailService;

    @Override
    public void sendRequest(SupportDto supportDto) {
        log.info("Processing support request: {}", supportDto);

        supportRepository.save(supportMapper.toEntity(supportDto));
        supportEmailService.sendSupportEmail(supportDto);
    }

    @Override
    public void sendResponse(Long supportID, EmailAnswerDto emailAnswerDto) {
        log.info("Sending answer for support request: {}", emailAnswerDto);
        Support support = supportRepository.findById(supportID)
                .orElseThrow(() -> new NotFoundException("Support message not found with ID " + supportID));

        supportEmailService.sendResponseEmail(support.getEmail(), emailAnswerDto);
        support.setAnswered(true);
        supportRepository.save(support);
    }

    @Override
    public List<SupportResponseDto> getRequests() {
        log.info("Retrieving all support requests");
        return supportRepository.findAll().stream().map(supportMapper::toResponseList).toList();
    }

    @Override
    public List<SupportResponseDto> getUnAnsweredRequests() {
        log.info("Retrieving all unanswered support requests");
        return supportRepository.findUnAnsweredRequests().stream().map(supportMapper::toResponseList).toList();
    }
}
