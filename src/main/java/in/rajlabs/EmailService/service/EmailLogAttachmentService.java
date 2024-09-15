package in.rajlabs.EmailService.service;


import in.rajlabs.EmailService.model.EmailLogAttachmentEntity;
import in.rajlabs.EmailService.repository.EmailLogAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmailLogAttachmentService {
    private final EmailLogAttachmentRepository attachmentRepository;

    @Autowired
    public EmailLogAttachmentService(EmailLogAttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public void saveAttachment(EmailLogAttachmentEntity attachment) {
        attachmentRepository.save(attachment);
    }

    public List<EmailLogAttachmentEntity> getAllAttachmentsForEmail(UUID emailLogId) {
        // Query the database to retrieve attachments associated with a specific email log ID
        return attachmentRepository.findByEmail_ID(emailLogId);
    }

    // Add other methods for attachment management as needed
}