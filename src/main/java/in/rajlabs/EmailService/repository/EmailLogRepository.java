package in.rajlabs.EmailService.repository;



import in.rajlabs.EmailService.model.EmailLogEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EmailLogRepository extends JpaRepository<EmailLogEntity, UUID> {
    @Modifying
    @Query("DELETE FROM EmailLogEntity e WHERE e.emailID IN (SELECT e2.emailID FROM EmailLogEntity e2 ORDER BY e2.emailID ASC LIMIT ?1)")
    void deleteOldestLogs(long count);

    @Transactional
    @Query("DELETE FROM EmailLogEntity e WHERE e.emailSentTime < ?1")
    void deleteLogsOlderThan(LocalDateTime cutoffDate);

    @Query("select e from EmailLogEntity e where e.isEmailsent = false and e.retryCount <= ?1")
    List<EmailLogEntity> getRetryEmailList(Integer retryCount);

    @Query("""
            select (count(e) > 0) from EmailLogEntity e
            where e.isEmailsent = true and e.emailType = ?1 and e.insertionTime between ?2 and ?3""")
    boolean isAnyAutoInventoryReportSentToday(String emailType, LocalDateTime insertionTimeStart, LocalDateTime insertionTimeEnd);

    @Query("select e from EmailLogEntity e order by e.insertionTime DESC")
    List<EmailLogEntity> findAllbyLatestOrder();




}