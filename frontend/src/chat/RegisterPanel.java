package chat;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class RegisterPanel extends JPanel {
    private ImageIcon selectedImage;
    private String imagePath;

    public RegisterPanel(JFrame frame) {
        setBackground(Theme.BG);
        setLayout(new GridBagLayout());
        JPanel card = Ui.card(new GridBagLayout());
        card.setPreferredSize(new Dimension(430, 590));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx=0; c.gridy=0; c.fill=GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(6,0,6,0);
        JLabel title = new JLabel("Créer un compte", SwingConstants.CENTER);
        title.setFont(Theme.TITLE); title.setForeground(Theme.TEXT); card.add(title,c);
        c.gridy++; JLabel sub=Ui.muted("Quelques informations pour commencer");
        sub.setHorizontalAlignment(SwingConstants.CENTER); card.add(sub,c);

        c.gridy++; c.insets=new Insets(17,0,4,0); card.add(Ui.label("Nom"),c);
        c.gridy++; c.insets=new Insets(3,0,7,0); JTextField name=Ui.field("Votre nom"); name.setPreferredSize(new Dimension(350,40)); card.add(name,c);
        c.gridy++; c.insets=new Insets(4,0,4,0); card.add(Ui.label("Email"),c);
        c.gridy++; c.insets=new Insets(3,0,7,0); JTextField email=Ui.field("vous@exemple.com"); email.setPreferredSize(new Dimension(350,40)); card.add(email,c);
        c.gridy++; c.insets=new Insets(4,0,4,0); card.add(Ui.label("Mot de passe"),c);
        c.gridy++; c.insets=new Insets(3,0,7,0); JPasswordField password=Ui.passwordField(); password.setPreferredSize(new Dimension(350,40)); card.add(password,c);
        c.gridy++; c.insets=new Insets(8,0,4,0); card.add(Ui.label("Photo de profil (facultatif)"),c);
        c.gridy++; c.insets=new Insets(3,0,8,0);
        JPanel imageRow=new JPanel(new FlowLayout(FlowLayout.LEFT,8,0)); imageRow.setOpaque(false);
        JLabel preview=Ui.avatar("?",null,48);
        JButton choose=Ui.button("Choisir une image",false);
        choose.addActionListener(e -> {
            JFileChooser chooser=new JFileChooser();
            if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
                File f=chooser.getSelectedFile(); imagePath=f.getAbsolutePath();
                selectedImage=Ui.loadImage(f,64);
                preview.setIcon(selectedImage); preview.setText("");
            }
        });
        imageRow.add(preview); imageRow.add(choose); card.add(imageRow,c);
        c.gridy++; c.insets=new Insets(12,0,5,0);
        JButton create=Ui.button("Créer mon compte",true);
        create.addActionListener(e -> {
            String n=name.getText().trim(), em=email.getText().trim();
            if(n.isEmpty() || em.isEmpty()) { JOptionPane.showMessageDialog(this,"Veuillez remplir le nom et l'email."); return; }
            if(password.getPassword().length<4) { JOptionPane.showMessageDialog(this,"Le mot de passe doit contenir au moins 4 caractères."); return; }
            Main.enterApp(n,em,selectedImage);
        });
        card.add(create,c);
        c.gridy++; c.insets=new Insets(7,0,0,0);
        JButton back=new JButton("J'ai déjà un compte — Se connecter");
        back.setBorderPainted(false); back.setContentAreaFilled(false); back.setForeground(Theme.PRIMARY);
        back.addActionListener(e -> Main.showLogin()); card.add(back,c);
        add(card);
    }
}
