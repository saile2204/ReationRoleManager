package net.ddns.saile2204;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandListener extends ListenerAdapter {

    String prefix = "!";
    public ArrayList<ReactionRole> reactionRoles = new ArrayList<ReactionRole>();
    //public ArrayList<message> messages = new ArrayList<message>();
    public ArrayList<String> mutes = new ArrayList<String>();


    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        MessageChannel messageChannel = event.getChannel();


        for(String item : mutes){
            if(item.equals(event.getAuthor().getId())){
                event.getMessage().delete().queue();
            }
        }


        if(event.getMessage().getContentRaw().startsWith(prefix)){
            String[] command = event.getMessage().getContentRaw().split(" ");

            if(command[0].startsWith(prefix)){
                if(command[0].substring(1).equals("setRole")){
                    setRole(event, command);
                }
                else if(command[0].substring(1).equals("addReactionRole")){
                    addReactionRole(event, command);
                }
                else if(command[0].substring(1).equals("setEmoji")){
                    setEmoji(event, command);
                }
                else if(command[0].substring(1).equals("setMessageID")){
                    setMessageID(event, command);
                }
                else if(command[0].substring(1).equals("getData")){
                    getData(event, command);
                }
                else if(command[0].substring(1).equals("removeReactionRole")){
                    removeReactionRole(command);
                }
                else if(command[0].substring(1).equals("mute") && event.getAuthor().getId().equals("275899874858500096")){
                    mute(event, command);
                }
                else if(command[0].substring(1).equals("unmute") && event.getAuthor().getId().equals("275899874858500096")){
                    unmute(event);
                }
                else if(command[0].substring(1).equals("fclear")){
                    clear(event, command);
                }
                else if(command[0].substring(1).equals("spam")){
                    spam(event, command);

                }
                else if(command[0].substring(1).equals("bye") && event.getAuthor().getId().equals("275899874858500096")){
                    event.getJDA().shutdown();
                }

            }


        }
    }


    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {

        System.out.println(event.getReactionEmote().getId());

        for (ReactionRole reactionRole : reactionRoles) {
            Role role = event.getGuild().getRolesByName(reactionRole.getName(), false).get(0);
            String name = reactionRole.getName();
            String messageID = reactionRole.getMessageID();
            //Emote emote = event.getJDA().getEmoteById(reactionRole.getEmoji());
            //System.out.println(event.getReactionEmote().getId());

            if(event.isFromType(ChannelType.TEXT)){
                System.out.println("Type Right");
                if(event.getMessageId().contentEquals(messageID)){
                    System.out.println("IDRight" + event.getReactionEmote().getId());
                    if(event.getReactionEmote().getId().equals(reactionRole.getEmoji())){
                        System.out.println("EmojiRight");


                        System.out.println(event.getMember().getNickname() + "has Received Role " + reactionRole.getRole());
                        event.getChannel().sendMessage(event.getMember().getNickname() + "has Received Role " + role.getName()).queue();
                        event.getGuild().addRoleToMember(event.getMember(), role).queue();


                    }

                }
            }

        }

    }

    public void addReactionRole(GuildMessageReceivedEvent event, String[] commandVar){

        String name;

        if(commandVar[1] != null){
            name = commandVar[1];
        }
        else{
            event.getChannel().sendMessage("Error wrong syntax").queue();
            return;
        }


        for(ReactionRole item: reactionRoles){
            if(item.getName().equals(name)){
                event.getChannel().sendMessage("Already Exitst").queue();
                return;
            }
        }
        reactionRoles.add(new ReactionRole(name));
        event.getChannel().sendMessage("Role added").queue();
    }

    private void setRole(GuildMessageReceivedEvent event, String[] commandVar){

        String name;
        String role;

        if(commandVar[1] != null && commandVar[2] != null){
            name = commandVar[1];
            role = commandVar[2];
        }
        else{
            event.getChannel().sendMessage("Error wrong syntax").queue();
            return;
        }


        for (ReactionRole item  : reactionRoles) {
            if(item.getName().equals(name)){
                item.setRole(role);
                event.getChannel().sendMessage("Role Set").queue();
            }
        }

    }

    private void setEmoji(GuildMessageReceivedEvent event, String[] commandVar){

        String name;
        String emoji;

        if(commandVar[1] != null && commandVar[2] != null){
            name = commandVar[1];
            emoji = commandVar[2];
        }
        else{
            event.getChannel().sendMessage("Error wrong syntax").queue();
            return;
        }


        for (ReactionRole item  : reactionRoles) {
            if(item.getName().equals(name)){
                item.setEmoji(emoji);
                event.getChannel().sendMessage("Emoji Set").queue();
            }
        }

    }

    private void setMessageID(GuildMessageReceivedEvent event, String[] commandVar){

        String name;
        String messageID;

        if(commandVar[1] != null && commandVar[2] != null){
            name = commandVar[1];
            messageID = commandVar[2];
        }
        else{
            event.getChannel().sendMessage("Error wrong syntax").queue();
            return;
        }


        for (ReactionRole item  : reactionRoles) {
            if(item.getName().equals(name)){
                item.setMessageID(messageID);
                event.getChannel().sendMessage("ID").queue();
            }
        }


    }

    public void getData(GuildMessageReceivedEvent event, String[] commandVar){

        String name;

        if(commandVar[1] != null){
            name = commandVar[1];
        }
        else{
            event.getChannel().sendMessage("Error wrong syntax").queue();
            return;
        }


        for(ReactionRole item: reactionRoles){
            if(item.getName().equals(name)){
                event.getChannel().sendMessage("Name = " + item.getName()
                        + " Role = " + item.getRole()
                        + " Emoji = " + item.getEmoji()
                        + " MessageID = " + item.getMessageID()).queue();
                return;
            }
        }
    }

    public void removeReactionRole(String[] commandVar){

        for (int i = 0; i <= reactionRoles.size(); i++) {
            if(reactionRoles.get(i).getName().equals(commandVar[1])){
                reactionRoles.remove(i);
            }
        }

    }

    public void clear(GuildMessageReceivedEvent event, String[] commandVar){
        List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(commandVar[1])).complete();

        for (Message message : messages){

            event.getChannel().deleteMessageById(message.getId()).queue();
        }
        System.out.println("Channel Cleared");

    }

    public void mute(GuildMessageReceivedEvent event, String[] commandVar){
        String ID = commandVar[1];

        for (int i = 0; i < mutes.size(); i++) {
            if(ID == event.getAuthor().getId()){
                return;
            }
        }
        mutes.add(ID);
        event.getChannel().sendMessage("User " + event.getJDA().getUserById(ID).getName() + " has bin mutet.").queue();
    }

    public void unmute(GuildMessageReceivedEvent event){
        mutes.clear();
        event.getChannel().sendMessage("All Users Umutet").queue();
    }

    public void spam(GuildMessageReceivedEvent event, String[] commandVar){
        for (int i = 0; i < Integer.parseInt(commandVar[2]); i++) {
            event.getChannel().sendMessage(commandVar[1]).queue();
        }
    }

    ArrayList<ReactionRole> getReactionRoles(){
        return reactionRoles;
    }

}
