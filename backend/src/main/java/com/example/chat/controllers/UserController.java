package com.example.chat.controllers;
import com.example.chat.dto.ApiDtos.UserView;import com.example.chat.services.UserService;import com.example.chat.services.AuthService;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/users") @CrossOrigin
public class UserController {private final UserService service;public UserController(UserService service){this.service=service;}
 @GetMapping("/{id}") public UserView get(@PathVariable Long id){return AuthService.view(service.get(id));}
}