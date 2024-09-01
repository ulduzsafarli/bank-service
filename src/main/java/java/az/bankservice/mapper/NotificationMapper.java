package java.az.bankservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.az.bankservice.entities.Notification;
import java.az.bankservice.model.notifications.NotificationRequest;
import java.az.bankservice.model.notifications.NotificationResponse;


@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NotificationMapper {

    @Mapping(source = "notification.user.id", target = "userId")
    NotificationResponse toDto(Notification notification);

    @Mapping(source = "notificationRequest.userId", target = "user.id")
    Notification toEntity(NotificationRequest notificationRequest);
}
