package com.example.chat.repositories;
import com.example.chat.models.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface GroupMemberRepository extends JpaRepository<GroupMember,Long> {
 Optional<GroupMember> findByGroupIdAndUserId(Long groupId,Long userId);
 List<GroupMember> findByGroupId(Long groupId);
 List<GroupMember> findByUserId(Long userId);
 boolean existsByGroupIdAndUserId(Long groupId,Long userId);
}