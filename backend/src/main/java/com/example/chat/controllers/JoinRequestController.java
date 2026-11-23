package com.example.chat.controllers;
import com.example.chat.dto.ApiDtos.*;import com.example.chat.services.GroupService;
import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/groups/{groupId}") @CrossOrigin
public class JoinRequestController {private final GroupService service;public JoinRequestController(GroupService service){this.service=service;}
 @PostMapping("/join-requests") public Map<String,String> join(@PathVariable Long groupId,@RequestParam Long userId){return Map.of("status",service.requestJoin(groupId,userId));}
 @GetMapping("/requests") public List<RequestView> pending(@PathVariable Long groupId,@RequestParam Long adminId){return service.pending(groupId,adminId);}
 @PostMapping("/requests/{requestId}") public Map<String,String> decide(@PathVariable Long groupId,@PathVariable Long requestId,@Valid @RequestBody DecisionRequest d){return Map.of("status",service.decide(groupId,requestId,d));}
}