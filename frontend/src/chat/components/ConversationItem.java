package chat.components;

import chat.*;
import chat.model.ChatConversation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ConversationItem extends JPanel {
    public ConversationItem(ChatConversation conversation, Runnable onClick) {
        setLayout(new BorderLayout(11, 0));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(11, 9, 11, 9));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel avatar = Ui.avatar(conversation.name, conversation.image, 43);
        add(avatar, BorderLayout.WEST);

        JPanel info = new JPanel(new GridLayout(2, 1, 0, 5));
        info.setOpaque(false);
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JLabel name = Ui.label(conversation.name);
        name.setFont(Theme.MEDIUM);
        top.add(name, BorderLayout.CENTER);
        if (conversation.unread) {
            JLabel dot = new JLabel("●");
            dot.setForeground(Theme.PRIMARY);
            top.add(dot, BorderLayout.EAST);
        }
        JLabel last = Ui.muted(conversation.lastMessage);
        info.add(top);
        info.add(last);
        add(info, BorderLayout.CENTER);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { onClick.run(); }
            public void mouseEntered(java.awt.event.MouseEvent e) { setBackground(new Color(248, 249, 255)); }
            public void mouseExited(java.awt.event.MouseEvent e) { setBackground(Color.WHITE); }
        });
        for (Component c : getComponents()) {
            c.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) { onClick.run(); }
            });
        }
    }
}
