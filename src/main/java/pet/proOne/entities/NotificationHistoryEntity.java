package pet.proOne.entities;
import jakarta.persistence.*;
import lombok.*;
import pet.proOne.model.NotificationStatus;
import pet.proOne.model.NotificationType;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_history" ,schema = "notification")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NotificationHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "created_at")
    private LocalDateTime creatAt;
    @Column(name = "verification_code")
    private String verificationCode;
    @Column(name = "message")
    private String message;
    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status")
    private NotificationStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type")
    private NotificationType type;
}
