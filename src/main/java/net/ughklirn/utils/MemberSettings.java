package net.ughklirn.utils;

import net.dv8tion.jda.api.entities.Guild;

public class MemberSettings {
    private Guild guild;
    public String prefix;
    public int volume;

    public MemberSettings(Guild guild) {
        this.guild = guild;
    }
}
