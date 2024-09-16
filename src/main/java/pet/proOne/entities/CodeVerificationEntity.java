package pet.proOne.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "code_verification",schema = "notification")
@NoArgsConstructor
@Data
public class CodeVerificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "verification_code")
    private String verificationCode;
    public CodeVerificationEntity(String email ,String verificationCode){
        this.email = email;
        this.verificationCode = verificationCode;
    }
}
