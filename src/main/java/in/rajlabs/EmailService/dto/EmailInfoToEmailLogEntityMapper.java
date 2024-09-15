package in.rajlabs.EmailService.dto;


import in.rajlabs.EmailService.model.EmailLogEntity;

import java.util.List;


public class EmailInfoToEmailLogEntityMapper {

    public static EmailLogEntity mapEmailInfoToEmailLogEntity(EmailerInfo emailInfo) {
        EmailLogEntity emailLogEntity = new EmailLogEntity();
        emailLogEntity.setEmailID(emailInfo.getEmailID());
        emailLogEntity.setSenderName(emailInfo.getSender_name());
        emailLogEntity.setSenderEmailAddress(emailInfo.getSender_emailAddress());
        emailLogEntity.setEmailType(emailInfo.getEmailType().toString());
        emailLogEntity.setInsertionTime(emailInfo.getInsertionTime());
        // Handle null recipients list gracefully
        List<String> recipientsList = emailInfo.getRecipientsList();
        if (recipientsList != null) {
            emailLogEntity.setRecipientsList(String.join(",", recipientsList));
        } else {
            emailLogEntity.setRecipientsList("");
        }

        emailLogEntity.setMailBody(emailInfo.getMailBody());
        emailLogEntity.setSubject(emailInfo.getSubject());
        emailLogEntity.setRetryCount(emailInfo.getRetryCount());
        // Handle null ccList and bccList gracefully
        List<String> ccList = emailInfo.getCcList();
        if (ccList != null) {
            emailLogEntity.setCcList(String.join(",", ccList));
        } else {
            emailLogEntity.setCcList("");
        }

        List<String> bccList = emailInfo.getBccList();
        if (bccList != null) {
            emailLogEntity.setBccList(String.join(",", bccList));
        } else {
            emailLogEntity.setBccList("");
        }

        emailLogEntity.setAttachmentCount(emailInfo.getAttachments() != null ? emailInfo.getAttachments().size() : 0);
        emailLogEntity.setEmailsent(emailInfo.isEmailsent());
        emailLogEntity.setEmailSentTime(emailInfo.getEmailSentTime());
        emailLogEntity.setStatus(emailInfo.getStatus());

        return emailLogEntity;
    }


}