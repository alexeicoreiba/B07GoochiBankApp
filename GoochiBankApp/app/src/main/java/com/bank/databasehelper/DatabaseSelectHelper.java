package com.bank.databasehelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.account.BalanceOwingAccount;
import com.bank.account.ChequingAccount;
import com.bank.account.RestrictedSavingsAccount;
import com.bank.account.SavingsAccount;
import com.bank.account.Tfsa;
import com.bank.user.Admin;
import com.bank.user.Customer;
import com.bank.user.Teller;
import com.bank.user.User;
import com.bank.account.Account;

public class DatabaseSelectHelper extends DatabaseSelector {

  /**
   * Gets the role with id
   * 
   * @param id the id of the role
   * @return the string containing the role
   */
  public static String getRole(int id) {
    // open the connection to the database
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    String role = null;
    // try to get the role of from the id, otherwise catch an exception
    try {
      role = getRole(id, connection);
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return role;
  }

  /**
   * get the hashed version of the password.
   * 
   * @param userId the user's id.
   * @return the hashed password to be checked against given password.
   */
  public static String getPassword(int userId) {
    // open the connection to the database
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    String hashPassword = null;
    try {
      // try to get the password from the database, otherwise catch exception
      hashPassword = getPassword(userId, connection);
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return hashPassword;
  }

  /**
   * Get a User object from the userId.
   * 
   * @param userId the id of the user
   * @return a User object with details about the user from the database
   */
  public static User getUserDetails(int userId) {
    // open the connection to the database
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = null;
    User user = null;
    try {
      // get the result set based on the userId
      results = getUserDetails(userId, connection);
      // for each result get the user details
      while (results.next()) {
        // get the role name
        int roleId = results.getInt("ROLEID");
        String role = getRole(roleId);
        // check which user it is and create the users
        if (role.equals("ADMIN")) {
          user = new Admin(userId, results.getString("NAME"), results.getInt("AGE"),
              results.getString("ADDRESS"));
        } else if (role.equals("TELLER")) {
          user = new Teller(userId, results.getString("NAME"), results.getInt("AGE"),
              results.getString("ADDRESS"));
        } else if (role.equals("CUSTOMER")) {
          user = new Customer(userId, results.getString("NAME"), results.getInt("AGE"),
              results.getString("ADDRESS"));
        }
      }
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return user;
  }

  /**
   * Get a list of accountIds associated with the userID
   * 
   * @param userId the id of the user
   * @return list of accountIds associated with the userID
   */
  public static List<Integer> getAccountIds(int userId) {
    List<Integer> accountList = new ArrayList<Integer>();
    // Uses userId to get account id and adds account ids to account list if userId is greater than
    // 0
    if (userId > 0) {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results;
      try {
        results = getAccountIds(userId, connection);
        while (results.next()) {
          // System.out.println(results.getInt("ID"));
          accountList.add(results.getInt("ACCOUNTID"));
        }
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    // Return list of accounts
    return accountList;
  }

  /**
   * . Get an Account object from the account Id
   * 
   * @param accountId the id of the account
   * @return an Account object created with the details of the account.
   */
  public static Account getAccountDetails(int accountId) {
    // open the connection to the database
    // Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    Account account = null;
    if (accountId > 0) {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results;
      try {
        results = getAccountDetails(accountId, connection);
        while (results.next()) {
          // Get account type from database
          String accountTypeName = getAccountTypeName(accountId);
          // If account type is CHEQUING, set account to chequing with corresponding account details
          if ("CHEQUING".equals(accountTypeName)) {
            account = new ChequingAccount(results.getInt("ID"), results.getString("NAME"),
                new BigDecimal(results.getString("BALANCE")));
            // If account type is SAVING, set account to saving with corresponding account details
          } else if ("SAVING".equals(accountTypeName)) {
            account = new SavingsAccount(results.getInt("ID"), results.getString("NAME"),
                new BigDecimal(results.getString("BALANCE")));
            // If account type is TFSA, set account to tfsa with corresponding account details
          } else if ("TFSA".equals(accountTypeName)) {
            account = new Tfsa(results.getInt("ID"), results.getString("NAME"),
                new BigDecimal(results.getString("BALANCE")));
            // If account type is RESTRICTEDSAVING, set account to saving with corresponding
            // account details
          } else if ("RESTRICTEDSAVING".equals(accountTypeName)) {
            account = new RestrictedSavingsAccount(results.getInt("ID"), results.getString("NAME"),
                new BigDecimal(results.getString("BALANCE")));
            // If account type is BALANCEOWING, set account to saving with corresponding
            // account details
          } else if ("BALANCEOWING".equals(accountTypeName)) {
            account = new BalanceOwingAccount(results.getInt("ID"), results.getString("NAME"),
                new BigDecimal(results.getString("BALANCE")));
          }
        }
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      // Return account with account details
      return account;
    }
    return null;
  }

  /**
   * Gets the current balance in the account.
   * 
   * @param accountId
   * @return the current balance of the account in the database
   */
  public static BigDecimal getBalance(int accountId) {
    // open the connection to the database
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    BigDecimal balance = null;
    // try to get the balance of the account from the database, otherwise catch exception
    try {
      balance = getBalance(accountId, connection);
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return balance;
  }

  /**
   * Get the current interest associated with the accountType.
   * 
   * @param accountType the id of the accountType
   * @return the current interest associated with the accountType.
   */
  public static BigDecimal getInterestRate(int accountType) {
    // open the connection to the database
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    BigDecimal interestRate = null;

    try {
      interestRate = getInterestRate(accountType, connection);
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return interestRate;
  }

  /**
   * Get a list of ids of account types.
   * 
   * @return a list of ids of account types.
   */
  public static List<Integer> getAccountTypesIds() {
    // open the connection to the database
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = null;
    List<Integer> ids = new ArrayList<>();
    // try to get the result set of all account types
    try {
      results = getAccountTypesId(connection);
      while (results.next()) {
        // add each id from the result set to the list
        ids.add(results.getInt("ID"));
      }
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ids;
  }

  /**
   * Get the name of the account type associated with the id
   * 
   * @param accountTypeId the id of the account type
   * @return the name of the account type associated with the id
   */
  public static String getAccountTypeName(int accountTypeId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    String name = null;
    // try to get the name of the account type, otherwise catch an exception
    try {
      name = getAccountTypeName(accountTypeId, connection);
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return name;
  }

  /**
   * Get a list of roles.
   * 
   * @return a list of roles.
   */
  public static List<Integer> getRoles() {
    // open the connection to the database
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = null;
    List<Integer> ids = new ArrayList<>();
    // try to get the result set of all the roles
    try {
      results = getRoles(connection);
      while (results.next()) {
        // add each id into the list of id's
        // System.out.println(results.getInt("ID"));
        ids.add(results.getInt("ID"));
      }
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ids;
  }

  /**
   * Get the account type Id associated with that account.
   * 
   * @param accountId the id of the account.
   * @return the account type Id associated with that account.
   */
  public static int getAccountType(int accountId) {
    // open the connection to the database
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int type1 = -1;
    // try and get the type id of the account, otherwise catch an exception
    try {
      type1 = getAccountType(accountId, connection);
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return type1;
  }

  /**
   * Get the roleId of the user.
   * 
   * @param userId the id of the user.
   * @return the roleId of the user.
   */
  public static int getUserRole(int userId) {
    // open the connection to the database
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int role = -1;
    // try and get the roleId of the user, otherwise catch an exception
    try {
      role = getUserRole(userId, connection);
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return role;
  }

  /**
   * Get all messageIds currently available to a user.
   * @param userId the user whose messages are being retrieved.
   * @return a list of IDs of the messages.
   */
  public static List<Integer> getAllMessageIds(int userId) {
    // open the connection to the database
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    List<Integer> messageIdList = new ArrayList<>();
    try {
      ResultSet result = getAllMessages(userId, connection);
      while (result.next()) {
        messageIdList.add(result.getInt("ID"));
      }
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return messageIdList;
  }
  
  /**
   * Get all messages currently available to a user.
   * @param userId the user whose messages are being retrieved.
   * @return a list of messages currently in the database.
   */
  public static List<String> getAllMessages(int userId) {
    // open the connection to the database
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    List<String> messageList = new ArrayList<>();
    try {
      ResultSet result = getAllMessages(userId, connection);
      while (result.next()) {
        messageList.add(result.getString("MESSAGE"));
      }
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return messageList;
  }
  
  /**
   * Get all message statuses.
   * @param userId the user whose message statuses are being retrieved.
   * @return a list of message statuses.
   */
  public static List<Integer> getAllMessageStatuses(int userId) {
    // open the connection to the database
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    List<Integer> messageStatusList = new ArrayList<>();
    try {
      ResultSet result = getAllMessages(userId, connection);
      while (result.next()) {
        messageStatusList.add(result.getInt("VIEWED"));
      }
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return messageStatusList;
  }
  
  /**
   * Get a specific message from the database.
   * @param messageId the id of the message.
   * @return the message from the database as a string.
   */
  public static String getSpecificMessage(int messageId) {
    // open the connection to the database
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    String message = "";
    try {
      message = getSpecificMessage(messageId, connection);
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return message;
  }
}
