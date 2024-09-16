package pet.proOne.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.proOne.entities.NotificationHistoryEntity;
@Repository
@Transactional
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistoryEntity,Long> {
}
