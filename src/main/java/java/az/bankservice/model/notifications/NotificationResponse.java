package java.az.bankservice.model.notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.az.bankservice.enumeration.NotificationType;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private Long id;
    private Long userId;
    private String message;
    private LocalDate sentDate;
    private NotificationType type;
}
