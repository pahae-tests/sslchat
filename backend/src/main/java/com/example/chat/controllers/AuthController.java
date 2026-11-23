package com.example.chat.controllers;
import com.example.chat.dto.ApiDtos.*;import com.example.chat.services.AuthService;
import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth") @CrossOrigin
public class AuthController {private final AuthService service;public AuthController(AuthService service){this.service=service;}
 @PostMapping("/register") public UserView register(@Valid @RequestBody RegisterRequest r){return service.register(r);}
 @PostMapping("/login") public UserView login(@Valid @RequestBody AuthRequest r){return service.login(r);}
}