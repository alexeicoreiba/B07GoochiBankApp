package com.bank.bank;

import com.bank.exceptions.ConnectionFailedException;

import java.sql.Connection;

public class DatabaseDriverExtender extends DatabaseDriver {

  /**
   * Connect to existing database or create it if it's not there
   * 
   * @return database connection
   */
  protected static Connection connectOrCreateDataBase() {
    return DatabaseDriver.connectOrCreateDataBase();
  }

  /**
   * Initialize database
   * 
   * @param connection
   * @return connection passed in
   * @throws ConnectionFailedException
   */
  protected static Connection initialize(Connection connection) throws ConnectionFailedException {
    return DatabaseDriver.initialize(connection);
  }

}
