package in.rajlabs.EmailService.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentInfo {

    private UUID attachment_id = CommonUtils.generateUUID();
    private String fileName;
    private byte[] content;
}