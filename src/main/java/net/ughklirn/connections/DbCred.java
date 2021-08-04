package net.ughklirn.connections;

public interface DbCred {
    /**
     * Credentials for simulated database.
     */

    final String driverClass = "org.postgresql.Driver";
    final String url = "jdbc:postgresql://192.168.178.21:5432/discord";
    final String user = "discord";
    final String password = "discord";
}
