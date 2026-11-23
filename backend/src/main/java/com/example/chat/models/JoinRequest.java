package com.example.chat.models;
import jakarta.persistence.*;
@Entity @Table(name="join_requests",uniqueConstraints=@UniqueConstraint(columnNames={"group_id","user_id"}))
public class JoinRequest {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @ManyToOne(optional=false) public ChatGroup group;
 @ManyToOne(optional=false) public User user;
 @Column(nullable=false) public String status="PENDING";
 protected JoinRequest() {}
 public JoinRequest(ChatGroup group,User user){this.group=group;this.user=user;}
}