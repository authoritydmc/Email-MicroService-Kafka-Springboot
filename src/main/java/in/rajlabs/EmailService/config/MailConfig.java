package in.rajlabs.EmailService.config;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.SimpleMailMessage;

import java.io.InputStream;
import java.util.Properties;
@Slf4j
@Configuration
public class MailConfig {



    @Bean
    @Profile("default")
    public JavaMailSender defaultMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        String emailUsername = System.getenv("EMAIL_USERNAME");
        String emailPassword = System.getenv("EMAIL_PASSWORD");
        mailSender.setUsername(emailUsername != null ? emailUsername : "default-email@zohomail.com");
        mailSender.setPassword(emailPassword != null ? emailPassword : "default-password");
//        log.info("Set username "+mailSender.getUsername());
//        log.info("Password"+mailSender.getPassword());
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
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
                log.info("Development environment email sending:");
                log.info("To: {}", (Object) simpleMessage.getTo());
                log.info("Subject: {}", simpleMessage.getSubject());
                log.info("Body: {}", simpleMessage.getText());
                log.info("Development environment: Email sending mocked!");
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
