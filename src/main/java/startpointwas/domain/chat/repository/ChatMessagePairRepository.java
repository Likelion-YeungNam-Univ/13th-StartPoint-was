package startpointwas.domain.chat.repository;


import startpointwas.domain.chat.entity.ChatMessagePairEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessagePairRepository extends JpaRepository<ChatMessagePairEntity, Long> {

    Page<ChatMessagePairEntity> findByContextIdOrderByIdDesc(String contextId, Pageable pageable);

    List<ChatMessagePairEntity> findTop200ByContextIdOrderByIdDesc(String contextId);

    long deleteByContextId(String contextId);
}
