package in.rajlabs.EmailService.service;

import in.rajlabs.EmailService.dto.EmailerInfo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaJsonConsumerService {

    @KafkaListener(topics = "SEND_EMAILS", groupId = "json-group")
    public void consumeJson(EmailerInfo emailerInfo) {
        // Handle EmailerInfo JSON messages
        System.out.println("Received EmailerInfo: " + emailerInfo);
    }
}
