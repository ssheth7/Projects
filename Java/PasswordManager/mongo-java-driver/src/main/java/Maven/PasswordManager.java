/*Things to do:
Convert Websites to Documents, convery documents to websites
Remove dependencies on local User ArrayList 
Create Password Encryption
Create UI
*/

package Maven;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
//import com.mongodb.client.model.Filters;
//import com.mongodb.client.model.Updates;
import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.model.Indexes;

import org.bson.Document;
//import javax.print.Doc;

public class PasswordManager {
    // private static ArrayList<User> userlist = new ArrayList<User>();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentuser;

    public static void Intro(MongoDatabase database) throws IOException {
        // Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to your password manager, enter your username: ");
        System.out.println("It must be atleast of length 5");

        boolean invaliduser = true;
        while (invaliduser) {
            User user = new User(scanner.nextLine());
            if (user.toString().length() < 5)
                System.out.println("Username size too short!");
            boolean inDB = false;

            MongoCollection<Document> collection = database.getCollection(user.toString());
            try {
                collection.find().first().get("Password");
                inDB =true;
                System.out.println("This username already exists");
            } catch (NullPointerException e) {
                inDB =false;
            }

            if (!inDB && user.toString().length() >= 5) {
                currentuser = user;
                invaliduser = false;
            }
            if (invaliduser)
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
            if (pass.contains(currentuser.toString())) {
                System.out.println("Your password cannot contain your username!");
                flags = true;
            }

            if (!flags) {
                invalidpass = false;
                currentuser.setPassword(pass);
            }
        }
        UploadtoDB(database);
    }

    public static boolean Login(MongoDatabase database) { // If the user already has a login, this method checks the
                                                          // validity of the credentials
        // Scanner scanner = new Scanner(System.in);
        boolean prevUser = true;
        String username = "";
        boolean validuser;
        while (prevUser) {
            System.out.println("What is your username?");
            username = scanner.nextLine();
            if (username.equals("create"))
                return !prevUser;
            MongoCollection<Document> collection = database.getCollection(username);
            try {
                collection.find().first().get("Password");
                validuser = true;
            } catch (NullPointerException e) {
                System.out.println("Username not found, type create to create a new account.");
                validuser = false;
            }
            while (validuser) {
                System.out.println("What is your password?");
                String passInput = scanner.nextLine();
                System.out.println(collection.find().first().get("Password"));
                if (collection.find().first().get("Password").equals(passInput)) {
                    System.out.println("Logged in as " + username);
                    User user = new User(username);
                    currentuser = user;
                    currentuser.setPassword(passInput);
                    return true;
                } else if (passInput.indexOf("create") > -1)
                    return false;
                else
                    System.out.println("Wrong password! Type 'create' to create a new account.");
            }

        }
        return false;
    }

    public static int Menu(boolean newUser) { // A menu for the user once they have logged in
        // Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 1: Create a new password for a website");
        if (!newUser)
            System.out.println("Enter 2: Get an existing password");
        System.out.println("Enter 3: Quit");
        int menuInput = scanner.nextInt();
        if (menuInput > 0 && menuInput <= 2 && newUser == false) {
            return menuInput;
        } else if (menuInput == 1 && newUser == true)
            return menuInput;
        else if (menuInput == 3)
            return menuInput;
        return 0;
    }

    public static void UploadtoDB(MongoDatabase database) {
        System.out.println("Connected to the database successfully");
        // Retrieving a collection

        MongoCollection<Document> collection = database.getCollection(currentuser.toString());

        Document passDoc = new Document("Password", currentuser.getPassword());
        collection.insertOne(passDoc);

    }

    public Website toWeb(Document fromDB) throws MalformedURLException {
        Website website = new Website((String) fromDB.get("domain"), (String) fromDB.get("Website username"),
                (String) fromDB.get("Website password"));
        return website;
    }

    public static void main(String[] args) throws MalformedURLException, IOException { // First the user can either
                                                                                       // create a new user or login,
                                                                                       // then they can create website
                                                                                       // credentials or get an existing
                                                                                       // password
        MongoClient mongoclient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase database = mongoclient.getDatabase("PasswordManager");
        boolean newuser = false;

        System.out.println("Are you a returning user?");
        if (scanner.nextLine().indexOf("y") == -1) {
            Intro(database);// no
            newuser = true;
        } else if (!Login(database))
            Intro(database);
        int menuInput;
        boolean loggedin = true;
        while (loggedin) {

            MongoCollection<Document> collection = database.getCollection(currentuser.getUserName());
            ;
            while (true) {
                menuInput = Menu(newuser);
                if (menuInput != 0)
                    break;
            }
            newuser = false;

            if (menuInput == 1) {
                System.out.println("What website would you like to create a password for?");
                scanner = new Scanner(System.in);
                String domain = scanner.nextLine();
                if (currentuser.checkExisting(domain, collection) == null) {
                    System.out.println("What is your username for this website?");
                    String domainuser = scanner.nextLine();
                    Website web = new Website(domain, domainuser,
                            currentuser.createwebsitePassword(domain, domainuser));

                    collection.insertOne(web.getDocument());
                    System.out.println("Your username for " + web + " is : " + web.getdomainuser());
                    System.out.println("Your password for " + web + " is : " + web.getdomainpass());
                } else {
                    Document found = new Document();
                    found = currentuser.checkExisting(domain, collection);
                    System.out.println("Your credentials for this website already exists");
                    System.out.println(
                            "Your username for " + found.get("Domain") + " is :" + found.get("Website Username"));
                    System.out.println(
                            "Your password for " + found.get("Domain") + " is :" + found.get("Website Password"));
                }
            }
            if (menuInput == 2) {
                // scanner.nextLine();
                currentuser.searchWebsites(collection);
            }
            if (menuInput == 3)
                loggedin = false;
        }
        scanner.close();
        mongoclient.close();
    }
}
