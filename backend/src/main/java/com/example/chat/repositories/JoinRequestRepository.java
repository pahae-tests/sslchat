package com.example.chat.repositories;
import com.example.chat.models.JoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface JoinRequestRepository extends JpaRepository<JoinRequest,Long> {
 Optional<JoinRequest> findByGroupIdAndUserId(Long groupId,Long userId);
 List<JoinRequest> findByGroupIdAndStatus(Long groupId,String status);
}