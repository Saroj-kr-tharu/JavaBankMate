package PackageFile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AccountManager {
    private String email;
    private int security_pin;
    private long account_no;

    Scanner scan = new Scanner(System.in);

    // construtor
    public void setter(String email, int security_pin, long account_no) {
        this.email = email;
        this.security_pin = security_pin;
        this.account_no = account_no;
    }

    private boolean isValidateBalance(Connection connection, double tempAmount) {
        try {
            String query = "SELECT balance FROM accounts WHERE email = ? AND security_pin= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, security_pin);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double balance = resultSet.getDouble("balance");
                if (balance >= tempAmount) {
                    return true;
                }

            }
            preparedStatement.close();
            resultSet.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

        return false;
    }

    public void TranferBalance(Connection connection) {
        if(!SecurityPin()) {
            return;
        }
        String receiver_query = "UPDATE accounts SET balance = balance + ? WHERE email = ? ";
        String sender_query = "UPDATE accounts SET balance = balance - ? WHERE email = ? AND security_pin = ? ";

        try {

            System.out.println("\t\t\t\t  Enter Receiver Email   -------->  ");
            String rec_email = scan.nextLine();
            rec_email = scan.nextLine();

            System.out.println("\t\t\t\t  Enter Amount To Send  -------->  ");
            double amount = scan.nextDouble();


            if (isValidateBalance(connection, amount)) {
                PreparedStatement rec_prepare = connection.prepareStatement(receiver_query);
                rec_prepare.setDouble(1, amount);
                rec_prepare.setString(2, rec_email);

                PreparedStatement sender_statement = connection.prepareStatement(sender_query);
                sender_statement.setDouble(1, amount);
                sender_statement.setString(2, email);
                sender_statement.setInt(3, security_pin);

                // set auto commit to false
                connection.setAutoCommit(false);

                int sender_row = sender_statement.executeUpdate();
                int receiver_row = rec_prepare.executeUpdate();

                if (sender_row > 0 && receiver_row > 0) {
                    System.out.printf(
                            "\n\t\t\t\t <------- Sucessfully Transferd Money %f into email %s from accounts %d -------->  ",
                            amount, rec_email, account_no);
                    connection.commit();
                } else {
                    System.out.println("\t\t\t\t <-------  Failure Transfer Failed   -------->  ");
                    connection.rollback();
                }

                sender_statement.close();
                rec_prepare.close();
//                connection.close();
            } else {

                System.out.println("\t\t\t\t <-------  Balance is not Enough to Complete these Transaction   -------->  ");
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void DebitedBalance(Connection connection) {
        if(!SecurityPin()) {
            return;
        }
        try {
            System.out.println("\t\t\t\t  Enter Balance to Withdraw  -------->  ");
            double amount = scan.nextDouble();
            if(isValidateBalance(connection,amount)) {

                String query = "UPDATE accounts SET balance= balance - ? WHERE email = ? AND security_pin = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDouble(1, amount);
                preparedStatement.setString(2, email);
                preparedStatement.setInt(3, security_pin);

                int rowAffected = preparedStatement.executeUpdate();
                if (rowAffected > 0) {
                    System.out.printf("\n\t\t\t\t <------- Sucessfully Debited Amount %f  From account %d -------->  ",
                            amount, account_no);

                } else {
                    System.out.println("\t\t\t\t <------- Error Occured   -------->  ");
                }

                preparedStatement.close();

//                connection.close();
            }
            else{
                System.out.println("\t\t\t\t <-------  Balance is not Enough to Complete these Transaction   -------->  ");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void CreditBalance(Connection connection) {

        if(!SecurityPin()) {
            return;
        }

        System.out.println("\t\t\t\t  Enter Balance to Credit  -------->  ");
        double amount = scan.nextDouble();

        try {

            String query = "UPDATE accounts SET balance= balance + ? WHERE email = ? AND security_pin = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, email);
            preparedStatement.setInt(3, security_pin);

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                System.out.printf("\n\t\t\t\t <------- Sucessfully Credit Amount %f  into account %d -------->  ",
                        amount, account_no);

            } else {
                System.out.println("\t\t\t\t <------- Error Occured   -------->  ");
            }
            preparedStatement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void CheckBalance(Connection connection) {

        if(!SecurityPin()) {
            return;
        }
            try {
                String query = "SELECT balance FROM accounts WHERE email = ? AND security_pin= ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setInt(2, security_pin);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    double balance = resultSet.getDouble("balance");
                    System.out.printf("\n\t\t\t\t <------- Balance %f   -------->  ", balance);

                }

            preparedStatement.close();
                resultSet.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());

            }


    }

    public boolean SecurityPin() {

        System.out.println("\t\t\t\t  Enter Pin Please  -------->  ");
        int temp = scan.nextInt();

        if (temp == security_pin) {
            return true;
        } else {
            return false;
        }


    }
}