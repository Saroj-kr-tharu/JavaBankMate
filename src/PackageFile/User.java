package PackageFile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private String email;
    private String password;
    private String full_name;
    Scanner scan = new Scanner(System.in);

    // register mehtods
    public void register(Connection connection) {

        System.out.println("\t\t\t\t  Enter Email  -------->  ");
        String email = scan.nextLine();
        System.out.println("\t\t\t\t  Enter Password  -------->  ");
        password = scan.nextLine();
        System.out.println("\t\t\t\t  Enter Full Name  -------->  ");
        full_name = scan.nextLine();

        // Prepared to send into Query
        String query = "INSERT INTO users(email,password,full_name) VALUES(?,?,?)";

        if (!isExistUser(connection, email)) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, full_name);

                int rowAffected = preparedStatement.executeUpdate();
                if (rowAffected > 0) {
                    System.out.println("\t\t\t\t <------- Sucessfully Registered   -------->  ");
                } else {
                    System.out.println("\t\t\t\t <------- Error Occured While Registering  -------->  ");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.printf("\t\t\t\t <------- %s is already registered     -------->  ", email);
            System.out.println("\n\t\t\t\t <------- Please Log in    -------->  ");
        }

    }

    private boolean isExistUser(Connection connection, String tempMail) {
        String query = "SELECT * FROM users WHERE email = ? ";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tempMail);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean login(Connection connection) {

        System.out.println("\t\t\t\t  Enter Email  -------->  ");
        email = scan.next();
        System.out.println("\t\t\t\t  Enter Password  -------->  ");
        password = scan.next();

        // Prepared to send into Query
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try {

            // Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                full_name = resultSet.getString("full_name");
                System.out.println("\t\t\t\t <------- Sucessfully Login   -------->  ");
                System.out.printf("\t\t\t\t <------- Welcome Mr %s   -------->  ", full_name);
                return  true;
            } else {
                System.out.println("\t\t\t\t <------- Email or Pass Wrong   -------->  ");
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return  false;

    }


    // getter
    public String getFull_name(){
        return  full_name;
    }

    public  String getEmail(){
        return  email;
    }

}