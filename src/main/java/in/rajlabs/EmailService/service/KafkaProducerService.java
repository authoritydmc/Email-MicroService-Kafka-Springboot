package in.rajlabs.EmailService.service;

import in.rajlabs.EmailService.dto.EmailerInfo;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> stringKafkaTemplate;
    private final KafkaTemplate<String, EmailerInfo> jsonKafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> stringKafkaTemplate,
                                KafkaTemplate<String, EmailerInfo> jsonKafkaTemplate) {
        this.stringKafkaTemplate = stringKafkaTemplate;
        this.jsonKafkaTemplate = jsonKafkaTemplate;
    }

    public void sendStringMessage(String topic, String message) {
        stringKafkaTemplate.send(topic, message);
    }

    public void sendJsonMessage(String topic, EmailerInfo emailerInfo) {
        jsonKafkaTemplate.send(topic, emailerInfo);
    }
}
