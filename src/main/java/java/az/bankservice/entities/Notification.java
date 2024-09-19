package java.az.bankservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.az.bankservice.enumeration.NotificationType;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "notifications", schema = "public")
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private LocalDate sentDate;
    @Enumerated(EnumType.STRING)
    private NotificationType type;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}