package in.rajlabs.EmailService.controller.api.v1;

import in.rajlabs.EmailService.dto.EmailerInfo;
import in.rajlabs.EmailService.service.EmailService;
import in.rajlabs.EmailService.service.KafkaProducerService;
import in.rajlabs.EmailService.util.CONSTANTS;
import in.rajlabs.EmailService.util.EmailType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/email")
@Slf4j
public class EmailController {

    @Autowired
    private final JavaMailSender javaMailSender;
    @Autowired
    private EmailService emailService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public EmailController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    /**
     * Endpoint for sending an email with an attachment.
     *
     * @param toEmail Recipient's email address received from the client
     * @param file    The attached file received from the client
     * @return ResponseEntity with the status of the email sending operation
     */
    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmailWithAttachment(
            @RequestParam("to") String toEmail,
            @RequestParam("file") MultipartFile file) throws IOException {
        try {
            // Create the email message with attachment
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set the email details
            helper.setTo(toEmail);
            helper.setSubject("Test email with attachment");
            helper.setText("Please find the attachment for test.");

            // Attach the file received from the client
            helper.addAttachment(file.getOriginalFilename(), file);

            // Send the email
            javaMailSender.send(message);

            log.info("Email sent successfully to {}", toEmail);
            return ResponseEntity.ok("Email sent successfully!");
        } catch (MessagingException e) {
            log.error("Failed to send email to {}", toEmail, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email.");
        }
    }

    /**
     * Endpoint for sending a test email.
     *
     * @param toEmail Recipient's email address received from the client
     * @return ResponseEntity with the status of the email sending operation
     */
    @GetMapping("/test")
    public ResponseEntity<String> test(@RequestParam("to") String toEmail) {
        try {
            // Create a simple email message
            SimpleMailMessage message = new SimpleMailMessage();

            // Set the email details
            message.setTo(toEmail);
            message.setSubject("Test Email");
            message.setText("This is a test email.");

            // Send the email
            javaMailSender.send(message);

            log.info("Test email sent successfully to {}", toEmail);
            return ResponseEntity.ok("Test email sent successfully!");
        } catch (MailException e) {
            log.error("Failed to send test email to {}", toEmail, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send test email. " + e.getMessage());
        }
    }
    @GetMapping("/kafka/test")
    public ResponseEntity<String> kafkatest(@RequestParam("to") String toEmail) {
        try {
        kafkaProducerService.sendStringMessage(CONSTANTS.KEY_KAFKA_EMAIL_TOPIC,"this is normal test email string which need to be send ");
            return new ResponseEntity<>("Dummy email sent successfully.", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to send test email to {}", toEmail, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send test email. " + e.getMessage());
        }
    }

    @GetMapping("/kafka/testNew")
    public ResponseEntity<String> testThymelefedVersion(@RequestParam("to") String toEmail) {
        try {

            EmailerInfo emailInfo = new EmailerInfo();
            emailInfo.setSubject("Dummy Email Subject");
            emailInfo.setMailBody("Hello, this is a dummy email.");
            emailInfo.setEmailType(EmailType.TEST);
            emailInfo.setRecipientsList(List.of(toEmail));
            kafkaProducerService.sendJsonMessage(CONSTANTS.KEY_KAFKA_EMAIL_TOPIC,emailInfo);
            return new ResponseEntity<>("kafka message produced to send email", HttpStatus.OK);
        } catch (MailException e) {
            log.error("Failed to send test email to {}", toEmail, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send test email. " + e.getMessage());
        }
    }
    @GetMapping("/testNew")
    public ResponseEntity<String> normalthymeleafemail(@RequestParam("to") String toEmail) {
        try {

            EmailerInfo emailInfo = new EmailerInfo();
            emailInfo.setSubject("Dummy Email Subject");
            emailInfo.setMailBody("Hello, this is a dummy email.");
            emailInfo.setEmailType(EmailType.TEST);
            emailInfo.setRecipientsList(List.of(toEmail));
        emailService.sendEmail(emailInfo);
            return new ResponseEntity<>("dummy email send email", HttpStatus.OK);
        } catch (MailException e) {
            log.error("Failed to send test email to {}", toEmail, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send test email. " + e.getMessage());
        }
    }
}
