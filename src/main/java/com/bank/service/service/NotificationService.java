package com.bank.service.service;

import com.bank.service.model.notifications.NotificationRequest;
import com.bank.service.model.notifications.NotificationResponse;
import java.util.List;

public interface NotificationService {
    void create(NotificationRequest notificationRequest);

    List<NotificationResponse> getAll();

    List<NotificationResponse> getByUserId(Long userId);

    void deleteUserId(Long userId);
}
