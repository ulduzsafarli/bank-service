package java.az.bankservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.az.bankservice.model.notifications.NotificationRequest;
import java.az.bankservice.model.notifications.NotificationResponse;
import java.az.bankservice.service.NotificationService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationResponse> getAll() {
        return notificationService.getAll();
    }

    @GetMapping("/{id}")
    public List<NotificationResponse> getByUserId(@PathVariable(value = "id") Long userId) {
        return notificationService.getByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody NotificationRequest notificationRequest) {
        notificationService.create(notificationRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long userId) {
        notificationService.deleteUserId(userId);
    }
}
