package java.az.bankservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.az.bankservice.exception.custom.NotFoundException;
import java.az.bankservice.mapper.NotificationMapper;
import java.az.bankservice.model.notifications.NotificationRequest;
import java.az.bankservice.model.notifications.NotificationResponse;
import java.az.bankservice.repository.NotificationRepository;
import java.az.bankservice.service.EmailSendingService;
import java.az.bankservice.service.NotificationService;
import java.az.bankservice.service.UserService;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final EmailSendingService emailSendingService;

    private static final String NOT_FOUND = "Notification %s by ID not found.";

    @Override
    @Transactional
    public void create(NotificationRequest notificationRequest) {
        log.info("Creating notification for user {}", notificationRequest.getUserId());
        var user = userService.getById(notificationRequest.getUserId());
        var notification = notificationMapper.toEntity(notificationRequest);
        notification.setSentDate(LocalDate.now());
        notificationRepository.save(notification);
        var emailForm = new EmailAnswerDto(notificationRequest.getMessage());
        emailSendingService.sendNotificationEmail(user.getEmail(), emailForm);
        log.info("Successfully create notification for user {}", notificationRequest.getUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getAll() {
        log.info("Receiving all notifications");
        var notifications = notificationRepository.findAll()
                .stream().map(notificationMapper::toDto).toList();
        log.info("Successfully receive all notifications");
        return notifications;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getByUserId(Long userId) {
        log.info("Receiving all notifications for user {}", userId);
        var notifications = notificationRepository.findByUserId(userId);
        if (notifications.isEmpty()) {
            throw new NotFoundException(String.format(NOT_FOUND, userId));
        }
        return notifications.stream().map(notificationMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void deleteUserId(Long userId) {
        log.info("Removing all notifications for user {}", userId);
        notificationRepository.deleteByUserId(userId);
    }

}
