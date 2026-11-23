package com.example.chat.services;
import com.example.chat.dto.ApiDtos.*;
import com.example.chat.models.*;
import com.example.chat.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
@Service
public class GroupService {
 private final GroupRepository groups; private final GroupMemberRepository members; private final JoinRequestRepository requests; private final UserService userService;
 public GroupService(GroupRepository groups,GroupMemberRepository members,JoinRequestRepository requests,UserService userService){this.groups=groups;this.members=members;this.requests=requests;this.userService=userService;}
 private ChatGroup getGroup(Long id){return groups.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Groupe introuvable"));}
 public GroupView create(GroupRequest r){User owner=userService.get(r.userId());ChatGroup g=groups.save(new ChatGroup(r.name(),r.image(),owner));members.save(new GroupMember(g,owner,"ADMIN"));return view(g,owner);}
 public List<GroupView> all(Long userId){User u=userService.get(userId);return groups.findAll().stream().map(g->view(g,u)).toList();}
 private GroupView view(ChatGroup g,User u){GroupMember m=members.findByGroupIdAndUserId(g.id,u.id).orElse(null);JoinRequest q=requests.findByGroupIdAndUserId(g.id,u.id).orElse(null);return new GroupView(g.id,g.name,g.image,g.owner.id,m!=null,m==null?null:m.role,q==null?null:q.status,members.findByGroupId(g.id).size());}
 @Transactional public String requestJoin(Long groupId,Long userId){ChatGroup g=getGroup(groupId);User u=userService.get(userId);if(members.existsByGroupIdAndUserId(groupId,userId))return "MEMBER";JoinRequest q=requests.findByGroupIdAndUserId(groupId,userId).orElseGet(()->new JoinRequest(g,u));q.status="PENDING";requests.save(q);return q.status;}
 public List<UserView> members(Long groupId){getGroup(groupId);return members.findByGroupId(groupId).stream().map(m->AuthService.view(m.user)).toList();}
 public List<RequestView> pending(Long groupId,Long adminId){requireAdmin(groupId,adminId);return requests.findByGroupIdAndStatus(groupId,"PENDING").stream().map(r->new RequestView(r.id,r.user.id,r.user.name,r.user.email)).toList();}
 @Transactional public String decide(Long groupId,Long requestId,DecisionRequest d){requireAdmin(groupId,d.adminId());JoinRequest q=requests.findById(requestId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Demande introuvable"));if(!q.group.id.equals(groupId)||!"PENDING".equals(q.status))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Demande invalide");if(Boolean.TRUE.equals(d.accept())){members.save(new GroupMember(q.group,q.user,"MEMBER"));q.status="ACCEPTED";}else q.status="REJECTED";requests.save(q);return q.status;}
 private void requireAdmin(Long groupId,Long userId){GroupMember m=members.findByGroupIdAndUserId(groupId,userId).orElseThrow(()->new ResponseStatusException(HttpStatus.FORBIDDEN,"Accès refusé"));if(!"ADMIN".equals(m.role))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Accès réservé aux administrateurs");}
 public boolean isMember(Long groupId,Long userId){return members.existsByGroupIdAndUserId(groupId,userId);}
}