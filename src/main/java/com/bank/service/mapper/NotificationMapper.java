package com.bank.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.bank.service.entities.Notification;
import com.bank.service.model.notifications.NotificationRequest;
import com.bank.service.model.notifications.NotificationResponse;


@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NotificationMapper {

    @Mapping(source = "notification.user.id", target = "userId")
    NotificationResponse toDto(Notification notification);

    @Mapping(source = "notificationRequest.userId", target = "user.id")
    Notification toEntity(NotificationRequest notificationRequest);
}
