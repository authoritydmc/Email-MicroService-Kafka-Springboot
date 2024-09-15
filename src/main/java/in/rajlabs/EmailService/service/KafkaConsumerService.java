package in.rajlabs.EmailService.service;

import in.rajlabs.EmailService.dto.EmailerInfo;
import in.rajlabs.EmailService.util.CONSTANTS;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Kafka Listener Service to handle different message types.
 */
@Service
public class KafkaConsumerService {

    @KafkaListener(topics = CONSTANTS.KEY_KAFKA_EMAIL_TOPIC)
    public void consumeString(String message) {
        // Handle normal string messages
        System.out.println("Received string message: " + message);
    }


}
