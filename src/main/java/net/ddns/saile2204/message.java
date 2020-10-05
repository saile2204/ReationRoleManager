package net.ddns.saile2204;

import net.dv8tion.jda.api.entities.MessageChannel;

public class message {

    private String messageID;
    private MessageChannel channel;

    public message(MessageChannel channel, String messageID){
        this.channel = channel;
        this.messageID = messageID;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public void setChannel(MessageChannel channel) {
        this.channel = channel;
    }
}
