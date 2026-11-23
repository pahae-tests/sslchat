package com.example.chat.models;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="messages")
public class ChatMessage {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @ManyToOne(optional=false) public User sender;
 @ManyToOne public User recipient;
 @ManyToOne public ChatGroup group;
 @Column(nullable=false,length=4000) public String content;
 @Column(nullable=false) public LocalDateTime sentAt=LocalDateTime.now();
 protected ChatMessage() {}
 public ChatMessage(User sender,User recipient,ChatGroup group,String content){this.sender=sender;this.recipient=recipient;this.group=group;this.content=content;}
}