package com.example.chat.controllers;
import com.example.chat.dto.ApiDtos.*;import com.example.chat.services.GroupService;
import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;import java.util.List;
@RestController @RequestMapping("/api/groups") @CrossOrigin
public class GroupController {private final GroupService service;public GroupController(GroupService service){this.service=service;}
 @PostMapping public GroupView create(@Valid @RequestBody GroupRequest r){return service.create(r);}
 @GetMapping public List<GroupView> all(@RequestParam Long userId){return service.all(userId);}
 @GetMapping("/{groupId}/members") public List<UserView> members(@PathVariable Long groupId){return service.members(groupId);}
}