package in.rajlabs.EmailService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * Simplified DTO for Kafka producers containing only the essential details.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailerInfoForKafka {

    private List<String> recipientsList = Collections.emptyList();
    private String mailBody;
    private String subject;

    @Override
    public String toString() {
        return "EmailerInfoForKafka{" +
                "recipientsList=" + recipientsList +
                ", mailBody='" + mailBody + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
