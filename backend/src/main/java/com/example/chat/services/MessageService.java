package com.example.chat.services;
import com.example.chat.dto.ApiDtos.*;
import com.example.chat.models.*;
import com.example.chat.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
@Service
public class MessageService {
 private final ChatMessageRepository messages;private final GroupRepository groups;private final GroupMemberRepository members;private final UserService users;
 public MessageService(ChatMessageRepository messages,GroupRepository groups,GroupMemberRepository members,UserService users){this.messages=messages;this.groups=groups;this.members=members;this.users=users;}
 private ChatGroup group(Long id){return groups.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Groupe introuvable"));}
 public MessageView send(MessageRequest r){User sender=users.get(r.senderId());ChatGroup g=null;User recipient=null;if(r.groupId()!=null){g=group(r.groupId());if(!members.existsByGroupIdAndUserId(g.id,sender.id))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Vous n'êtes pas membre du groupe");}else{if(r.recipientId()==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"recipientId ou groupId obligatoire");recipient=users.get(r.recipientId());}return view(messages.save(new ChatMessage(sender,recipient,g,r.content())));}
 public List<MessageView> groupMessages(Long groupId,Long userId){if(!members.existsByGroupIdAndUserId(groupId,userId))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Accès refusé");return messages.findByGroupIdOrderBySentAtAsc(groupId).stream().map(this::view).toList();}
 public List<MessageView> privateMessages(Long user1,Long user2){users.get(user1);users.get(user2);return messages.findByGroupIsNullAndSenderIdAndRecipientIdOrGroupIsNullAndSenderIdAndRecipientIdOrderBySentAtAsc(user1,user2,user2,user1).stream().map(this::view).toList();}
 private MessageView view(ChatMessage m){return new MessageView(m.id,m.sender.id,m.sender.name,m.recipient==null?null:m.recipient.id,m.group==null?null:m.group.id,m.content,m.sentAt.toString());}
}