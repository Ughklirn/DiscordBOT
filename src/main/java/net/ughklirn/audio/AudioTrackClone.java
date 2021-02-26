package net.ughklirn.audio;

import java.time.Duration;

public class AudioTrackClone {
    public static String title;
    public static String author;
    public static String uri;
    public static Duration length;

    public static String getTrackInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("**TRACK INFO**\n");
        sb.append("\t**TITLE:** " + title + "\n");
        sb.append("\t**AUTHOR:** " + author + "\n");
        sb.append("\t**LENGTH:** " + length + "\n");
        sb.append("\t**URI:** <" + uri + ">\n");
        return sb.toString();
    }
}
