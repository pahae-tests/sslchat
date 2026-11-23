package chat;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static JFrame frame;
    private static String currentName = "Alex Morgan";
    private static String currentEmail = "alex@example.com";
    private static ImageIcon currentImage;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
            frame = new JFrame("Connect — Chat");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setMinimumSize(new Dimension(980, 650));
            frame.setSize(1180, 760);
            frame.setLocationRelativeTo(null);
            showLogin();
            frame.setVisible(true);
        });
    }

    public static void showLogin() {
        frame.setContentPane(new LoginPanel(frame));
        frame.revalidate(); frame.repaint();
    }

    public static void showRegister() {
        frame.setContentPane(new RegisterPanel(frame));
        frame.revalidate(); frame.repaint();
    }

    public static void enterApp(String name, String email, ImageIcon image) {
        currentName = name; currentEmail = email; currentImage = image;
        frame.setContentPane(new MainPanel(frame, currentName, currentEmail, currentImage));
        frame.revalidate(); frame.repaint();
    }
}
