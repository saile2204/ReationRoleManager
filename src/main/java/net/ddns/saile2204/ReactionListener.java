package net.ddns.saile2204;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReactionListener extends ListenerAdapter {


    CommandListener listener;

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        ArrayList<ReactionRole> reactionRoles = listener.getReactionRoles();
        System.out.println(event.getReactionEmote());

        for (ReactionRole reactionRole : reactionRoles) {
            String name = reactionRole.getName();
            String role = reactionRole.getRole();
            String messageID = reactionRole.getMessageID();
            String emoji = reactionRole.getEmoji();


            if(event.getMessageId().equals(messageID)
                    && event.getReactionEmote().equals(event.getJDA().getEmotesByName(emoji, false))){

                event.getJDA().getRolesByName(role, false);
                event.getGuild().addRoleToMember(event.getMember(), event.getJDA().getRolesByName(role, false).get(1)).queue();
                event.getChannel().sendMessage("Role " + role + " added to " + event.getMember());

            }

        }

    }
}
