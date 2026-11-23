package com.example.chat.repositories;
import com.example.chat.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
 List<ChatMessage> findByGroupIdOrderBySentAtAsc(Long groupId);
 List<ChatMessage> findByGroupIsNullAndSenderIdAndRecipientIdOrGroupIsNullAndSenderIdAndRecipientIdOrderBySentAtAsc(Long s1,Long r1,Long s2,Long r2);
}