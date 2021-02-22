package net.ughklirn;

import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

public class Start {
    private ShardManager sm;

    public static void main(String[] args) throws LoginException {
        BOT bot = new BOTImpl();
        bot.run();
        //Start s = new Start();
    }

//    public Start() throws LoginException {
//        DefaultShardManager b = new DefaultShardManager(DiscordCred.BOT_TOKEN);
//        b.login();
//        b.setStatus(OnlineStatus.ONLINE);
//        b.setActivity(Activity.playing("Ughklirn!"));
//
//
//        this.sm = b;
//        System.out.println("Online :)");
//        this.shutdown();
//    }
//
//    private void shutdown() {
//        new Thread(() -> {
//            String line = "";
//            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//            try {
//                while (((line = br.readLine()) != null)) {
//                    if (line.equals(DiscordCred.BOT_EXIT)) {
//                        System.out.println("Exit!");
//                        if (sm != null) {
//                            sm.setStatus(OnlineStatus.OFFLINE);
//                            sm.shutdown();
//                            System.out.println("Offline :(");
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
}
