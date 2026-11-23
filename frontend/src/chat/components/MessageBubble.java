package chat.components;

import chat.Theme;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MessageBubble extends JPanel {
    public MessageBubble(String text, String sender, String date, boolean mine) {
        setOpaque(false);
        setLayout(new FlowLayout(mine ? FlowLayout.RIGHT : FlowLayout.LEFT, 0, 5));

        JPanel bubble = new JPanel();
        bubble.setLayout(new BoxLayout(bubble, BoxLayout.Y_AXIS));
        bubble.setBackground(mine ? Theme.SOFT : Color.WHITE);
        bubble.setBorder(new EmptyBorder(10, 13, 8, 13));
        bubble.setMaximumSize(new Dimension(390, Integer.MAX_VALUE));

        if (!mine) {
            JLabel who = new JLabel(sender);
            who.setFont(new Font("SansSerif", Font.BOLD, 11));
            who.setForeground(Theme.PRIMARY_DARK);
            bubble.add(who);
            bubble.add(Box.createVerticalStrut(4));
        }
        JLabel body = new JLabel("<html><div style='width:260px'>" + escape(text) + "</div></html>");
        body.setFont(Theme.FONT);
        body.setForeground(Theme.TEXT);
        bubble.add(body);
        bubble.add(Box.createVerticalStrut(5));
        JLabel time = new JLabel(date);
        time.setFont(new Font("SansSerif", Font.PLAIN, 10));
        time.setForeground(Theme.MUTED);
        time.setAlignmentX(mine ? Component.RIGHT_ALIGNMENT : Component.LEFT_ALIGNMENT);
        bubble.add(time);
        add(bubble);
    }

    private String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
