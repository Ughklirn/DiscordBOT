import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class UghklirnImpl implements Ughklirn {
    public static void main(String[] args) {
        JDABuilder builder = JDABuilder.createDefault(args[0]);

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Disable compression (not recommended)
        builder.setCompression(Compression.NONE);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching("TV"));

        try {
            builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent evt) {
        User objUser = evt.getAuthor();
        MessageChannel objMsgCh = evt.getChannel();
        Message objMsg = evt.getMessage();
        String content = objMsg.getContentRaw();
        Guild guild = evt.getGuild();

        //the prefix to which the bot responds to is "Mr.Meeseeks, "
        if (objMsg.getContentRaw().equalsIgnoreCase(Ref2.prefix + "I need help")) {
            objMsgCh.sendMessage("Hi, " + objUser.getAsMention() + ", " + " I'm Mr.Meeseeks! Look at me! How can I help?").queue();

            objMsgCh.sendMessage("You can tell me to **ADD** you to a role, or **REMOVE** you from a role!").queue();

            // Test for "Mr", "Meeseeks", "add", and "me".
        } else if (content.matches("^(?=.*\\badd\\b)(?=.*\\bme\\b)(?=.*\\bto\\b)(?=.*\\bMr\\b)(?=.*\\bMeeseeks\\b).+")) {
            // Test for pronouns (Assuming your roles names are "he/him" and "she/her")
            Role group = content.matches("((she)|(her))") ? guild.getRolesByName("she/her", true).get(0) :
                    content.matches("((he)|(him))") ? guild.getRolesByName("he/him", true).get(0) : null;
            if (group == null) {
                // Let the user know if they used an invalid pronoun.
                objMsgCh.sendMessage("Sorry " + objUser.getAsMention() + ", I can't find that role!").queue();
            } else {
                // Assign the role.
                guild.getController().addRolesToMember​(guild.getMember(objUser), group);
                objMsgCh.sendMessage("Added " + objUser.getAsMention() + " to " + group.getName() + "!").queue();
            }
        }
    }
}
