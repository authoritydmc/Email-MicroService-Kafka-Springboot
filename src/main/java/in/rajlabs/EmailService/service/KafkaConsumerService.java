package in.rajlabs.EmailService.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.rajlabs.EmailService.dto.EmailerInfo;
import in.rajlabs.EmailService.util.CONSTANTS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Slf4j
@Service
public class KafkaConsumerService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    EmailService emailService;
    @KafkaListener(topics = CONSTANTS.KEY_KAFKA_EMAIL_TOPIC)
    public void consumeMessage(String message) {
        try {
            // Try to parse the message as EmailerInfo
            EmailerInfo emailerInfo = objectMapper.readValue(message, EmailerInfo.class);
            log.info("Got Request to send email for {}", emailerInfo);
            emailService.sendEmail(emailerInfo);
        } catch (Exception e) {
            // If parsing fails, treat it as a normal string
           log.error("error while parsing kafka email sending message to emailer Info Class"+e.getMessage());
        }
    }
}
