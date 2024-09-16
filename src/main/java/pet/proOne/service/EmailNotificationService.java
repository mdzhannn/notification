package pet.proOne.service;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import pet.proOne.entities.CodeVerificationEntity;
import pet.proOne.entities.NotificationHistoryEntity;
import pet.proOne.exception.NoSuchEmailException;
import pet.proOne.model.NotificationStatus;
import pet.proOne.model.NotificationType;
import pet.proOne.repository.CodeVerificationRepository;
import pet.proOne.repository.NotificationHistoryRepository;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {
    private final CodeVerificationRepository notificRepository;
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final JavaMailSender javaMailSender;
    private final String from = "gapparov.m22@gmail.com";
    public void sendVerificationCode(String email){
        var verificationCode = generateCode();

        String message = "Код подтверждение";
        String subject = "Код подтверждение";
        notificRepository.deleteByEmail(email);

        NotificationHistoryEntity notificationHistoryEntity = NotificationHistoryEntity.builder()
                .email(email)
                .creatAt(LocalDateTime.now())
                .verificationCode(generateCode())
                .status(NotificationStatus.SENT)
                .type(NotificationType.CODE)
                .message("Пару слов")
                .build();
        try {
            sendEmail(email,message + verificationCode, subject);
            notificRepository.save(new CodeVerificationEntity(email, verificationCode));
            notificationHistoryRepository.save(notificationHistoryEntity);
        }catch (Exception e){
            notificationHistoryEntity.setStatus(NotificationStatus.SEND_ERROR);
            notificationHistoryRepository.save(notificationHistoryEntity);
        }
    }

    @Transactional
    public void sendMessage(String email, String message) {

        String subject = "Уведомление";
        NotificationHistoryEntity notificationHistoryEntity = NotificationHistoryEntity.builder()
                .email("gapprov900@gmail.com")
                .creatAt(LocalDateTime.now())
                .verificationCode(generateCode())
                .status(NotificationStatus.SENT)
                .type(NotificationType.MESSAGE)
                .message("Пару слов туда а")
                .build();


        try {
            sendEmail(email, message, subject);
            notificationHistoryRepository.save(notificationHistoryEntity);
        } catch (Exception e) {

            notificationHistoryEntity.setStatus(NotificationStatus.SEND_ERROR);
            notificationHistoryRepository.save(notificationHistoryEntity);
        }

    }

    @Transactional
    public Boolean verify(String email, String code) {
        var verification = notificRepository.findByEmail(email);

        if (verification == null) {
            throw new NoSuchEmailException(email);
        } else {
            if (code.equals(verification.getVerificationCode())) {
//                notificRepository.deleteByEmail(email);
                sendMessage(email, "Вы успешно подтвердили ваш аккаунт!");
                return true;
            }
            return false;

        }
    }

    public void sendEmail(String email , String message , String subject){
        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setFrom(new InternetAddress(from, "DikiyStore"));
            mimeMessage.setContent(message, "text/html; charset=UTF-8");
            mimeMessage.setSubject(subject, "UTF-8");
            mimeMessage.setSentDate(new Date());
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mimeMessage.setHeader("Content-Type", "text/html; charset=UTF-8");
        };
        javaMailSender.send(preparator);

    }

    private String generateCode() {
        return RandomStringUtils.randomNumeric(6);
    }

}
