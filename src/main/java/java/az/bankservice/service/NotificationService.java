package java.az.bankservice.service;

import java.az.bankservice.model.notifications.NotificationRequest;
import java.az.bankservice.model.notifications.NotificationResponse;
import java.util.List;

public interface NotificationService {
    void create(NotificationRequest notificationRequest);

    List<NotificationResponse> getAll();

    List<NotificationResponse> getByUserId(Long userId);

    void deleteUserId(Long userId);
}
