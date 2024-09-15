package in.rajlabs.EmailService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "email_attachments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailLogAttachmentEntity {

    @Id
    private UUID attachment_id;

    @Column(nullable = false)
    private String fileName;

    @Lob
    @Column(nullable = false)
    private byte[] content;

    private LocalDateTime insertionTime;

    private LocalDateTime updatedTime;


    private UUID email_id;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        insertionTime = now;
        updatedTime = now;
    }
    @PreUpdate
    public void preUpdate() {
        updatedTime = LocalDateTime.now();
    }


}