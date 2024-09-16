package pet.proOne.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.proOne.entities.CodeVerificationEntity;
@Repository
@Transactional
public interface CodeVerificationRepository extends JpaRepository<CodeVerificationEntity,Long> {
    CodeVerificationEntity findByEmail(String email);
    void deleteByEmail(String email);
}
