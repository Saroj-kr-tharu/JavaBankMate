
import java.sql.*; // import all from sql section
import java.util.Scanner;

// class
import  PackageFile.database;
import PackageFile.User;
import PackageFile.Accounts;
import PackageFile.AccountManager;


public class Main {
    // variable for db connection
    private static final String username = "root";
    private static final String password = "Root@admin";
    private static final String url = "jdbc:mysql://localhost:3306/bankmangementsystem";

    // input
    Scanner scan = new Scanner(System.in);

    // data type for functionality of menu
    int ch;
    String holdScreen;

    // variable for hold value
   private String name;
   private String email;



   // instance of Class
   private final User user = new User();
    private final Accounts accounts = new Accounts();
    private final AccountManager accountManager = new AccountManager();

    public  void accountMenu(Connection connection){

        accounts.SetupReturn(connection,email);
        accountManager.setter(accounts.getEmail(), accounts.getSecurityPin(), accounts.getAccountNO());


        while (true) {
            System.out.print("\033[H\033[2J");

            System.out.println("\n\t\t\t <-------------------------------------------------------> ");
            System.out.printf("\t\t\t <--------   Welcome %s  To System --------->  ",name);
            System.out.println("\n\t\t\t\t <-------    1. Check Balance     -------->  ");
            System.out.println("\t\t\t\t <-------    2. Credit Balance    -------->  ");
            System.out.println("\t\t\t\t <-------    3. Debit Balance     -------->  ");
            System.out.println("\t\t\t\t <-------    4. Transfer Balance  -------->  ");
            System.out.println("\t\t\t\t <-------    7. Help              -------->  ");
            System.out.println("\t\t\t\t <-------    9. Logout      -------->  ");
            System.out.println("\t\t\t <-------------------------------------------------------> ");

            ch = scan.nextInt();

            switch (ch) {

                case 1: // Credit Balance
                    System.out.println("\t\t\t\t <------- Welcome to Check Balance Function -------->  ");

                    accountManager.CheckBalance(connection);
                    holdScreen = scan.nextLine();
                    holdScreen = scan.nextLine();
                    break;



                case 2: // Credit Balance
                    System.out.println("\t\t\t\t <------- Welcome to Credit Function -------->  ");

                    accountManager.CreditBalance(connection);
                    holdScreen = scan.nextLine();
                    holdScreen = scan.nextLine();
                    break;

                case 3: // debit Balance
                    System.out.println("\t\t\t\t <------- Welcome to Debit Function -------->  ");

                    accountManager.DebitedBalance(connection);
                    holdScreen = scan.nextLine();
                    holdScreen = scan.nextLine();
                    break;

                case 4: // Transfer
                    System.out.println("\t\t\t\t <------- Welcome to Transfer  Function -------->  ");

                    accountManager.TranferBalance(connection);
                    holdScreen = scan.nextLine();
                    holdScreen = scan.nextLine();
                    break;


                case 7: // Help Section
                    System.out.println("\n\n\t\t\t <-------------------------------------------------------> ");
                    System.out.println("\t\t\t\t <------- Welcome To Help Section   --------->   ");
                    System.out.println("\t\t\t\t <-------    May Be You Found Bug           --------->  ");
                    System.out.println("\t\t\t\t <-------    You Need Database              --------->  ");
                    System.out.println("\t\t\t\t <-------    Having tables users,accounts   --------->  ");
                    System.out.println("\t\t\t\t <-------    Having issue in -integer,input   --------->  ");
                    System.out.println("\t\t\t\t <-------    Program By Saroj Kumar Tharu   --------->  ");
                    System.out.println("\t\t\t\t <-------    Sarojc11345@gmail.com ---------->  ");
                    System.out.println("\t\t\t <-------------------------------------------------------> ");
                    holdScreen = scan.nextLine();
                    break;
                case 9: // Log Out

                    System.out.println("\t\t\t\t <------- log Outing  -------->  ");
                    mainMenu();

                    break;

                default:
                    System.out.println("\t\t\t\t <------ Invalid Options -------> ");
                    holdScreen = scan.nextLine();
                    break;
            }
        }
    }


    void mainMenu() {
        //db connection and driver laoder
        database db = new database(username,password,url);
        Connection connection = db.dbConnection();




        while (true) {
            System.out.print("\033[H\033[2J");

            System.out.println("\t\t\t <-------------------------------------------------------> ");
            System.out.println("\t\t\t <-------    Welcome To Bank Management System  -------->  ");
            System.out.println("\t\t\t\t <-------    1. Login System     -------->  ");
            System.out.println("\t\t\t\t <-------    2. Register          -------->  ");
            System.out.println("\t\t\t\t <-------    7. Help              -------->  ");
            System.out.println("\t\t\t\t <-------    9. Exit System       -------->  ");
            System.out.println("\t\t\t <-------------------------------------------------------> ");

            ch = scan.nextInt();

            switch (ch) {
                case 1: // Login

                    if(user.login(connection)){
                        name= user.getFull_name();
                        email = user.getEmail();

                        if(accounts.accountExistEmail(connection,email)){
                        accountMenu(connection);
                        }
                        else{
                            System.out.println("\n");
                            System.out.println("\n\t\t\t\t <-------   1. Open Account       -------->  ");
                            System.out.println("\t\t\t\t <-------    9. Exit System       -------->  ");
                            int choice = scan.nextInt();
                            if(choice == 1){
                                accounts.openAccount(connection,email);
                            }
                            else{
                                System.out.println("\t\t\t\t <------- Thanks for using our System -------->  ");
                                System.out.println("\t\t\t\t <------- Exiting .. Exiting  -------->  ");
                                System.exit(1);
                            }
                        }

                    }

                    holdScreen = scan.nextLine();
                    holdScreen = scan.nextLine();
                    break;

                case 2: // Register
                    System.out.println("\t\t\t\t <------- Welcome to Register Function -------->  ");
                    user.register(connection);
                    holdScreen = scan.nextLine();
                    holdScreen = scan.nextLine();
                    break;


                case 7: // Help Section
                    System.out.println("\n\n\t\t\t <-------------------------------------------------------> ");
                    System.out.println("\t\t\t\t <------- Welcome To Help Section   --------->   ");
                    System.out.println("\t\t\t\t <-------    May Be You Found Bug   --------->  ");
                    System.out.println("\t\t\t\t <-------    Log in with Creditials   --------->  ");
                    System.out.println("\t\t\t\t <-------    Register OR Exit   --------->  ");
                    System.out.println("\t\t\t\t <-------    Program By Saroj Kumar Tharu --->  ");
                    System.out.println("\t\t\t\t <-------    Sarojc11345@gmail.com ---------->  ");
                    System.out.println("\t\t\t <-------------------------------------------------------> ");
                    holdScreen = scan.nextLine();
                    break;
                case 9: // Exit System
                    System.out.println("\t\t\t\t <------- Thanks for using our System -------->  ");
                    System.out.println("\t\t\t\t <------- Exiting .. Exiting  -------->  ");
                    System.exit(1);
                    break;

                default:
                    System.out.println("\t\t\t\t <------ Invalid Options -------> ");
                    holdScreen = scan.nextLine();
                    break;
            }
        }


    }

    public static void main(String[] args) {

        Main m = new Main();
        m.mainMenu();

    }


}