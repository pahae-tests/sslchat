package com.example.chat.repositories;
import com.example.chat.models.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;
public interface GroupRepository extends JpaRepository<ChatGroup,Long> {}