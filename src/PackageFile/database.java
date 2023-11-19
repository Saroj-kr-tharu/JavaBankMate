package PackageFile;

import java.sql.*;

public class database {
    private  final String username;
    private  final String password ;
    private final String url;


    // constortor
    public database(String username, String password , String url){
        this.username= username;
        this.password= password;
        this.url= url;
    }


    public Connection dbConnection() {

        // db connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // getting connection
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return (DriverManager.getConnection(url, username, password));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }


    }


}
