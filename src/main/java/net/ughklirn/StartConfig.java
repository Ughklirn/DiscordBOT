package net.ughklirn;

import net.ughklirn.connections.Database;
import net.ughklirn.connections.IDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StartConfig {
    private static final Logger L = LoggerFactory.getLogger(StartConfig.class);
    private static String id = "809702674756665414";

    public static void main(String[] args) {
        IDatabase db = Database.getInstance();
        db.connect();
        try {
            ResultSet rs = db.getConnection().createStatement().executeQuery("select * from initialize");
            while (rs.next()) {
                System.err.println("BOT: " + rs.getString("BOT"));
                System.err.println("TOKEN: " + rs.getString("TOKEN"));
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
