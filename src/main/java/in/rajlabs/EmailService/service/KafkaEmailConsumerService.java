package in.rajlabs.EmailService.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaEmailConsumerService {

    @KafkaListener(topics = "SEND_EMAILS", groupId = "email-group")
    public void listen(String message) {
        // Process the message
       log.info("Received message: " + message);

        // Add your logic here to handle the email sending
        // For example, parse the message and send an email using your EmailService
    }
}
