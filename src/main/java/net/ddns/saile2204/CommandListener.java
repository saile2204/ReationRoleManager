package net.ddns.saile2204;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CommandListener extends ListenerAdapter {

    String prefix = "!";
    public ArrayList<ReactionRole> reactionRoles = new ArrayList<ReactionRole>();

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        MessageChannel messageChannel = event.getChannel();

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
                else if(command[0].substring(1).equals("bye")){
                    event.getJDA().shutdown();
                }
            }


        }
    }


    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {

        System.out.println(event.getReactionEmote());

        for (ReactionRole reactionRole : reactionRoles) {
            String name = reactionRole.getName();
            String role = reactionRole.getRole();
            String messageID = reactionRole.getMessageID();
            String emoji = reactionRole.getEmoji();


            if(event.getMessageId().contentEquals(messageID)){
                System.out.println("soos");


                event.getJDA().getRolesByName(role, false);
                event.getGuild().addRoleToMember(event.getMember(), event.getJDA().getRolesByName(role, false).get(1)).queue();
                event.getChannel().sendMessage("Role " + role + " added to " + event.getMember());

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



    ArrayList<ReactionRole> getReactionRoles(){
        return reactionRoles;
    }

}
