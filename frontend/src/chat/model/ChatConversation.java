package chat.model;

import javax.swing.ImageIcon;
import java.util.*;

public class ChatConversation {
    public final String id, name, lastMessage;
    public final ImageIcon image;
    public final boolean unread, group;
    public final List<ChatMessage> messages = new ArrayList<>();
    public boolean member;
    public boolean admin;
    public String requestStatus = "";
    public final List<String> members = new ArrayList<>();
    public final List<String> requests = new ArrayList<>();

    public ChatConversation(String id, String name, String lastMessage, ImageIcon image,
                            boolean unread, boolean group, boolean member) {
        this.id = id; this.name = name; this.lastMessage = lastMessage; this.image = image;
        this.unread = unread; this.group = group; this.member = member;
    }
}
