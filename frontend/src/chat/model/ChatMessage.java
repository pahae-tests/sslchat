package chat.model;

public class ChatMessage {
    public final String text, sender, date;
    public final boolean mine;

    public ChatMessage(String text, String sender, String date, boolean mine) {
        this.text = text;
        this.sender = sender;
        this.date = date;
        this.mine = mine;
    }
}
