package in.rajlabs.EmailService.service;

import in.rajlabs.EmailService.dto.EmailerInfo;
import in.rajlabs.EmailService.dto.EmailerInfoForKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> stringKafkaTemplate;
    private final KafkaTemplate<String, EmailerInfoForKafka> jsonKafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> stringKafkaTemplate,
                                KafkaTemplate<String, EmailerInfoForKafka> jsonKafkaTemplate) {
        this.stringKafkaTemplate = stringKafkaTemplate;
        this.jsonKafkaTemplate = jsonKafkaTemplate;
    }

    public void sendStringMessage(String topic, String message) {
        stringKafkaTemplate.send(topic, message);
    }

    public void sendJsonMessage(String topic, EmailerInfoForKafka emailerInfo) {
        jsonKafkaTemplate.send(topic, emailerInfo);
    }
}
