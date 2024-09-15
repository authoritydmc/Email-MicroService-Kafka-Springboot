package in.rajlabs.EmailService.config;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.SimpleMailMessage;

import java.io.InputStream;
import java.util.Properties;

@Configuration
public class MailConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailConfig.class);

    @Bean
    @Profile("default")
    public JavaMailSender defaultJavaMailSender() {
        return new JavaMailSender() {
            @Override
            public MimeMessage createMimeMessage() {
                return null;
            }

            @Override
            public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
                return null;
            }

            @Override
            public void send(MimeMessage... mimeMessages) throws MailException {

            }

            @Override
            public void send(SimpleMailMessage simpleMessage) {
                // Log the email details for testing
                System.out.println("Default environment email sending:");
                System.out.println("To: " + simpleMessage.getTo());
                System.out.println("Subject: " + simpleMessage.getSubject());
                System.out.println("Body: " + simpleMessage.getText());
                System.out.println("Default environment: Email sending simulated!");
            }

            @Override
            public void send(SimpleMailMessage... simpleMessages) {
                for (SimpleMailMessage message : simpleMessages) {
                    send(message);
                }
            }

        };
    }

    @Bean
    @Profile("dev")
    public JavaMailSender devJavaMailSender() {
        return new JavaMailSender() {
            @Override
            public MimeMessage createMimeMessage() {
                return null;
            }

            @Override
            public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
                return null;
            }

            @Override
            public void send(MimeMessage... mimeMessages) throws MailException {

            }

            @Override
            public void send(SimpleMailMessage simpleMessage) {
                LOGGER.info("Development environment email sending:");
                LOGGER.info("To: {}", (Object) simpleMessage.getTo());
                LOGGER.info("Subject: {}", simpleMessage.getSubject());
                LOGGER.info("Body: {}", simpleMessage.getText());
                LOGGER.info("Development environment: Email sending mocked!");
            }

            @Override
            public void send(SimpleMailMessage... simpleMessages) {
                for (SimpleMailMessage message : simpleMessages) {
                    send(message);
                }
            }
        };
    }

    @Bean
    @Profile("prod")
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        String emailUsername = System.getenv("EMAIL_USERNAME");
        String emailPassword = System.getenv("EMAIL_PASSWORD");

        mailSender.setUsername(emailUsername != null ? emailUsername : "default-email@gmail.com");
        mailSender.setPassword(emailPassword != null ? emailPassword : "default-password");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }
}
