package chat;

import chat.components.*;
import chat.model.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainPanel extends JPanel {
    private final JFrame frame;
    private final String userName, userEmail;
    private final ImageIcon userImage;
    private final java.util.List<ChatConversation> chats = new ArrayList<>();
    private final java.util.List<ChatConversation> groups = new ArrayList<>();
    private final JPanel listPanel = new JPanel();
    private final JPanel messagesPanel = new JPanel();
    private final JLabel conversationTitle = new JLabel("Sélectionnez une conversation");
    private final JLabel conversationSubtitle = new JLabel("Vos messages apparaîtront ici");
    private final JButton detailsButton = Ui.button("Détails", false);
    private final JTextField messageInput = Ui.field("Écrire un message...");
    private final CardLayout listCards = new CardLayout();
    private final JPanel listCardPanel = new JPanel(listCards);
    private ChatConversation selected;
    private boolean showingGroups = false;

    public MainPanel(JFrame frame, String userName, String userEmail, ImageIcon userImage) {
        this.frame=frame; this.userName=userName; this.userEmail=userEmail; this.userImage=userImage;
        seedData();
        setLayout(new BorderLayout());
        setBackground(Theme.BG);
        add(buildSidebar(),BorderLayout.WEST);
        add(buildChatArea(),BorderLayout.CENTER);
        renderLists();
        if(!chats.isEmpty()) select(chats.get(0));
    }

    private void seedData() {
        ChatConversation a=new ChatConversation("c1","Sarah Johnson","Tu es disponible demain ?",null,true,false,true);
        a.messages.add(new ChatMessage("Salut Alex !","Sarah Johnson","10:32",false));
        a.messages.add(new ChatMessage("Oui, bien sûr. Qu'est-ce qui se passe ?","Moi","10:34",true));
        a.messages.add(new ChatMessage("Tu es disponible demain ?","Sarah Johnson","10:35",false));
        chats.add(a);
        ChatConversation b=new ChatConversation("c2","Youssef Amrani","Merci beaucoup !",null,false,false,true);
        b.messages.add(new ChatMessage("Le fichier est prêt.","Moi","Hier",true));
        b.messages.add(new ChatMessage("Merci beaucoup !","Youssef Amrani","Hier",false));
        chats.add(b);

        ChatConversation g1=new ChatConversation("g1","Développeurs Java","Nadia : On avance ?",null,true,true,true);
        g1.admin=true; g1.members.addAll(Arrays.asList("Alex Morgan (admin)","Nadia Benali","Omar Khalid"));
        g1.requests.add("Samir El Fassi"); g1.requests.add("Lina Karim");
        g1.messages.add(new ChatMessage("Bienvenue dans le groupe !","Nadia Benali","09:15",false));
        g1.messages.add(new ChatMessage("On avance sur l'interface Swing.","Moi","09:20",true));
        groups.add(g1);
        ChatConversation g2=new ChatConversation("g2","Gaming","Discussions jeux vidéo",null,false,true,false);
        g2.requestStatus="";
        g2.members.addAll(Arrays.asList("Mehdi","Sara","Omar"));
        g2.messages.add(new ChatMessage("Qui joue ce soir ?","Mehdi","Hier",false));
        groups.add(g2);
        ChatConversation g3=new ChatConversation("g3","Projet réseau","Groupe ouvert à tous",null,false,true,true);
        g3.members.addAll(Arrays.asList("Nadia","Youssef","Alex Morgan"));
        groups.add(g3);
    }

    private JPanel buildSidebar() {
        JPanel side=new JPanel(new BorderLayout());
        side.setPreferredSize(new Dimension(350,10));
        side.setBackground(Color.WHITE);
        side.setBorder(new MatteBorder(0,0,0,1,Theme.BORDER));

        JPanel account=new JPanel(new BorderLayout(10,0));
        account.setBackground(Color.WHITE); account.setBorder(new EmptyBorder(18,16,16,14));
        account.add(Ui.avatar(userName,userImage,43),BorderLayout.WEST);
        JPanel who=new JPanel(new GridLayout(2,1,0,3)); who.setOpaque(false);
        JLabel n=Ui.label(userName); n.setFont(Theme.MEDIUM);
        who.add(n); who.add(Ui.muted(userEmail)); account.add(who,BorderLayout.CENTER);
        JButton logout=new JButton("↪");
        logout.setToolTipText("Déconnexion"); logout.setForeground(Theme.MUTED);
        logout.setBorderPainted(false); logout.setContentAreaFilled(false);
        logout.addActionListener(e -> Main.showLogin()); account.add(logout,BorderLayout.EAST);
        side.add(account,BorderLayout.NORTH);

        JPanel tabs=new JPanel(new GridLayout(1,2,8,0)); tabs.setBackground(Color.WHITE);
        tabs.setBorder(new EmptyBorder(0,14,12,14));
        JButton chatsTab=Ui.button("Chats",true), groupsTab=Ui.button("Groupes",false);
        chatsTab.addActionListener(e->{showingGroups=false; chatsTab.setBackground(Theme.PRIMARY);chatsTab.setForeground(Color.WHITE);groupsTab.setBackground(Color.WHITE);groupsTab.setForeground(Theme.TEXT);renderLists();});
        groupsTab.addActionListener(e->{showingGroups=true;groupsTab.setBackground(Theme.PRIMARY);groupsTab.setForeground(Color.WHITE);chatsTab.setBackground(Color.WHITE);chatsTab.setForeground(Theme.TEXT);renderLists();});
        tabs.add(chatsTab); tabs.add(groupsTab);
        JPanel north=new JPanel(new BorderLayout()); north.setBackground(Color.WHITE); north.add(tabs,BorderLayout.NORTH);
        side.add(north,BorderLayout.CENTER);

        JPanel listWrap=new JPanel(new BorderLayout()); listWrap.setBackground(Color.WHITE);
        JPanel listHeader=new JPanel(new BorderLayout()); listHeader.setBackground(Color.WHITE); listHeader.setBorder(new EmptyBorder(3,16,9,14));
        JLabel heading=new JLabel("MESSAGES"); heading.setFont(new Font("SansSerif",Font.BOLD,10)); heading.setForeground(Theme.MUTED);
        listHeader.add(heading,BorderLayout.WEST);
        JButton createGroup=Ui.button("+ Créer un groupe",false);
        createGroup.setFont(new Font("SansSerif",Font.BOLD,11));
        createGroup.addActionListener(e->createGroupDialog());
        listHeader.add(createGroup,BorderLayout.EAST);
        listWrap.add(listHeader,BorderLayout.NORTH);

        listPanel.setLayout(new BoxLayout(listPanel,BoxLayout.Y_AXIS)); listPanel.setBackground(Color.WHITE);
        JScrollPane scroll=new JScrollPane(listPanel); scroll.setBorder(null); scroll.getViewport().setBackground(Color.WHITE);
        listWrap.add(scroll,BorderLayout.CENTER);
        side.add(listWrap,BorderLayout.SOUTH);
        return side;
    }

    private JPanel buildChatArea() {
        JPanel area=new JPanel(new BorderLayout()); area.setBackground(Theme.BG);
        JPanel header=new JPanel(new BorderLayout(10,0)); header.setBackground(Color.WHITE);
        header.setBorder(new CompoundBorder(new MatteBorder(0,0,1,0,Theme.BORDER),new EmptyBorder(17,22,17,22)));
        JPanel titleBox=new JPanel(new GridLayout(2,1,0,4)); titleBox.setOpaque(false);
        conversationTitle.setFont(Theme.MEDIUM); conversationTitle.setForeground(Theme.TEXT);
        conversationSubtitle.setFont(new Font("SansSerif",Font.PLAIN,11)); conversationSubtitle.setForeground(Theme.MUTED);
        titleBox.add(conversationTitle); titleBox.add(conversationSubtitle); header.add(titleBox,BorderLayout.CENTER);
        detailsButton.setVisible(false); detailsButton.addActionListener(e->showDetails());
        header.add(detailsButton,BorderLayout.EAST); area.add(header,BorderLayout.NORTH);

        messagesPanel.setLayout(new BoxLayout(messagesPanel,BoxLayout.Y_AXIS)); messagesPanel.setBackground(Theme.BG);
        messagesPanel.setBorder(new EmptyBorder(20,22,20,22));
        JScrollPane scroll=new JScrollPane(messagesPanel); scroll.setBorder(null); scroll.getViewport().setBackground(Theme.BG);
        area.add(scroll,BorderLayout.CENTER);

        JPanel composer=new JPanel(new BorderLayout(10,0)); composer.setBackground(Color.WHITE);
        composer.setBorder(new CompoundBorder(new MatteBorder(1,0,0,0,Theme.BORDER),new EmptyBorder(14,18,14,18)));
        messageInput.setPreferredSize(new Dimension(100,42));
        JButton send=Ui.button("Envoyer",true);
        send.addActionListener(e->sendMessage());
        messageInput.addActionListener(e->sendMessage());
        composer.add(messageInput,BorderLayout.CENTER); composer.add(send,BorderLayout.EAST);
        area.add(composer,BorderLayout.SOUTH);
        return area;
    }

    private void renderLists() {
        listPanel.removeAll();
        java.util.List<ChatConversation> source=showingGroups?groups:chats;
        for(ChatConversation c:source) {
            JPanel wrap=new JPanel(new BorderLayout());
            wrap.setBackground(Color.WHITE);
            wrap.add(new ConversationItem(c,()->onConversationClick(c)),BorderLayout.CENTER);
            if(showingGroups && !c.member && !c.requestStatus.isEmpty()) {
                JLabel status=Ui.muted("  "+c.requestStatus);
                status.setBorder(new EmptyBorder(0,65,7,0)); wrap.add(status,BorderLayout.SOUTH);
            }
            listPanel.add(wrap);
            listPanel.add(new JSeparator());
        }
        listPanel.revalidate(); listPanel.repaint();
    }

    private void onConversationClick(ChatConversation c) {
        if(c.group && !c.member) {
            if(c.requestStatus.isEmpty()) {
                c.requestStatus="Demande envoyée";
                JOptionPane.showMessageDialog(this,"Votre demande pour rejoindre « "+c.name+" » a été envoyée.");
            } else {
                JOptionPane.showMessageDialog(this,"Demande déjà envoyée pour ce groupe.");
            }
            renderLists();
            return;
        }
        select(c);
    }

    private void select(ChatConversation c) {
        selected=c;
        conversationTitle.setText(c.name);
        conversationSubtitle.setText(c.group ? c.members.size()+" membres" : "Conversation privée");
        detailsButton.setVisible(c.group);
        messagesPanel.removeAll();
        for(ChatMessage m:c.messages) {
            MessageBubble bubble=new MessageBubble(m.text,m.sender,m.date,m.mine);
            bubble.setAlignmentX(Component.LEFT_ALIGNMENT);
            messagesPanel.add(bubble);
        }
        messagesPanel.add(Box.createVerticalGlue());
        messagesPanel.revalidate(); messagesPanel.repaint();
        revalidate();
    }

    private void sendMessage() {
        if(selected==null || messageInput.getText().trim().isEmpty()) return;
        if(selected.group && !selected.member) return;
        String text=messageInput.getText().trim();
        selected.messages.add(new ChatMessage(text,"Moi",LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),true));
        messageInput.setText(""); select(selected);
    }

    private void createGroupDialog() {
        JTextField name=Ui.field("Nom du groupe");
        JLabel preview=Ui.avatar("G",null,52);
        final ImageIcon[] image={null};
        JButton choose=Ui.button("Choisir une image",false);
        choose.addActionListener(e->{
            JFileChooser fc=new JFileChooser();
            if(fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
                image[0]=Ui.loadImage(fc.getSelectedFile(),64);
                preview.setIcon(image[0]); preview.setText("");
            }
        });
        JPanel form=new JPanel(new BorderLayout(10,10));
        JPanel row=new JPanel(new FlowLayout(FlowLayout.LEFT)); row.add(preview); row.add(choose);
        form.add(new JLabel("Nom du groupe"),BorderLayout.NORTH);
        form.add(name,BorderLayout.CENTER); form.add(row,BorderLayout.SOUTH);
        int result=JOptionPane.showConfirmDialog(this,form,"Créer un groupe",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if(result==JOptionPane.OK_OPTION) {
            if(name.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this,"Le nom du groupe est obligatoire."); return; }
            ChatConversation g=new ChatConversation("g"+System.nanoTime(),name.getText().trim(),"Groupe créé",image[0],false,true,true);
            g.admin=true; g.members.add(userName+" (admin)");
            groups.add(0,g); showingGroups=true; renderLists(); select(g);
        }
    }

    private void showDetails() {
        if(selected==null || !selected.group) return;
        JDialog dialog=new JDialog(frame,"Détails — "+selected.name,true);
        dialog.setSize(430,430); dialog.setLocationRelativeTo(frame);
        JPanel content=new JPanel(new BorderLayout(10,10)); content.setBorder(new EmptyBorder(16,16,16,16)); content.setBackground(Color.WHITE);
        JLabel title=new JLabel("Membres ("+selected.members.size()+")"); title.setFont(Theme.MEDIUM);
        DefaultListModel<String> memberModel=new DefaultListModel<>();
        for(String m:selected.members) memberModel.addElement(m);
        JList<String> memberList=new JList<>(memberModel); memberList.setFont(Theme.FONT);
        JPanel top=new JPanel(new BorderLayout()); top.setOpaque(false); top.add(title,BorderLayout.NORTH);
        top.add(new JScrollPane(memberList),BorderLayout.CENTER); content.add(top,BorderLayout.CENTER);

        if(selected.admin) {
            JPanel requests=new JPanel(new BorderLayout(5,5)); requests.setOpaque(false);
            JLabel reqTitle=new JLabel("Demandes en attente ("+selected.requests.size()+")"); reqTitle.setFont(Theme.MEDIUM);
            JPanel reqList=new JPanel(); reqList.setLayout(new BoxLayout(reqList,BoxLayout.Y_AXIS)); reqList.setOpaque(false);
            for(String requester:new ArrayList<>(selected.requests)) {
                JPanel r=new JPanel(new BorderLayout(5,0)); r.setOpaque(false);
                r.add(Ui.label(requester),BorderLayout.CENTER);
                JPanel actions=new JPanel(new FlowLayout(FlowLayout.RIGHT,4,0)); actions.setOpaque(false);
                JButton accept=Ui.button("Accepter",true), refuse=Ui.button("Refuser",false);
                accept.setFont(new Font("SansSerif",Font.BOLD,10)); refuse.setFont(new Font("SansSerif",Font.BOLD,10));
                accept.addActionListener(e->{selected.requests.remove(requester);selected.members.add(requester);refreshDetails(dialog);});
                refuse.addActionListener(e->{selected.requests.remove(requester);refreshDetails(dialog);});
                actions.add(accept); actions.add(refuse); r.add(actions,BorderLayout.EAST);
                reqList.add(r); reqList.add(Box.createVerticalStrut(8));
            }
            requests.add(reqTitle,BorderLayout.NORTH); requests.add(new JScrollPane(reqList),BorderLayout.CENTER);
            requests.setPreferredSize(new Dimension(380,145)); content.add(requests,BorderLayout.SOUTH);
        }
        dialog.setContentPane(content); dialog.setVisible(true);
    }

    private void refreshDetails(JDialog dialog) {
        dialog.dispose();
        showDetails();
        select(selected);
    }
}
