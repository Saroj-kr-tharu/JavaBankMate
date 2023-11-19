package PackageFile;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.Scanner;

public class Accounts {
    private long accountNO;
    private double balance;
    private int SecurityPin;
    private String email;

    Scanner scan = new Scanner(System.in);
    public void openAccount(Connection connection, String mail){
        // input section

        System.out.println("\t\t\t\t  Enter Security PIn  -------->  ");
        SecurityPin = scan.nextInt();
        System.out.println("\t\t\t\t  Enter Opening Balance -------->  ");
        balance = scan.nextDouble();

        email= mail;

        try{

        String query =" INSERT INTO accounts(account_no, balance,security_pin,email) VALUES(?,?,?,?) ";
        accountNO = generateAccount(connection);


        if(!accountExistEmail(connection,email)){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1,accountNO);
            preparedStatement.setDouble(2,balance);
            preparedStatement.setInt(3,SecurityPin);
            preparedStatement.setString(4,email);

            int rowAffected=  preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("\t\t\t\t <------- Sucessfully Opened New Account   -------->  ");
            } else {
                System.out.println("\t\t\t\t <------- Error Occured   -------->  ");
            }
        }
        else{
            System.out.println("\t\t\t\t <------- Email Already Used   -------->  ");
            System.out.println("\t\t\t\t <------- Please Registered with another Email   -------->  ");
        }




        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    private long generateAccount(Connection connection){
        Random rand = new Random();
        int rand_int1 = rand.nextInt(1000);

        String temp = "10000"+ rand_int1;
        accountNO = Long.parseLong(temp);

        if(accountExist(connection, accountNO)){
            generateAccount(connection);
        }
        return accountNO;
    }


    public void SetupReturn(Connection connection, String mail){
        String query = "SELECT * FROM accounts WHERE email = ? ";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,mail  );
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
             email = mail;
             SecurityPin = resultSet.getInt("security_pin");
             accountNO = resultSet.getLong("account_no");
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }


    public long getAccountNO(){
        return accountNO;
    }

    public int getSecurityPin() {
        return SecurityPin;
    }

    public String getEmail() {
        return email;
    }

    public boolean accountExistEmail(Connection connection, String mail ){
        String query = "SELECT * FROM accounts WHERE email = ? ";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,mail  );
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
                return  true;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return  false;
    }
    public boolean accountExist(Connection connection,long accountN ){
        String query = "SELECT * FROM accounts WHERE account_no = ? ";

        try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1,accountN  );
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next())
                return  true;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return  false;
    }

}
