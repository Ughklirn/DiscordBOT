package net.ughklirn.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.dv8tion.jda.api.entities.Guild;

import java.io.*;
import java.security.CodeSource;
import java.util.*;

public class Config {
    private static Config INSTANCE = null;
    private String botToken;
    private int botMusic_Volume;
    private List<String> lTextChannel_Commands_Admins;
    private List<String> lTextChannel_Commands_Moderation;
    private List<String> lTextChannel_Commands_Users;
    private List<String> lTextChannel_Commands_Music;
    private List<String> lTextChannel_Commands_AutoVoiceChannel;
    private Map<RoleType, List<String>> mTextChannel_Commands_Roles;
    private Map<Guild, Integer> mTextChannel_Commands_Music_Volume;
    private File file;

    private Config() {
        this.file = new File("config.json");
        this.INSTANCE = this;
        this.botToken = this.readToken();
        this.botMusic_Volume = 100;
        this.lTextChannel_Commands_Admins = new LinkedList<>();
        this.lTextChannel_Commands_Moderation = new LinkedList<>();
        this.lTextChannel_Commands_Users = new LinkedList<>();
        this.lTextChannel_Commands_Music = new LinkedList<>();
        this.lTextChannel_Commands_AutoVoiceChannel = new LinkedList<>();
        this.mTextChannel_Commands_Roles = new LinkedHashMap<>();
        this.mTextChannel_Commands_Music_Volume = new HashMap<>();
        this.readFiles();
        System.out.println("Create :/");
        if (!file.exists()) {
            this.save();
        }
    }

    public Config(Config c) {
        c.botToken = botToken;
        c.botMusic_Volume = botMusic_Volume;
        c.lTextChannel_Commands_Admins = lTextChannel_Commands_Admins;
        c.lTextChannel_Commands_Moderation = lTextChannel_Commands_Moderation;
        c.lTextChannel_Commands_Users = lTextChannel_Commands_Users;
        c.lTextChannel_Commands_Music = lTextChannel_Commands_Music;
        c.lTextChannel_Commands_AutoVoiceChannel = lTextChannel_Commands_AutoVoiceChannel;
        c.mTextChannel_Commands_Roles = mTextChannel_Commands_Roles;
        c.mTextChannel_Commands_Music_Volume = mTextChannel_Commands_Music_Volume;
        c.file = file;
    }

    public static Config getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Config();
        }
        return INSTANCE;
    }

    public void save() {
        file.delete();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonConfig = gson.toJson(this);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write(jsonConfig);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Config load() throws FileNotFoundException {
        if (!file.exists()) {
            System.err.println("No File!");
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        INSTANCE = (Config) gson.fromJson(br, Config.class);
        return INSTANCE;
    }

    public int getBotMusic_Volume() {
        return botMusic_Volume;
    }

    public void setBotMusic_Volume(int botMusic_Volume) {
        this.botMusic_Volume = botMusic_Volume;
    }

    public Map<Guild, Integer> getTextChannel_Music_Volume() {
        return mTextChannel_Commands_Music_Volume;
    }

    public void setVolume(Guild guild, Integer volume) {
        boolean wasFound = false;
        for (Guild k1 : this.mTextChannel_Commands_Music_Volume.keySet()) {
            if (k1.equals(guild)) {
                this.mTextChannel_Commands_Music_Volume.replace(guild, volume);
                wasFound = true;
//                System.out.println("was found :)");
            }
        }

        if (!wasFound) {
            this.mTextChannel_Commands_Music_Volume.put(guild, volume);
//            System.out.println("was not found :(");
        }

//        System.out.println(this.mTextChannel_Commands_Music_Volume.get(guild));
//        System.out.println("+++ ### +++");
//        for (Guild k : this.mTextChannel_Commands_Music_Volume.keySet()) {
//            System.out.println("Keys: " + k);
//        }
//        System.out.println("### +++ ###");
//        for (Integer k : this.mTextChannel_Commands_Music_Volume.values()) {
//            System.out.println("Values: " + k);
//        }
//        System.out.println("### ### ###");
        System.out.println(guild);
    }

    public String getBotToken() {
        return botToken;
    }

    public List<String> getTextChannels_Commands_Admins() {
        return lTextChannel_Commands_Admins;
    }

    public List<String> getTextChannels_Commands_Moderation() {
        return lTextChannel_Commands_Moderation;
    }

    public List<String> getTextChannels_Commands_Users() {
        return lTextChannel_Commands_Users;
    }

    public Map<RoleType, List<String>> getTextChannels_Commands_Roles() {
        return mTextChannel_Commands_Roles;
    }

    public List<String> getTextChannels_Commands_Music() {
        return lTextChannel_Commands_Music;
    }

    public List<String> getTextChannels_Commands_AutoVoiceChannel() {
        return lTextChannel_Commands_AutoVoiceChannel;
    }

    private String readToken() {
        if (file.exists()) {

        }
        BufferedReader br = null;
        FileReader fr = null;
        List<String> lToken = new ArrayList<>();

        try {
            fr = new FileReader(this.getJarPathWithoutJar() + DiscordCred.BOT_PATH_KEY);
            br = new BufferedReader(fr);
            String game;
            while ((game = br.readLine()) != null) {
                lToken.add(game);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lToken.get(0);
        }
    }

    private void readFiles() {
        this.mTextChannel_Commands_Roles.put(RoleType.GAMES, new LinkedList<>());
        this.mTextChannel_Commands_Roles.put(RoleType.CLANS, new LinkedList<>());


        BufferedReader br = null;
        FileReader fr = null;
        try {

            /*
             * Roles: Games
             */

            fr = new FileReader(this.getJarPathWithoutJar() + DiscordCred.BOT_PATH_GAMES);
            br = new BufferedReader(fr);
            String game;
            while ((game = br.readLine()) != null) {
                mTextChannel_Commands_Roles.get(RoleType.GAMES).add(game);
            }

            /*
             * Roles: Clans
             */

            fr = new FileReader(this.getJarPathWithoutJar() + DiscordCred.BOT_PATH_CLAN);
            br = new BufferedReader(fr);
            String clan;
            while ((clan = br.readLine()) != null) {
                mTextChannel_Commands_Roles.get(RoleType.CLANS).add(clan);
            }

            /*
             * tChannel: Music
             */

            fr = new FileReader(this.getJarPathWithoutJar() + DiscordCred.BOT_PATH_CHANNELS_TEXT_MUSIC);
            br = new BufferedReader(fr);
            String music;
            while ((music = br.readLine()) != null) {
                lTextChannel_Commands_Music.add(music);
            }


            /*
             * tChannel: Admin
             */

            fr = new FileReader(this.getJarPathWithoutJar() + DiscordCred.BOT_PATH_CHANNELS_TEXT_ADMIN);
            br = new BufferedReader(fr);
            String admin;
            while ((admin = br.readLine()) != null) {
                lTextChannel_Commands_Admins.add(admin);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Config loadMe() throws FileNotFoundException {
        File file = new File(this.getJarPathWithoutJar() + "/config.json");
        if (!file.exists()) {
            System.err.println("No File!");
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        return gson.fromJson(br, Config.class);
    }

    //gibt den Pfad dieser Jar-Datei zurück
    private String getCurrentJarPath() {
        String path = getJarPath();
        if (path.endsWith(".jar")) {
            return path.substring(0, path.lastIndexOf("/"));
        }
        return path;
    }

    //gibt den absoluten Pfad inklusive Dateiname dieser Anwendung zurück
    private String getJarPath() {
        final CodeSource source = this.getClass().getProtectionDomain().getCodeSource();
        if (source != null) {
            return source.getLocation().getPath().replaceAll("%20", " ");
        }
        return null;
    }

    private String getJarPathWithoutJar() {
        final CodeSource source = this.getClass().getProtectionDomain().getCodeSource();
        if (source != null) {
            return source.getLocation().getPath().replaceAll("%20", " ").replace("DiscordBOT.jar", "").replace("build/classes/java/main", "");
        }
        return null;
    }

}
