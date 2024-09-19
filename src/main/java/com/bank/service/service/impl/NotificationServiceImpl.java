package com.bank.service.service.impl;

import com.bank.service.service.NotificationEmailService;
import com.bank.service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.service.exception.custom.NotFoundException;
import com.bank.service.mapper.NotificationMapper;
import com.bank.service.model.notifications.NotificationRequest;
import com.bank.service.model.notifications.NotificationResponse;
import com.bank.service.model.support.EmailAnswerDto;
import com.bank.service.repository.NotificationRepository;
import com.bank.service.service.UserService;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final NotificationEmailService notificationEmailService;

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
        notificationEmailService.sendNotificationEmail(user.getEmail(), emailForm);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getAll() {
        log.info("Receiving all notifications");
        return notificationRepository.findAll()
                .stream().map(notificationMapper::toDto).toList();
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
