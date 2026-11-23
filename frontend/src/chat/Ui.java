package chat;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public final class Ui {
    private Ui() {}

    public static JButton button(String text, boolean primary) {
        JButton b = new JButton(text);
        b.setFont(Theme.MEDIUM);
        b.setForeground(primary ? Color.WHITE : Theme.TEXT);
        b.setBackground(primary ? Theme.PRIMARY : Color.WHITE);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new CompoundBorder(new LineBorder(primary ? Theme.PRIMARY : Theme.BORDER, 1, true),
                new EmptyBorder(9, 15, 9, 15)));
        return b;
    }

    public static JTextField field(String placeholder) {
        JTextField f = new JTextField();
        f.setFont(Theme.FONT);
        f.setForeground(Theme.TEXT);
        f.setBorder(new CompoundBorder(new LineBorder(Theme.BORDER, 1, true),
                new EmptyBorder(10, 12, 10, 12)));
        f.putClientProperty("JTextField.placeholderText", placeholder);
        return f;
    }

    public static JPasswordField passwordField() {
        JPasswordField f = new JPasswordField();
        f.setFont(Theme.FONT);
        f.setBorder(new CompoundBorder(new LineBorder(Theme.BORDER, 1, true),
                new EmptyBorder(10, 12, 10, 12)));
        return f;
    }

    public static JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(Theme.FONT);
        l.setForeground(Theme.TEXT);
        return l;
    }

    public static JLabel muted(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.PLAIN, 11));
        l.setForeground(Theme.MUTED);
        return l;
    }

    public static JPanel card(LayoutManager layout) {
        JPanel p = new JPanel(layout);
        p.setBackground(Color.WHITE);
        p.setBorder(new CompoundBorder(new LineBorder(Theme.BORDER, 1, true),
                new EmptyBorder(18, 18, 18, 18)));
        return p;
    }

    public static ImageIcon loadImage(File file, int size) {
        try {
            ImageIcon raw = new ImageIcon(file.getAbsolutePath());
            Image img = raw.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }

    public static JLabel avatar(String name, ImageIcon image, int size) {
        JLabel l = new JLabel();
        l.setPreferredSize(new Dimension(size, size));
        l.setMinimumSize(new Dimension(size, size));
        l.setHorizontalAlignment(SwingConstants.CENTER);
        l.setOpaque(true);
        l.setBackground(new Color(229, 233, 255));
        l.setForeground(Theme.PRIMARY_DARK);
        l.setFont(new Font("SansSerif", Font.BOLD, Math.max(12, size / 3)));
        l.setBorder(new LineBorder(Color.WHITE, 2, true));
        if (image != null) l.setIcon(new ImageIcon(image.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH)));
        else l.setText(initials(name));
        return l;
    }

    public static String initials(String name) {
        if (name == null || name.isBlank()) return "?";
        String[] parts = name.trim().split("\\\\s+");
        return parts.length > 1
                ? ("" + parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase()
                : name.substring(0, 1).toUpperCase();
    }

    public static ImageIcon placeholderImage(int size) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(229, 233, 255));
        g.fillOval(0, 0, size, size);
        g.setColor(Theme.PRIMARY);
        g.setFont(new Font("SansSerif", Font.BOLD, size / 3));
        g.drawString("G", size / 3, size * 2 / 3);
        g.dispose();
        return new ImageIcon(img);
    }
}
