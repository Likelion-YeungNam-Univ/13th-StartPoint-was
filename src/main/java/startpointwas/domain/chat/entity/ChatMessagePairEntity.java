package startpointwas.domain.chat.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@NoArgsConstructor
@Entity
@Getter
@Table(
        name = "chat_message_pair",
        indexes = { @Index(name="idx_ctx_id_id", columnList = "contextId,id") }
)
public class ChatMessagePairEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String contextId;

    @Lob @Column(columnDefinition = "LONGTEXT")
    private String question;

    @Lob @Column(columnDefinition = "LONGTEXT")
    private String answer;

    private Instant createdAt = Instant.now();


    public ChatMessagePairEntity(String userId, String contextId, String q, String a) {
        this.userId = userId;
        this.contextId = contextId;
        this.question = q;
        this.answer = a;
    }
}
