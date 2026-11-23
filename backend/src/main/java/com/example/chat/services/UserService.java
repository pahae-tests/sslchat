package com.example.chat.services;
import com.example.chat.models.User;
import com.example.chat.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service
public class UserService {
 private final UserRepository users;
 public UserService(UserRepository users){this.users=users;}
 public User get(Long id){return users.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Utilisateur introuvable"));}
}