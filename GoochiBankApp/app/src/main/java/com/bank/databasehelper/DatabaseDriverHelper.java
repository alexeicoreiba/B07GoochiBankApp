package com.bank.databasehelper;

import java.sql.Connection;

import com.bank.exceptions.ConnectionFailedException;

public class DatabaseDriverHelper extends DatabaseDriver {
  protected static Connection connectOrCreateDataBase() {
    return DatabaseDriver.connectOrCreateDataBase();
  }

  public static Connection reMake() throws ConnectionFailedException {
    return DatabaseDriver.reInitialize();
  }
}
