package in.rajlabs.EmailService.util;

import in.rajlabs.EmailService.dto.EmailerInfo;
import in.rajlabs.EmailService.dto.EmailerInfoForKafka;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class EmailMapper {

    /**
     * Converts EmailerInfoForKafka to EmailerInfo.
     *
     * @param emailerInfoForKafka The simplified DTO containing essential email details.
     * @return The full EmailerInfo object.
     */
    public static EmailerInfo convertToEmailerInfo(EmailerInfoForKafka emailerInfoForKafka) {
        // Create a new EmailerInfo object with default values
        EmailerInfo emailerInfo = new EmailerInfo();

        // Set fields with values from EmailerInfoForKafka or default values
        emailerInfo.setSubject(emailerInfoForKafka.getSubject() != null ? emailerInfoForKafka.getSubject() : "Dummy Email Subject");
        emailerInfo.setMailBody(emailerInfoForKafka.getMailBody() != null ? emailerInfoForKafka.getMailBody() : "Hello, this is a dummy email.");
        emailerInfo.setEmailType(EmailType.TEST);
        emailerInfo.setRecipientsList(emailerInfoForKafka.getRecipientsList() != null ? emailerInfoForKafka.getRecipientsList() : List.of("default@example.com"));
        return emailerInfo;
    }

}
