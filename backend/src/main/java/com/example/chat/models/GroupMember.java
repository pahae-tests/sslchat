package com.example.chat.models;
import jakarta.persistence.*;
@Entity @Table(name="group_members",uniqueConstraints=@UniqueConstraint(columnNames={"group_id","user_id"}))
public class GroupMember {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @ManyToOne(optional=false) public ChatGroup group;
 @ManyToOne(optional=false) public User user;
 @Column(nullable=false) public String role;
 protected GroupMember() {}
 public GroupMember(ChatGroup group,User user,String role){this.group=group;this.user=user;this.role=role;}
}