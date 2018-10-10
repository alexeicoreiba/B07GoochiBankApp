package com.bank.databasehelper;

import com.bank.exceptions.InvalidNameException;
import com.bank.generics.EnumMapAccountTypes;
import com.bank.generics.AccountTypes;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseUpdateHelper extends DatabaseUpdater {

  /**
   * Update the role name of a given role in the role table.
   * 
   * @param name the new name of the role.
   * @param id the current ID of the role.
   * @return true if successful, false otherwise.
   * @throws InvalidNameException if the given name is null
   */
  public static boolean updateRoleName(String name, int id) throws InvalidNameException {
    // check if name is null
    if (name.equals(null)) {
      throw new InvalidNameException();
    }
    // initialize the connection
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    // update the name
    boolean complete = updateRoleName(name, id, connection);
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return complete;
  }

  /**
   * Use this to update the user's name.
   * 
   * @param name the new name
   * @param id the current id
   * @return true if it works, false otherwise.
   * @throws InvalidNameException is the inputed name is null.
   */
  public static boolean updateUserName(String name, int id) throws InvalidNameException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    // check if inputed name is null
    if (name.equals(null)) {
      throw new InvalidNameException();
    }
    // run helper to update the user name
    boolean complete = updateUserName(name, id, connection);
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return complete;
  }

  /**
   * Use this to update the user's age.
   * 
   * @param age the new age.
   * @param id the current id
   * @return true if it succeeds, false otherwise.
   */
  public static boolean updateUserAge(int age, int id) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    // check if inputed age is less than 0
    if (age < 0) {
      return false;
    }
    // run the helper to update the age of the user
    boolean complete = updateUserAge(age, id, connection);
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return complete;
  }

  /**
   * update the role of the user.
   * 
   * @param roleId the new role.
   * @param id the current id.
   * @return true if successful, false otherwise.
   */
  public static boolean updateUserRole(int roleId, int id) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    // get the list of roles from the database
    List<Integer> listA = new ArrayList<>();
    listA = DatabaseSelectHelper.getRoles();
    boolean exists1 = false;
    // if the inputed roleId is not in the list
    for (int i = 0; i < listA.size(); i++) {
      if ((roleId == listA.get(i))) {
        exists1 = true;
      }
    }
    if (exists1 == false) {
      return false;
    }
    // update the user role
    boolean complete = updateUserRole(roleId, id, connection);
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return complete;
  }

  /**
   * Use this to update user's address.
   * 
   * @param address the new address.
   * @param id the current id.
   * @return true if successful, false otherwise.
   */
  public static boolean updateUserAddress(String address, int id) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    // check if address is within 100 character limit
    if (!(address.length() <= 100)) {
      return false;
    }
    boolean complete = updateUserAddress(address, id, connection);
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return complete;
  }

  /**
   * update the name of the account.
   * 
   * @param name the new name for the account.
   * @param id the id of the account to be changed.
   * @return true if successful, false otherwise.
   * @throws InvalidNameException if name is null.
   */
  public static boolean updateAccountName(String name, int id) throws InvalidNameException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    // check if name is null
    if (name.equals(null)) {
      throw new InvalidNameException();
    }
    boolean complete = updateAccountName(name, id, connection);
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return complete;
  }

  /**
   * update the account balance.
   * 
   * @param balance the new balance for the account.
   * @param id the id of the account.
   * @return true if successful, false otherwise.
   */
  public static boolean updateAccountBalance(BigDecimal balance, int id) {
    // Create a map of account types and allow the balance to be negative if it is balanceowing
    EnumMapAccountTypes accountsMap = new EnumMapAccountTypes();
    boolean isBalanceOwing = DatabaseSelectHelper.getAccountDetails(id).getType() == accountsMap
        .get(AccountTypes.BALANCEOWING);
    if (balance.compareTo(BigDecimal.ZERO) >= 0 || isBalanceOwing) {
      BigDecimal roundedBalance = new BigDecimal(balance.toPlainString());
      roundedBalance = roundedBalance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      // Sets and returns boolean value to true if balance is updated
      boolean complete = updateAccountBalance(roundedBalance, id, connection);
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      return complete;
    }
    // Returns false if balance is negative
    return false;
  }

  /**
   * update the type of the account.
   * 
   * @param typeId the new type for the account.
   * @param id the id of the account to be updated.
   * @return true if successful, false otherwise.
   */
  public static boolean updateAccountType(int typeId, int id) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    List<Integer> listA = new ArrayList<>();
    // get a list of ids of all account types
    listA = DatabaseSelectHelper.getAccountTypesIds();
    boolean exists = false;
    // check if the given typeId is in the list
    for (int i = 0; i < listA.size(); i++) {
      if ((typeId == listA.get(i))) {
        exists = true;
      }
    }
    if (exists == false) {
      return false;
    }
    // update the account type
    boolean complete = updateAccountType(typeId, id, connection);
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return complete;
  }

  /**
   * update the name of an accountType.
   * 
   * @param name the new name to be given.
   * @param id the id of the accountType.
   * @return true if successful, false otherwise.
   * @throws InvalidNameException if name is null
   */
  public static boolean updateAccountTypeName(String name, int id) throws InvalidNameException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    // check if name is null
    if (name.equals(null)) {
      throw new InvalidNameException();
    }
    boolean complete = updateAccountTypeName(name, id, connection);
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return complete;
  }

  /**
   * update the interest rate for this account type.
   * 
   * @param interestRate the interest rate to be updated to.
   * @param id the id of the accountType.
   * @return true if successful, false otherwise.
   */
  public static boolean updateAccountTypeInterestRate(BigDecimal interestRate, int id) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    // check if interest rate is between 0 and 1
    BigDecimal a = new BigDecimal("1.0");
    BigDecimal b = new BigDecimal("0.0");
    if (!((interestRate.compareTo(b) == 1) && (interestRate.compareTo(a) == -1))) {
      return false;
    }
    boolean complete = updateAccountTypeInterestRate(interestRate, id, connection);
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return complete;
  }

  /**
   * Updates a users password in the database.
   * 
   * @param userId the id of the user.
   * @param password the HASHED password of the user (not plain text!).
   * @return true if update succeeded, false otherwise.
   * @throws InvalidNameException if password is null
   */
  public static boolean updatePassword(String password, int id) throws InvalidNameException {
    // check if name is null
    if (password.equals(null)) {
      throw new InvalidNameException();
    }
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = updateUserPassword(password, id, connection);
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return complete;
  }

  /**
   * Update the state of the user message to viewed.
   * 
   * @param userMessageId the id of the message that has been viewed.
   * @return true if successful, false o/w.
   */
  public static boolean updateUserMessageState(int id) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean changed = updateUserMessageState(id, connection);
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return changed;
  }
}
