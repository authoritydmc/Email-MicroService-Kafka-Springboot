package in.rajlabs.EmailService.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "email_log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailLogEntity {

    @Id
    private UUID emailID;

    private String senderName = "ISUZU_AUTO_MAILER";
    private String senderEmailAddress = "inventorydata.scanned@isuzu-india.com";
    private String emailType;
    private String recipientsList;
    private String mailBody;
    private String subject;
    private String ccList;
    private String bccList;
    private Integer attachmentCount;
    private boolean isEmailsent = false;
    private LocalDateTime emailSentTime;
    private LocalDateTime insertionTime;

    private LocalDateTime updatedTime;
    private String status;
    private Integer retryCount=0;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        insertionTime = now;
        updatedTime = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "EmailLogEntity{" +
                "emailID=" + emailID +
                ", senderName='" + senderName + '\'' +
                ", senderEmailAddress='" + senderEmailAddress + '\'' +
                ", emailType='" + emailType + '\'' +
                ", recipientsList='" + recipientsList + '\'' +
                ", mailBody='" + mailBody + '\'' +
                ", subject='" + subject + '\'' +
                ", ccList='" + ccList + '\'' +
                ", bccList='" + bccList + '\'' +
                ", attachmentCount=" + attachmentCount +
                ", isEmailsent=" + isEmailsent +
                ", emailSentTime=" + emailSentTime +
                ", insertionTime=" + insertionTime +
                ", updatedTime=" + updatedTime +
                ", status='" + status + '\'' +
                ", retryCount=" + retryCount +
                '}';
    }
}