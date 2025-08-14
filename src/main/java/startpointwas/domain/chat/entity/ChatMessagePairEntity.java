package startpointwas.domain.chat.entity;


import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "chat_message_pair",
        indexes = { @Index(name="idx_ctx_id_id", columnList = "contextId,id") }
)
public class ChatMessagePairEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contextId;

    @Lob @Column(columnDefinition = "LONGTEXT")
    private String question;

    @Lob @Column(columnDefinition = "LONGTEXT")
    private String answer;

    private Instant createdAt = Instant.now();

    protected ChatMessagePairEntity() {}
    public ChatMessagePairEntity(String contextId, String q, String a) {
        this.contextId = contextId; this.question = q; this.answer = a;
    }

    public Long getId() { return id; }
    public String getContextId() { return contextId; }
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    public Instant getCreatedAt() { return createdAt; }
}
