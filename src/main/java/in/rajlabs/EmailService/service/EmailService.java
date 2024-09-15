package in.rajlabs.EmailService.service;

import in.rajlabs.EmailService.dto.EmailerInfo;
import in.rajlabs.EmailService.util.AttachmentInfo;
import in.rajlabs.EmailService.util.EmailType;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailService {
    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;


    @Autowired
    private EmailLogService emailLogService;
    @Autowired
    private TemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Async
    public void sendEmail(EmailerInfo emailInfo) {
        LOG.info("Sending email with attachments...");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            if (emailInfo.getRecipientsList().isEmpty())
            {
                emailInfo.setEmailType(EmailType.EMPTY_DO_NOT_SEND_EMAIL);
            }
            helper = new MimeMessageHelper(message, true);

            if (emailInfo.getEmailType().equals(EmailType.EMPTY_DO_NOT_SEND_EMAIL)) {

                LOG.error("Err: Do not send email if emailType not set in EmailerInfo object .");
                return;
            }





            LOG.info("Recipients list :" + emailInfo.getRecipientsList() + " size: " + emailInfo.getRecipientsList().size());


            if (emailInfo.getRecipientsList().isEmpty()) {
                LOG.error("Recipient List is empty .NO receiver for the email");
                return;

            }
            helper.setTo(emailInfo.getRecipientsList().toArray(new String[0]));
            helper.setSubject(emailInfo.getSubject());

            Context context = new Context();
            context.setVariable("headerText", getHeaderText(emailInfo.getEmailType()));
            context.setVariable("emailContent", emailInfo.getMailBody());


            if (emailInfo.getCcList() != null) {
                helper.setCc(emailInfo.getCcList().toArray(new String[0]));
            }
            if (emailInfo.getBccList() != null) {
                helper.setBcc(emailInfo.getBccList().toArray(new String[0]));
            }
            // Attachments
            if (emailInfo.getAttachments() != null) {
                for (AttachmentInfo attachment : emailInfo.getAttachments()) {
                    // Create a DataSource from the byte[] content
                    if (attachment != null) {

                        InputStreamSource inputStreamSource = new ByteArrayResource(attachment.getContent());
                        helper.addAttachment(attachment.getFileName(), inputStreamSource);
                    }


                }
            }
            // Render the templates with dynamic data
            String contentTemplate = templateEngine.process("default_email_template", context);

            helper.setText(contentTemplate, true);

            javaMailSender.send(message);
            LOG.info("Email sent successfully.");
            emailInfo.setEmailsent(true);
            emailInfo.setEmailSentTime(LocalDateTime.now());
            emailInfo.setStatus("Mail send successfully");

            emailLogService.saveEmailLog(emailInfo);


        } catch (Exception e) {
            emailInfo.setEmailsent(false);
            emailInfo.setStatus("error occurred while mail sending : " + e.getMessage());
            emailLogService.saveEmailLog(emailInfo);

            LOG.error("Failed to send email.", e);
        }
    }

    private String getHeaderText(EmailType emailType) {

        if (emailType.equals(EmailType.TEST))
            return "TEST EMAIL";

        return "Email Service Rajlabs";
    }


    @Async
    public void sendEmailWithoutAttachments(EmailerInfo emailInfo) {
        LOG.info("Sending email without attachments...");

        emailInfo.setAttachments(null); // Remove attachments
        sendEmail(emailInfo);
    }


}
