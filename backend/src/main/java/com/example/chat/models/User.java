package com.example.chat.models;
import jakarta.persistence.*;
@Entity @Table(name="users")
public class User {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(nullable=false) public String name;
 @Column(nullable=false, unique=true) public String email;
 @Column(nullable=false) public String passwordHash;
 @Lob @Column(name="avatar") public byte[] avatar;
 protected User() {}
 public User(String name,String email,String passwordHash,byte[] avatar){this.name=name;this.email=email;this.passwordHash=passwordHash;this.avatar=avatar;}
}