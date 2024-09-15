package in.rajlabs.EmailService.dto;


import in.rajlabs.EmailService.model.EmailLogAttachmentEntity;
import in.rajlabs.EmailService.model.EmailLogEntity;
import in.rajlabs.EmailService.util.AttachmentInfo;
import in.rajlabs.EmailService.util.EmailType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class EmailLogToEmailerInfoMapper {

    public static EmailerInfo mapEmailLogEntityToEmailerInfo(EmailLogEntity emailLogEntity, List<EmailLogAttachmentEntity> attachments) {
        EmailerInfo emailerInfo = new EmailerInfo();
        emailerInfo.setEmailID(emailLogEntity.getEmailID());
        emailerInfo.setSender_name(emailLogEntity.getSenderName());
        emailerInfo.setSender_emailAddress(emailLogEntity.getSenderEmailAddress());
        emailerInfo.setEmailType(EmailType.valueOf(emailLogEntity.getEmailType()));
        emailerInfo.setMailBody(emailLogEntity.getMailBody());
        emailerInfo.setSubject(emailLogEntity.getSubject());
        emailerInfo.setRetryCount(emailLogEntity.getRetryCount());
        emailerInfo.setInsertionTime(emailLogEntity.getInsertionTime());

        // Handle recipientsList, ccList, and bccList
        if (emailLogEntity.getRecipientsList() != null) {
            String[] recipientsArray = emailLogEntity.getRecipientsList().split(",");
            emailerInfo.setRecipientsList(Arrays.asList(recipientsArray));
        }
        if (emailLogEntity.getCcList() != null) {
            String[] ccArray = emailLogEntity.getCcList().split(",");
            log.info("CC array length" + ccArray.length);

            List<String> addresses = Arrays.stream(ccArray).filter(s -> !s.isEmpty()).toList();

            if (!addresses.isEmpty()) {
                emailerInfo.setCcList(addresses);
            } else {
                emailerInfo.setCcList(null);
            }
        }



        if (emailLogEntity.getBccList() != null) {
            String[] bccArray = emailLogEntity.getBccList().split(",");
            List<String> addresses = Arrays.stream(bccArray).filter(s -> !s.isEmpty()).toList();


            log.info("BCC array length" + addresses.size());

            if (!addresses.isEmpty())
                emailerInfo.setBccList(Arrays.asList(bccArray));
            else
                emailerInfo.setBccList(null);
        }

        // Attachments
        if (attachments != null && !attachments.isEmpty()) {
            emailerInfo.setAttachments(new ArrayList<>());

            for (EmailLogAttachmentEntity attachmentEntity : attachments) {
                AttachmentInfo attachmentInfo = new AttachmentInfo();
                attachmentInfo.setAttachment_id(attachmentEntity.getAttachment_id());
                attachmentInfo.setFileName(attachmentEntity.getFileName());
                attachmentInfo.setContent(attachmentEntity.getContent());
                emailerInfo.getAttachments().add(attachmentInfo);
            }
        }

        emailerInfo.setEmailsent(emailLogEntity.isEmailsent());
        emailerInfo.setEmailSentTime(emailLogEntity.getEmailSentTime());
        emailerInfo.setStatus(emailLogEntity.getStatus());

        return emailerInfo;
    }
}