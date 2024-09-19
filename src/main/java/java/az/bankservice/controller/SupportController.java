package java.az.bankservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.az.bankservice.model.support.EmailAnswerDto;
import java.az.bankservice.model.support.SupportDto;
import java.az.bankservice.model.support.SupportResponseDto;
import java.az.bankservice.service.SupportService;
import java.util.List;

@RestController
@RequestMapping("/support")
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;

    @PostMapping("/request")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendRequest(@Valid @RequestBody SupportDto supportDto) {
        supportService.sendRequest(supportDto);
    }

    @PostMapping("/respond/{id}")
    public void sendResponse(@PathVariable Long id, @Valid @RequestBody EmailAnswerDto emailAnswerDto) {
        supportService.sendResponse(id, emailAnswerDto);
    }

    @GetMapping("/requests")
    public List<SupportResponseDto> getRequests(){
        return supportService.getRequests();
    }

    @GetMapping("/unanswered-requests")
    public List<SupportResponseDto> getUnAnsweredRequests(){
        return supportService.getUnAnsweredRequests();
    }
}