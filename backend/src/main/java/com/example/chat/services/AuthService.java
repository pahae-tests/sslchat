package com.example.chat.services;
import com.example.chat.dto.ApiDtos.*;
import com.example.chat.models.User;
import com.example.chat.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service
public class AuthService {
 private final UserRepository users; private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
 public AuthService(UserRepository users){this.users=users;}
 public UserView register(RegisterRequest r){if(users.existsByEmailIgnoreCase(r.email()))throw new ResponseStatusException(HttpStatus.CONFLICT,"Email déjà utilisé"); User u=users.save(new User(r.name(),r.email().toLowerCase(),encoder.encode(r.password()),r.image()));return view(u);}
 public UserView login(AuthRequest r){User u=users.findByEmailIgnoreCase(r.email()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Identifiants invalides"));if(!encoder.matches(r.password(),u.passwordHash))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Identifiants invalides");return view(u);}
 public static UserView view(User u){return new UserView(u.id,u.name,u.email,u.avatar);}
}