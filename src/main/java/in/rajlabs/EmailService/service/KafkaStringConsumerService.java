package in.rajlabs.EmailService.service;

import in.rajlabs.EmailService.util.CONSTANTS;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaStringConsumerService {

    @KafkaListener(topics = CONSTANTS.KEY_KAFKA_EMAIL_TOPIC, groupId = "string-group")
    public void consumeString(String message) {
        // Handle string messages
        System.out.println("Received string message: " + message);
    }
}
