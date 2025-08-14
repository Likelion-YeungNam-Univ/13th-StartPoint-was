package startpointwas.domain.chat.repository;


import startpointwas.domain.chat.entity.ChatMessagePairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatMessagePairRepository extends JpaRepository<ChatMessagePairEntity, Long> {
    List<ChatMessagePairEntity> findByUserIdOrderByIdAsc(String userId);
    long deleteByContextId(String contextId);
}
