package pet.proOne.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.proOne.exception.NoSuchEmailException;
import pet.proOne.service.EmailNotificationService;
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/email/send")
public class EmailNotificationController {
    private final EmailNotificationService notificationService;

    @PostMapping(path = "/code")
    public void sendVerificationCode(@RequestParam(name = "email")String email){
        notificationService.sendVerificationCode(email);
    }
    @PostMapping(path = "/message")
    public void sendMessage(@RequestParam(name = "email")String email ,@RequestParam(name = "message")String message){
        notificationService.sendMessage(email, message);
    }
    @GetMapping(path = "/verify")
    public ResponseEntity<Boolean> verify(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "code") String code) {

        try {
            // Возвращаем результат верификации
            return ResponseEntity.ok(notificationService.verify(email, code));
        } catch (NoSuchEmailException e) {
            // Обрабатываем исключение, если email не найден
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(false); // Можно также вернуть сообщение, а не просто false
        } catch (Exception e) {
            // Обрабатываем другие возможные ошибки
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(false);
        }
    }
}
