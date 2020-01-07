/*Things to do:
Create UI
*/

package Maven;

import java.io.IOException;
import java.net.MalformedURLException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class PasswordManager {
    private static Scanner scanner = new Scanner(System.in); 
    private static User currentuser; //Global object that allows all methods to connect to the user's collection

    public static boolean Intro(MongoDatabase database) throws IOException {//Creates a new collection for the user
        // Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to your password manager, enter your username: ");
        System.out.println("It must be atleast of length 5");

        boolean invaliduser = true;
        while (invaliduser) {//while loop that allows the user to create their username based on the given requirements
            String username = scanner.nextLine();
            if (username.length() < 5)
                System.out.println("Username size too short!");
            boolean inDB = false;

            MongoCollection<Document> collection = database.getCollection(username);
            try {//if a collection belonging to the same user if found, then the user will be asked to choose a different username
                collection.find().first().get("Password");
                inDB =true;
                System.out.println("This username already exists");
                System.out.println("Is this your account?");
                String signIn = scanner.nextLine();
                    if(signIn.indexOf('y') != -1){//user would like to sign in
                        if( Login(database, username)) //if the user successfully logs in, the user is able access their website 
                            return false;           //credentials
                    }
            } catch (NullPointerException e) {
                inDB =false;
            }

            if (!inDB && username.length() >= 5) {//the user's proposed username meet all requirements
                User user = new User(username);
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
        while (invalidpass) {//user is stuck in while loop until they create a valid password
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

            if (!flags) {//if there are no flags, the current user's credentials are set
                invalidpass = false;
                currentuser.setPassword(pass);
            }
        }
        UploadtoDB(database);//the current user credentials are then uploaded to the database
        return true;
    }

    public static boolean Login(MongoDatabase database, String fromCreate) { // If the user already has a login, this method checks the
                                                          // validity of the credentials
        // Scanner scanner = new Scanner(System.in);
        boolean prevUser = true;
        String username = "";
        boolean validuser;
        if(fromCreate == null)
        while (prevUser) {//User enters his username
            System.out.println("What is your username?");
            username = scanner.nextLine();
            if (username.equals("create"))//if the user realizes that they do not have an account,  they can create a new account
                return !prevUser;
            MongoCollection<Document> collection = database.getCollection(username);
            try {
                collection.find().first().get("Password");
                validuser = true;
            } catch (NullPointerException e) {
                System.out.println("Username not found, type create to create a new account.");
                validuser = false;
            }
            while (validuser) {//user is in a while loop until they get the correct credentials or decide to create a new account
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
        else {
            while (true) {//If the user initially thinks they don't have an account but find that they do after entering their
                MongoCollection<Document> collection = database.getCollection(fromCreate);//username
                System.out.println("What is your password?");
                String passInput = scanner.nextLine();
                System.out.println(collection.find().first().get("Password"));
                if (collection.find().first().get("Password").equals(passInput)) {
                    System.out.println("Logged in as " + fromCreate);
                    User user = new User(fromCreate);
                    currentuser = user;
                    currentuser.setPassword(passInput);
                    return true;
                } else if (passInput.indexOf("create") > -1)
                    return false;
                else
                    System.out.println("Wrong password! Type 'create' to create a new account.");//user can opt to create a new acc
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

   

    public static void main(String[] args) throws MalformedURLException, IOException { // First the user can either
                                                                                       // create a new user or login,
                                                                                       // then they can create website
                                                                                       // credentials or get an existing
                                                                                       // password
        SpringApplication.run(PasswordManager.class, args);
        boolean newuser = false;
        
        MongoClientURI uri = new MongoClientURI(
        "mongodb://username:<Password>@cluster0-shard-00-00-w47ob.mongodb.net:27017,cluster0-shard-00-01-w47ob.mongodb.net:27017,cluster0-shard-00-02-w47ob.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("test");

        

        System.out.println("Are you a returning user?");
        if (scanner.nextLine().indexOf("y") == -1) {
            newuser = Intro(database);// no, the user is a new user
            
        } else if (!Login(database, null))//yes the user is returning
            newuser = Intro(database);
        int menuInput;
        boolean loggedin = true;
        while (loggedin) {//while the user is logged in, they are connected to their account's collection

            MongoCollection<Document> collection = database.getCollection(currentuser.getUserName());
            
            while (true) {//user is asked to enter a valid menu input(1, 2, or 3)
                menuInput = Menu(newuser);
                if (menuInput != 0)
                    break;
            }
            newuser = false;

            if (menuInput == 1) {//user creates website credentials
                System.out.println("What website would you like to create a password for?");
                scanner = new Scanner(System.in);
                String domain = scanner.nextLine();
                if (currentuser.checkExisting(domain, collection) == null) {//checks to make sure the user doesn't already website credentials
                    System.out.println("What is your username for this website?");
                    String domainuser = scanner.nextLine();
                    Website web = new Website(domain, domainuser,
                            currentuser.createwebsitePassword(domain, domainuser));

                    collection.insertOne(web.getDocument());//inserts the user's credentials to their collection
                    System.out.println("Your username for " + web + " is : " + web.getdomainuser());
                    System.out.println("Your password for " + web + " is : " + web.getdomainpass());
                } else {
                    Document found = new Document();
                    found = currentuser.checkExisting(domain, collection);
                    System.out.println("Your credentials for this website already exists");//prints the user's credentials for an 
                                                                                            //existing website 
                    System.out.println(
                            "Your username for " + found.get("Domain") + " is :" + found.get("Website Username"));
                    System.out.println(
                            "Your password for " + found.get("Domain") + " is :" + found.get("Website Password"));
                }
            }
            if (menuInput == 2) {//searches the user's collection for a website
                currentuser.searchWebsites(collection);
            }
            if (menuInput == 3)
                loggedin = false;
        }
        scanner.close();
        mongoClient.close();
        
    }
}
