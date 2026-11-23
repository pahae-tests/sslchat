package chat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginPanel extends JPanel {
    public LoginPanel(JFrame frame) {
        setBackground(Theme.BG);
        setLayout(new GridBagLayout());

        JPanel card = Ui.card(new GridBagLayout());
        card.setPreferredSize(new Dimension(390, 430));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0; c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(7, 0, 7, 0);
        JLabel brand = new JLabel("✦  connect", SwingConstants.CENTER);
        brand.setFont(new Font("SansSerif", Font.BOLD, 25));
        brand.setForeground(Theme.PRIMARY);
        card.add(brand, c);
        c.gridy++;
        JLabel title = new JLabel("Bon retour !", SwingConstants.CENTER);
        title.setFont(Theme.TITLE); title.setForeground(Theme.TEXT);
        card.add(title, c);
        c.gridy++;
        JLabel sub = Ui.muted("Connectez-vous pour continuer");
        sub.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(sub, c);
        c.gridy++;
        c.insets = new Insets(18, 0, 4, 0);
        card.add(Ui.label("Email"), c);
        c.gridy++; c.insets = new Insets(3, 0, 9, 0);
        JTextField email = Ui.field("vous@exemple.com");
        email.setPreferredSize(new Dimension(330, 42));
        card.add(email, c);
        c.gridy++; c.insets = new Insets(6, 0, 4, 0);
        card.add(Ui.label("Mot de passe"), c);
        c.gridy++; c.insets = new Insets(3, 0, 12, 0);
        JPasswordField password = Ui.passwordField();
        password.setPreferredSize(new Dimension(330, 42));
        card.add(password, c);
        c.gridy++;
        JButton login = Ui.button("Se connecter", true);
        login.addActionListener(e -> Main.enterApp("Alex Morgan",
                email.getText().isBlank() ? "alex@example.com" : email.getText(), null));
        card.add(login, c);
        c.gridy++; c.insets = new Insets(12, 0, 4, 0);
        JPanel registerRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        registerRow.setOpaque(false);
        registerRow.add(Ui.muted("Pas encore de compte ?"));
        JButton register = new JButton("Créer un compte");
        register.setBorderPainted(false); register.setContentAreaFilled(false);
        register.setForeground(Theme.PRIMARY); register.setFont(Theme.MEDIUM);
        register.addActionListener(e -> Main.showRegister());
        registerRow.add(register);
        card.add(registerRow, c);

        add(card);
    }
}
