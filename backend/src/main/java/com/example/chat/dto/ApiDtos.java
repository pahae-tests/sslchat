package com.example.chat.dto;
import jakarta.validation.constraints.*;
public final class ApiDtos {
 private ApiDtos() {}
 public record AuthRequest(@NotBlank @Email String email,@NotBlank String password) {}
 public record RegisterRequest(@NotBlank String name,@Email @NotBlank String email,@Size(min=4) String password,byte[] image) {}
 public record GroupRequest(@NotBlank String name,byte[] image,@NotNull Long userId) {}
 public record MessageRequest(@NotNull Long senderId,Long recipientId,Long groupId,@NotBlank String content) {}
 public record DecisionRequest(@NotNull Long adminId,@NotNull Boolean accept) {}
 public record UserView(Long id,String name,String email,byte[] image) {}
 public record GroupView(Long id,String name,byte[] image,Long ownerId,boolean member,String role,String requestStatus,long memberCount) {}
 public record MessageView(Long id,Long senderId,String sender,Long recipientId,Long groupId,String content,String sentAt) {}
 public record RequestView(Long id,Long userId,String name,String email) {}
}