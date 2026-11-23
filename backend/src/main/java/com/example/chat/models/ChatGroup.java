package com.example.chat.models;
import jakarta.persistence.*;
@Entity @Table(name="chat_groups")
public class ChatGroup {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(nullable=false) public String name;
 @Lob public byte[] image;
 @ManyToOne(optional=false) public User owner;
 protected ChatGroup() {}
 public ChatGroup(String name,byte[] image,User owner){this.name=name;this.image=image;this.owner=owner;}
}