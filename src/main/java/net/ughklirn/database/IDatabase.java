package net.ughklirn.database;

import java.sql.Connection;

/**
 * @author Dustin Eikmeier
 * @version 1.0
 * @since 1.8
 */

public interface IDatabase {
    boolean connect();

    boolean disconnect();

    Connection getConnection();

    void fillDefault(String id);
}
