/*Things to do:
Remove dependencies on local User ArrayList 
Write and read user credentials from MongoDB server
Create Password Encryption
Create UI
*/

package org.mongodb;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;

import org.bson.Document;
import javax.print.Doc;

public class PasswordManager {
    private static ArrayList<User> userlist = new ArrayList<User>();
    
    private static User currentuser;

    public static void Intro(MongoDatabase database) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to your password manager, enter your username: ");
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
        Scanner scanner = new Scanner(System.in);
        boolean prevUser = true;
        String username = "";
        while (prevUser) {
            System.out.println("What is your username?");
            username = scanner.nextLine();
            if (username.equals("create"))
                return !prevUser;
            try {
                MongoCollection<Document> collection = database.getCollection(username);
                
                while (true) {
                    System.out.println("What is your password?");
                    String passInput = scanner.nextLine();
                    System.out.println(collection.find().first().get("Password"));
                    if (collection.find().first().get("Password").equals(passInput)) {
                        System.out.println("Logged in as " + username);
                        return true;
                    } 
                    else if (passInput.indexOf("create") > -1)
                        return false;
                    else
                        System.out.println("Wrong password! Type 'create' to create a new account.");
                }

            } catch (NullPointerException e) {
                System.out.println("Username not found, type create to create a new account.");
            }
        }
        return false;
    }

    public static String stripString(String search) { // will condense a url from www.github.com to github
        int startIndex = 0;
        int endIndex = search.length();
        if (search.contains("www."))
            startIndex = 4;
        else if (search.contains("http://"))
            startIndex = 7;
        if (search.indexOf(".com") != -1)
            endIndex = search.indexOf(".com");
        return search.substring(startIndex, endIndex);
    }

    public static int Menu(boolean newUser) { // A menu for the user once they have logged in
        Scanner scanner = new Scanner(System.in);
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
        System.out.println("Collection sampleCollection selected successfully");

        Document passDoc = new Document("Password", currentuser.getPassword());
        collection.insertOne(passDoc);

        // Getting the iterable object
        FindIterable<Document> iterDoc = collection.find();
        // int i = 1;

        // Getting the iterator
        Iterator<Document> it = iterDoc.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
            // i++;
        }

        System.out.println("Collection created successfully");
        for (String name : database.listCollectionNames()) {
            System.out.println(name);

        }
    }

    public static void main(String[] args) throws MalformedURLException, IOException { // First the user can either
                                                                                       // create a new user or login,
                                                                                       // then they can create website
                                                                                       // credentials or get an existing
                                                                                       // password
        MongoClient mongoclient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase database = mongoclient.getDatabase("PasswordManager");
        Scanner scanner = new Scanner(System.in);
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
            while (true) {
                menuInput = Menu(newuser);
                if (menuInput != 0)
                    break;
            }
            newuser = false;

            if (menuInput == 1) {
                System.out.println("What website would you like to create a password for?");
                String domain = scanner.nextLine();
                if (currentuser.checkExisting(domain) == null) {
                    System.out.println("What is your username for this website?");
                    String domainuser = scanner.nextLine();
                    Website web = new Website(domain, domainuser,
                            currentuser.createwebsitePassword(domain, domainuser));
                    currentuser.addWebsite(web);
                    System.out.println("Your username for " + web + " is : " + web.getdomainuser());
                    System.out.println("Your password for " + web + " is : " + web.getdomainpass());
                } else {
                    System.out.println("Your credentials for this website already exists");
                    System.out.println("Your username for " + currentuser.checkExisting(domain) + " is :"
                            + currentuser.checkExisting(domain).getdomainuser());
                    System.out.println("Your password for " + currentuser.checkExisting(domain) + " is :"
                            + currentuser.checkExisting(domain).getdomainpass());
                }
            }
            if (menuInput == 2) {
                System.out.println("Type the website of the credentials you wish to recieve");
                String getwebsite = scanner.nextLine();
                currentuser.searchWebsites(getwebsite);
            }
            if (menuInput == 3)
                loggedin = false;
        }
        scanner.close();

    }
}