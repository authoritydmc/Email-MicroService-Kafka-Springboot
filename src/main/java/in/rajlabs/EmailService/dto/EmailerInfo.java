package in.rajlabs.EmailService.dto;


import in.rajlabs.EmailService.util.AttachmentInfo;
import in.rajlabs.EmailService.util.CommonUtils;
import in.rajlabs.EmailService.util.EmailType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailerInfo {


    private UUID emailID = CommonUtils.generateUUID();
    private String sender_name = "RAJLABS";
    private String sender_emailAddress = "autoEmail@gmail.com";
    private EmailType emailType = EmailType.EMPTY_DO_NOT_SEND_EMAIL;
    //important
    private List<String> recipientsList = Collections.emptyList();
    //important

    private String mailBody;
    //important

    private String subject;

    private List<String> ccList;

    private List<String> bccList;
    private List<AttachmentInfo> attachments;

    private boolean isEmailsent = false;
    private LocalDateTime emailSentTime;
    private String status;

    private Integer retryCount = 0;
    private LocalDateTime insertionTime = LocalDateTime.now();

}