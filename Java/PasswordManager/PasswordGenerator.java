import java.net.MalformedURLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordGenerator {
    private static ArrayList<User> userlist = new ArrayList<User>();
    private static User currentuser;

    public static void Intro() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to your password generator, enter your username: ");
        System.out.println("It must be atleast of length 5");

        boolean invaliduser = true;
        while (invaliduser) {
            User user = new User(scanner.nextLine());
            if (user.toString().length() < 5)
                System.out.println("Username size too short!");
            if (userlist.contains(user))
                System.out.println("Username already exists");

            if (user.toString().length() >= 5 && !userlist.contains(user)) {
                userlist.add(user);
                currentuser = user;
                invaliduser = false;
            }
            System.out.println("Enter a valid username");
        }

        System.out.println("Here are the requirements for the master password:");
        System.out.println("1. A length between 9 and 15 characters, inclusive");
        System.out.println("2. Atleast one special character");
        System.out.println("3. Needs to be alphanumeric");
        System.out.println("4. Password cannot match your username");
        boolean invalidpass = true;
        boolean flags;
        while (invalidpass) {
            flags = false;
            System.out.println("Enter your password");
            String pass = scanner.nextLine();
            if (pass.length() < 9) {
                System.out.println("Your password is too short!");
                flags = true;
            } else if (pass.length() > 15) {
                System.out.println("Your password is too long");
                flags = true;
            }
            Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(pass);

            if (!m.find()) {
                System.out.println("Your password does not contain a special character!");
                flags = true;
            }

            p = Pattern.compile("[^a-z]");
            m = p.matcher(pass);

            if (!m.find()) {
                System.out.println("Your password does not have a number!");
                flags = true;
            }

            p = Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
            m = p.matcher(pass);
            if (!m.find()) {
                System.out.println("Your password does not have letters!");
                flags = true;
            }
            if (currentuser.toString().equals(pass)) {
                System.out.println("Your password cannot be the same as your username!");
                flags = true;
            }

            if (!flags) {
                invalidpass = false;
                currentuser.setPassword(pass);
            }
        }

    }

    public static boolean Login() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("What is your username?");
            String username = scanner.nextLine();
            if (username.equals("create"))
                return false;
            User user = new User(username);
            if (userlist.indexOf(user) != -1) {
                currentuser = user;
                break;
            }
            System.out.println(
                    "That username doesn't exist. Try a different username or type 'create' to create a new account");
        }

        while (true) {
            System.out.println("What is your password?");
            String pass = scanner.nextLine();
            if (currentuser.checkCredentials(pass))
                return true;
            System.out.println("Wrong password");
        }
    }

    public static int Menu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 1: Create a new password for a website");
        System.out.println("Enter 2: Get an existing password");
        int menuInput = scanner.nextInt();
        if (menuInput > 0 && menuInput <= 2)
            return menuInput;
        else
            return 0;
    }

    public static void main(String[] args) throws MalformedURLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you a returning user?");
        if (scanner.nextLine().indexOf("y") == -1)
            Intro();
        else 
            if(!Login())
                Intro();
        int menuInput;
        while(true){
                menuInput = Menu();
                if(menuInput != 0)
                break;
            }
        
        if(menuInput == 1){
            System.out.println("What website would you like to create a password for?");
            String domain = scanner.nextLine();
            System.out.println("What is your username for this website?");
            String domainuser = scanner.nextLine();
            Website web = new Website(domain, domainuser);
            currentuser.addWebsite(web);
            currentuser.createPassword(domain, domainuser);
            System.out.println("Your password is :" + web.getdomainpass());
            System.out.println("Your username is :" + web.getdomainuser());
        }
    }

}