package com.atm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AtmImplementation implements IAtm {

    Scanner scanner = new Scanner(System.in);

    // Create Connection
    public Connection createConnection() {

        String url = "jdbc:mysql://localhost:3306/atm_management";
        String user = "[DB_NAME]";
        String password = "[DB_PASSWORD]";

        Connection con = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }

        return con;
    }

    // CREATE ACCOUNT
    @Override
    public void createAccount() {

        try {

            Connection con = createConnection();

            System.out.print("Enter Account Number : ");
            String accNo = scanner.next();

            System.out.print("Enter Name : ");
            String name = scanner.next();

            System.out.print("Enter Email : ");
            String email = scanner.next();

            System.out.print("Enter Phone Number : ");
            String phone = scanner.next();

            System.out.print("Enter Branch : ");
            String branch = scanner.next();

            System.out.print("Enter PIN : ");
            int pin = scanner.nextInt();

            System.out.print("Enter Balance : ");
            double balance = scanner.nextDouble();

            String query =
                    "INSERT INTO account VALUES(?,?,?,?,?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, accNo);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, branch);
            ps.setInt(6, pin);
            ps.setDouble(7, balance);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                System.out.println("✅ Account Created Successfully");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // DEPOSIT
    @Override
    public void depositAmount() {

        try {

            Connection con = createConnection();

            System.out.print("Enter Account Number : ");
            String accNo = scanner.next();

            System.out.print("Enter PIN : ");
            int pin = scanner.nextInt();

            System.out.print("Enter Amount : ");
            double amount = scanner.nextDouble();

            String check =
                    "SELECT * FROM account WHERE account_number=? AND pin=?";

            PreparedStatement ps =
                    con.prepareStatement(check);

            ps.setString(1, accNo);
            ps.setInt(2, pin);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String update =
                        "UPDATE account SET balance=balance+? WHERE account_number=?";

                PreparedStatement ps2 =
                        con.prepareStatement(update);

                ps2.setDouble(1, amount);
                ps2.setString(2, accNo);

                ps2.executeUpdate();

                System.out.println("✅ Amount Deposited");

            } else {

                System.out.println("❌ Invalid Account Number or PIN");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // WITHDRAW
    @Override
    public void withdrawAmount() {

        try {

            Connection con = createConnection();

            System.out.print("Enter Account Number : ");
            String accNo = scanner.next();

            System.out.print("Enter PIN : ");
            int pin = scanner.nextInt();

            System.out.print("Enter Amount : ");
            double amount = scanner.nextDouble();

            String check =
                    "SELECT balance FROM account WHERE account_number=? AND pin=?";

            PreparedStatement ps =
                    con.prepareStatement(check);

            ps.setString(1, accNo);
            ps.setInt(2, pin);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                double balance = rs.getDouble("balance");

                if (balance >= amount) {

                    String update =
                            "UPDATE account SET balance=balance-? WHERE account_number=?";

                    PreparedStatement ps2 =
                            con.prepareStatement(update);

                    ps2.setDouble(1, amount);
                    ps2.setString(2, accNo);

                    ps2.executeUpdate();

                    System.out.println("✅ Withdraw Successful");

                } else {

                    System.out.println("❌ Insufficient Balance");
                }

            } else {

                System.out.println("❌ Invalid Account Number or PIN");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // CHECK BALANCE
    @Override
    public void checkBalance() {

        try {

            Connection con = createConnection();

            System.out.print("Enter Account Number : ");
            String accNo = scanner.next();

            System.out.print("Enter PIN : ");
            int pin = scanner.nextInt();

            String query =
                    "SELECT balance FROM account WHERE account_number=? AND pin=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, accNo);
            ps.setInt(2, pin);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                double balance = rs.getDouble("balance");

                System.out.println("💰 Balance : " + balance);

            } else {

                System.out.println("❌ Invalid Account Number or PIN");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    // DISPLAY ACCOUNT DETAILS
    public void displayAccountDetails() {

        try {

            Connection con = createConnection();

            System.out.print("Enter Account Number : ");
            String accNo = scanner.next();

            System.out.print("Enter PIN : ");
            int pin = scanner.nextInt();

            String query =
                    "SELECT * FROM account WHERE account_number=? AND pin=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, accNo);
            ps.setInt(2, pin);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("================================");

                System.out.println("Account Number : "
                        + rs.getString("account_number"));

                System.out.println("Name : "
                        + rs.getString("name"));

                System.out.println("Email : "
                        + rs.getString("email"));

                System.out.println("Phone : "
                        + rs.getString("phone_number"));

                System.out.println("Branch : "
                        + rs.getString("branch"));

                System.out.println("Balance : "
                        + rs.getDouble("balance"));

                System.out.println("================================");

            } else {

                System.out.println("❌ Invalid Account Number or PIN");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // DELETE ACCOUNT
    public void deleteAccount() {

        try {

            Connection con = createConnection();

            System.out.print("Enter Account Number : ");
            String accNo = scanner.next();

            System.out.print("Enter PIN : ");
            int pin = scanner.nextInt();

            String query =
                    "DELETE FROM account WHERE account_number=? AND pin=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, accNo);
            ps.setInt(2, pin);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                System.out.println("✅ Account Deleted Successfully");

            } else {

                System.out.println("❌ Invalid Account Number or PIN");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
	public void updation() {
		  try {

	            Connection con = createConnection();

	            System.out.print("Enter Account Number : ");
	            String accNo = scanner.next();

	            System.out.print("Enter PIN : ");
	            int pin = scanner.nextInt();

	            String check =
	                    "SELECT * FROM account WHERE account_number=? AND pin=?";

	            PreparedStatement ps =
	                    con.prepareStatement(check);

	            ps.setString(1, accNo);
	            ps.setInt(2, pin);

	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {

	                System.out.println("1. Update Name");
	                System.out.println("2. Update Email");
	                System.out.println("3. Update Phone");
	                System.out.println("4. Update Branch");
	                System.out.println("5. Update PIN");

	                System.out.print("Choose Option : ");

	                int choice = scanner.nextInt();

	                switch (choice) {

	                    case 1:

	                        System.out.print("Enter New Name : ");
	                        String name = scanner.next();

	                        PreparedStatement ps1 =
	                                con.prepareStatement(
	                                        "UPDATE account SET name=? WHERE account_number=?");

	                        ps1.setString(1, name);
	                        ps1.setString(2, accNo);

	                        ps1.executeUpdate();

	                        System.out.println("✅ Name Updated");
	                        break;

	                    case 2:

	                        System.out.print("Enter New Email : ");
	                        String email = scanner.next();

	                        PreparedStatement ps2 =
	                                con.prepareStatement(
	                                        "UPDATE account SET email=? WHERE account_number=?");

	                        ps2.setString(1, email);
	                        ps2.setString(2, accNo);

	                        ps2.executeUpdate();

	                        System.out.println("✅ Email Updated");
	                        break;

	                    case 3:

	                        System.out.print("Enter New Phone : ");
	                        String phone = scanner.next();

	                        PreparedStatement ps3 =
	                                con.prepareStatement(
	                                        "UPDATE account SET phone_number=? WHERE account_number=?");

	                        ps3.setString(1, phone);
	                        ps3.setString(2, accNo);

	                        ps3.executeUpdate();

	                        System.out.println("✅ Phone Updated");
	                        break;

	                    case 4:

	                        System.out.print("Enter New Branch : ");
	                        String branch = scanner.next();

	                        PreparedStatement ps4 =
	                                con.prepareStatement(
	                                        "UPDATE account SET branch=? WHERE account_number=?");

	                        ps4.setString(1, branch);
	                        ps4.setString(2, accNo);

	                        ps4.executeUpdate();

	                        System.out.println("✅ Branch Updated");
	                        break;

	                    case 5:

	                        System.out.print("Enter New PIN : ");
	                        int newPin = scanner.nextInt();

	                        PreparedStatement ps5 =
	                                con.prepareStatement(
	                                        "UPDATE account SET pin=? WHERE account_number=?");

	                        ps5.setInt(1, newPin);
	                        ps5.setString(2, accNo);

	                        ps5.executeUpdate();

	                        System.out.println("✅ PIN Updated");
	                        break;

	                    default:

	                        throw new InvalidChoiceException(
	                                "❌ Invalid Choice");
	                }

	            } else {

	                System.out.println("❌ Invalid Account Number or PIN");
	            }

	            con.close();

	        } catch (Exception e) {

	            e.printStackTrace();
	        }

		
	}

}
