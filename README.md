# B07GoochiBankApp
This is a banking app made in Android Studio for CSCB07 (Software Design) in a group of 4. The project was completed in 10 days.

Install the Goochi App onto your Android (preferably Nexus 5X or later).
 
We are initializing with these users and accounts: GaganAdmin (ADMIN), GaganTeller (TELLER), KarenCustomer (CUSTOMER), KarenCheq (CHEQUING, $100000.00).

LOGIN INFO:
Admin (USERID: 1, PASSWORD: 123), Teller (USERID: 3, PASSWORD: 123), or Customer (USERID: 2, PASSWORD: 123).

This initialization only happens the first time you run the app. If you want to re-initialize with original values, you will need to reinstall the app.
 
Our feature is transactions:
Users can transfer money to each other, given an account id.
When a user sends money to another, the other receives a message in the format @#$%hashedpassword:amount. When receiving, this can be used to authenticate the transaction and extract the money. Also, because of @#$%, the message will only be set to read if transaction is successful. Read messages do not allow for transactions.
Account Transfer
Users can transfer an amount from one account to another, by entering the account names they want to transfer to and from, and the amount that they wish to transfer.
Transfer to User 
You can transfer to a user by entering the name of the account, userid, amount that you wish to transfer, and the password (the password is only for the user to accept the money).
Receive Transfer 
You can receive transfers from other users by entering the messageid, account name, and the password, and then the amount will automatically be transferred to your account.
 
Open the app. There will be a red main page with the Goochi logo. Then it will jump to the main page where you have the option of
logging in as an Admin (USERID: 1, PASSWORD: 123), Teller (USERID: 3, PASSWORD: 123), or Customer (USERID: 2, PASSWORD: 123).
 
Once you log in as an Admin, you will be brought to the Admin Options page where you can choose to:
-          Create new Admin/Teller, by entering Name, Age, Address, and Password. You will then be given the New Admin/Teller’s USERID which you can then use along with the Password to login;
-          View all current Admins/Tellers/Customers;
-          View total balance of accounts for user, by entering the user’s USERID;
-          Database (serialize/deserialize);
-          Messages (view/leave messages);
-          Promote Teller to Admin;
-          View all accounts balance.
 
Once you log in as a Teller, you will be brought to the Teller Options page where you can choose to:
-          Create new Customer, by entering Name, Age, Address, and Password;
-          Log in to a Customer’s account (you will be brought to the Customer log in page). From there, you will be brought to the Teller Options page where you can:
			• Transfers (Account Transfer, Transfer to User, Receive Transfer);
			• Create a new account, by entering the name of the account and the starting money. You will then be taken to another page to pick your account type (Chequing, Savings, TFSA, Restricted Savings, or Balance Owing);
-          View messages.
 
Once you log in as a Customer, you will be brought to the Customer Options page where you can choose to:
-          View your list of accounts and balances;
-          Make a deposit, by entering the name of the account and the amount you wish to deposit;
-          Make a withdrawal, by entering the name of the account and the amount you wish to withdraw;
-          Check your balance, by entering the name of the account that you want to check the balance for;
-          View all messages;
-          Transfer Options (Account Transfer, Transfer to User, Receive Transfer).
 
If for any reason, the app crashes after clicking through exit buttons and navigating back and forth, this is most likely due to a null pointer exception from passing objects between activities. Thus, if you want to continue testing some specific button, restart the app and navigate directly to that button without using any exit buttons.
