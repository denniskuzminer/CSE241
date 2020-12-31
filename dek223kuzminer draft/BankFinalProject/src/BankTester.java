import java.sql.*;
import java.util.*;

public class BankTester {

    public static void main(String[] args) throws Exception { // Make sure upon submission that exceptions are handled with try/catch
        /*
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String ans = "";
        String dept = "";
        Scanner scnr = new Scanner(System.in);
        boolean isValid = true;
        String department = "";
        System.out.println("***TEST***");
        do {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");

                System.out.println("Enter username: ");
                String user = scnr.nextLine();
                System.out.println("Enter password: ");
                String password = scnr.nextLine();
                System.out.println("Connecting to database...");
                connection = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", user,
                        password);
                isValid = true;
            } catch (SQLException e1) {
                isValid = false;
                System.out.println("Invalid username or password. Try again.");
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            }
        } while (!isValid);
*/
        Connection connection = null;
        PreparedStatement statement = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        String user = "";
        String password = "";
        Scanner scnr = new Scanner(System.in);
        boolean isValid = true;
        boolean quit = false;
        do {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");

                System.out.println("Enter username: ");
                user = scnr.nextLine();
                System.out.println("Enter password: ");
                password = scnr.nextLine();
                System.out.println("Connecting to database...");
                connection = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", user,
                        password);
                isValid = true;
            } catch (SQLException e1) {
                isValid = false;
                System.out.println("Invalid username or password. Try again.");
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            }
        } while (!isValid);
        System.out.println("\n---Connected to Nickel Bank---\n");
        while (!quit) {
            System.out.println("Welcome to Nickel Bank.");
            System.out.println("How can we help you?");
            System.out.println("Type p to enter a purchase.");
            System.out.println("Type d or w to make a withdrawal or deposit.");
            System.out.println("Type l to enter a loan.");
            System.out.println("Type q to exit Nickel Bank Management System.");
            String ans = scnr.nextLine();
            switch (ans) {
                case "p":
                    System.out.println("Are you using a credit or debit card? ");
                    isValid = false;
                    while (!isValid) {
                        ans = scnr.nextLine();
                        if (ans.equals("debit") || ans.equals("debit card")) {
                            Purchase p = new Purchase("debit", user, password);
                            isValid = true;
                        }
                        else {
                            if (ans.equals("credit") || ans.equals("credit card")) {
                                Purchase p = new Purchase("credit", user, password);
                                isValid = true;
                            } else {
                                System.out.println("Please enter either debit or credit card");
                                isValid = false;
                            }
                        }
                    }
                    break;
                case "d":
                case "w":
                    AccountDepWith dw = new AccountDepWith(user, password);
                    break;
                case "l":
                    TakeOutLoan l = new TakeOutLoan(user, password);
                    break;
                case "q":
                    System.out.println("Thank you for choosing Nickel Bank!");
                    quit = true;
                    break;
                default:
                    System.out.println("Please enter in a valid letter from the menu.");
                    quit = false;
                    continue;
            }
        }
        try {
            if(statement != null)
                statement.close();
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        try {
            if(connection != null)
                connection.close();
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }
    /*
    public void insertTransactionData(String table, String type) {
        try {
            System.out.println("What was the value of the purchase: ");
            int purchaseValue = scnr.nextInt();
            query = "select balance from account join debit_card on account.acc_id = debit_card.acc_id where debit_card_number = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, debitCardNo);
            resultSet = statement.executeQuery();
            int balance;
            if (!resultSet.next()) {
                System.out.println("Empty result.");
                throw new Exception();
            } else {
                do {
                    isValid = true;
                    System.out.println("Balance before transaction: " + resultSet.getString("balance"));
                    balance = Integer.parseInt(resultSet.getString("balance"));
                } while (resultSet.next());
            }
            if (balance - purchaseValue < 0) {
                System.out.println("Insufficient Funds. Card Declined.");
                throw new Exception();
            } else {
                //query
                balance -= purchaseValue;
                //7751654471668020
                String update = "update account set balance = " + (balance) +
                        "where account.acc_id = " +
                        "(select account.acc_id from account join debit_card on account.acc_id = debit_card.acc_id " +
                        "where debit_card_number = " + debitCardNo + " )";
                statement = connection.prepareStatement(update);
                statement.executeUpdate();
                System.out.println("Transaction Approved. \nYour current account balance is: " + (balance));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     */
}
