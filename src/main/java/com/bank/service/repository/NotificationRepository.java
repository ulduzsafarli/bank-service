package com.bank.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.service.entities.Notification;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM Notification notif WHERE notif.user.id = :userId")
    int deleteByUserId(@Param("userId") Long userId);
}
