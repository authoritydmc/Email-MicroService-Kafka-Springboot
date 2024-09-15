package in.rajlabs.EmailService.service;


import in.rajlabs.EmailService.dto.EmailInfoToEmailLogEntityMapper;
import in.rajlabs.EmailService.dto.EmailLogToEmailerInfoMapper;
import in.rajlabs.EmailService.dto.EmailerInfo;
import in.rajlabs.EmailService.model.EmailLogAttachmentEntity;
import in.rajlabs.EmailService.model.EmailLogEntity;
import in.rajlabs.EmailService.repository.EmailLogRepository;
import in.rajlabs.EmailService.util.AttachmentInfo;
import in.rajlabs.EmailService.util.EmailType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class EmailLogService {

    private final EmailLogRepository emailLogRepository;

    @Autowired
    private EmailLogAttachmentService emailLogAttachmentService;

    @Autowired
    public EmailLogService(EmailLogRepository emailLogRepository) {
        this.emailLogRepository = emailLogRepository;
    }

    // Implement methods for CRUD operations or custom logic

    public Boolean saveEmailLog(EmailerInfo emailerInfo) {

        EmailLogEntity emailLog = EmailInfoToEmailLogEntityMapper.mapEmailInfoToEmailLogEntity(emailerInfo);
        emailLogRepository.save(emailLog);
        // Check if attachments are not null and save them
        if (emailerInfo.getAttachments() != null && emailerInfo.getRetryCount() == 0) {
            for (AttachmentInfo attachment : emailerInfo.getAttachments()) {
                // Create an EmailLogAttachmentEntity and set its properties
                EmailLogAttachmentEntity emailLogAttachmentEntity = new EmailLogAttachmentEntity();
                emailLogAttachmentEntity.setAttachment_id(attachment.getAttachment_id());
                emailLogAttachmentEntity.setFileName(attachment.getFileName());
                emailLogAttachmentEntity.setContent(attachment.getContent());
                emailLogAttachmentEntity.setEmail_id(emailLog.getEmailID());

                // Save the attachment using the EmailLogAttachmentService
                emailLogAttachmentService.saveAttachment(emailLogAttachmentEntity);
            }
        }


        return true;
    }

    public EmailLogEntity getEmailLog(UUID emailID) {
        return emailLogRepository.findById(emailID).orElse(null);
    }

    public List<EmailLogEntity> getAllEmailLogs() {
        return emailLogRepository.findAllbyLatestOrder();
    }



    public List<EmailerInfo> getAllRetryEmails() {

        List<EmailLogEntity> emailLogEntities = emailLogRepository.getRetryEmailList(3);
//convert this emailLogEntites to EmailerInfo
        List<EmailerInfo> emailerInfoList = new ArrayList<>();
        for (EmailLogEntity emailLog : emailLogEntities) {
            List<EmailLogAttachmentEntity> emailLogAttachmentEntities = emailLogAttachmentService.getAllAttachmentsForEmail(emailLog.getEmailID());
            emailerInfoList.add(EmailLogToEmailerInfoMapper.mapEmailLogEntityToEmailerInfo(emailLog, emailLogAttachmentEntities));
        }

        return emailerInfoList;
    }
}