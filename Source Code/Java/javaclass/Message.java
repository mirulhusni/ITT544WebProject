package javaclass;

public class Message {
    
    public Integer Message_ID;
    public String MessageSender;
    public String MessageReceiver;
    public String MessageContent;
    
    public Message(){
    }

    public Message(Integer Message_ID, String MessageSender, String MessageReceiver, String MessageContent) {
        this.Message_ID = Message_ID;
        this.MessageSender = MessageSender;
        this.MessageReceiver = MessageReceiver;
        this.MessageContent = MessageContent;
    }

    public Integer getMessage_ID() {
        return Message_ID;
    }

    public String getMessageSender() {
        return MessageSender;
    }

    public String getMessageReceiver() {
        return MessageReceiver;
    }

    public String getMessageContent() {
        return MessageContent;
    }

    public void setMessageID(Integer Message_ID) {
        this.Message_ID = Message_ID;
    }

    public void setMessageSender(String MessageSender) {
        this.MessageSender = MessageSender;
    }

    public void setMessageReceiver(String MessageReceiver) {
        this.MessageReceiver = MessageReceiver;
    }

    public void setMessageContent(String MessageContent) {
        this.MessageContent = MessageContent;
    }
}
