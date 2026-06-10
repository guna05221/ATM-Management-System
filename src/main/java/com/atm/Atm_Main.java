package com.atm;

import java.util.Scanner;

public class Atm_Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        AtmImplementation atm = new AtmImplementation();

        while (true) {

            System.out.println("\n========== ATM MANAGEMENT SYSTEM ==========");

            System.out.println("1. CREATE ACCOUNT");
            System.out.println("2. DEPOSIT");
            System.out.println("3. WITHDRAW");
            System.out.println("4. CHECK BALANCE");
            System.out.println("5. UPDATE ACCOUNT");
            System.out.println("6. DISPLAY ACCOUNT DETAILS");
            System.out.println("7. DELETE ACCOUNT");
            System.out.println("10. EXIT");

            System.out.print("CHOOSE AN OPTION : ");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:

                    atm.createAccount();
                    break;

                case 2:

                    atm.depositAmount();
                    break;

                case 3:

                    atm.withdrawAmount();
                    break;

                case 4:

                    atm.checkBalance();
                    break;

                case 5:

                    atm.updation();
                    break;

                case 6:

                    atm.displayAccountDetails();
                    break;

                case 7:

                    atm.deleteAccount();
                    break;

                case 10:

                    System.out.println("🙏 THANK YOU FOR USING ATM");

                    scanner.close();

                    System.exit(0);

                default:

                    System.out.println("❌ Invalid Choice!");
            }
        }
    }
}