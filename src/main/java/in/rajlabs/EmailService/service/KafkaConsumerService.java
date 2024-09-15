package in.rajlabs.EmailService.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.rajlabs.EmailService.dto.EmailerInfo;
import in.rajlabs.EmailService.dto.EmailerInfoForKafka;
import in.rajlabs.EmailService.util.CONSTANTS;
import in.rajlabs.EmailService.util.EmailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = CONSTANTS.KEY_KAFKA_EMAIL_TOPIC)
    public void consumeMessage(String message) {
        try {
            // Try to parse the message as EmailerInfoForKafka
            EmailerInfoForKafka emailerInfoForKafka = objectMapper.readValue(message, EmailerInfoForKafka.class);
            log.info("Got request to send email for: {}", emailerInfoForKafka);

            // Convert EmailerInfoForKafka to EmailerInfo
            EmailerInfo emailerInfo = EmailMapper.convertToEmailerInfo(emailerInfoForKafka);
            emailService.sendEmail(emailerInfo);

        } catch (Exception e) {
            // Handle exceptions and log errors
            log.error("Error while processing Kafka message: {}", e.getMessage());
        }
    }
}
