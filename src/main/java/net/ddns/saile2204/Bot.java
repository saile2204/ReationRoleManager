package net.ddns.saile2204;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Bot {

    String token = "NzYxNjQwNjU1OTU2MjEzODIw.X3djIg.9HqXhpHp3rBXh3mvVyi6plPBzzY";


    public Bot() throws LoginException {
        JDA api = JDABuilder.createDefault(token)
                .addEventListeners(new CommandListener())
                .setActivity(Activity.playing("Wartet auf Reaction"))
                .build();
    }

    public static void main(String[] args) throws LoginException {
        new Bot();
    }





}
