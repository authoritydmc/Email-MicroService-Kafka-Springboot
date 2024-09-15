package in.rajlabs.EmailService.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaStringConsumerService {

    @KafkaListener(topics = "SEND_EMAILS", groupId = "string-group")
    public void consumeString(String message) {
        // Handle string messages
        System.out.println("Received string message: " + message);
    }
}
