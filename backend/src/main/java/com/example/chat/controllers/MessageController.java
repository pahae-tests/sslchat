package com.example.chat.controllers;
import com.example.chat.dto.ApiDtos.*;import com.example.chat.services.MessageService;
import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;import java.util.List;
@RestController @RequestMapping("/api") @CrossOrigin
public class MessageController {private final MessageService service;public MessageController(MessageService service){this.service=service;}
 @PostMapping("/messages") public MessageView send(@Valid @RequestBody MessageRequest r){return service.send(r);}
 @GetMapping("/groups/{groupId}/messages") public List<MessageView> group(@PathVariable Long groupId,@RequestParam Long userId){return service.groupMessages(groupId,userId);}
 @GetMapping("/messages/private") public List<MessageView> privateMessages(@RequestParam Long user1,@RequestParam Long user2){return service.privateMessages(user1,user2);}
}