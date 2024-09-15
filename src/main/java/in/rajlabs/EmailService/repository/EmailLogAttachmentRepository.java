package in.rajlabs.EmailService.repository;


import in.rajlabs.EmailService.model.EmailLogAttachmentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EmailLogAttachmentRepository extends JpaRepository<EmailLogAttachmentEntity, UUID> {
    @Query("select e from EmailLogAttachmentEntity e where e.email_id = :email_id")
    List<EmailLogAttachmentEntity> findByEmail_ID(@Param("email_id") UUID email_id);


    @Transactional
    void deleteByInsertionTimeBefore(LocalDateTime cutoffTime);
}