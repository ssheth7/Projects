package Maven;

import java.net.MalformedURLException;
import java.util.*;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import com.mongodb.BasicDBObject;

//import static com.mongodb.client.model.Projections.excludeId;
import org.bson.Document;

public class User { // Each user has a list of websites; each website has a username and a password
    
    private String user;
    private String pass;
    private static Scanner scanner = new Scanner(System.in);

    // Get and Set methods for the private variables
    public User(String user) {
        this.user = user;
    }

    public void setPassword(String pass) {
        this.pass = pass;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserName() {
        return user;
    }

    public String getPassword() {
        return pass;
    }

    public String toString() {
        return user;
    }


    public Document checkExisting(String search, MongoCollection<Document> collection) throws MalformedURLException { 
        //check's user's list of websites to make sure the user doesnt create dup. credentials
        //returns credentials if it does exists
        Website searchW = new Website(search);
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("Domain", searchW.toString());
        FindIterable<Document> iterable = collection.find(whereQuery);
        for(Document doc : iterable)
        if(doc.getString("Domain").equals(searchW.toString())) 
               return doc;
        return null;
    }

    public String createwebsitePassword(String domain, String domainuser) throws MalformedURLException {//Queries the user 
        System.out.println("What length would you like your password?");                            //for their password length
        int plength = scanner.nextInt();
        return encryptPass(domainuser, domain, plength);
    }

    public void searchWebsites(MongoCollection<Document> collection) throws MalformedURLException {// Prints credentials
                                                                                                   // if the website the
                                                                                                    // user entered correctly matches the a
                                                                                                    // previously entered website
        System.out.println("Type the website of the credentials you wish to recieve");
        String getwebsite = scanner.nextLine();
        Website search = new Website(getwebsite);
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("Domain", search.toString());
        FindIterable<Document> iterable = collection.find(whereQuery);
        try {//if credentials are found, the website's credentials are given
            System.out.println("Your username for " + getwebsite + " is: " + iterable.first().get("Website Username"));
            System.out.println("Your password for " + getwebsite + " is: " + iterable.first().get("Website Password"));
        } catch (NullPointerException e) {//if website credentials are not found
            System.out.println("You have not created credentials for " + getwebsite);
        }
    }

    private String encryptPass(String user, String domain, int plength) {// Encrypts password based on the user's
                                                                           // username and the domain of the website(TO
                                                                           // DO)
    
    String specialChars, numbers, unique;                                          
    
    boolean invalidpass = true;
    String encrypted = ""; 
    while (invalidpass) {//while loop continuously creates passwords until it meets requirements(special char, number, letters)

        specialChars = RandomStringUtils.randomAscii(plength); //creates random string of length plength - with specialChars
        
        numbers = RandomStringUtils.randomAlphanumeric(plength);//random string with numbers
        
        unique = RandomStringUtils.random(plength, (user+domain).toCharArray());//random chars taken from 
                                                                                //account username and domain          
        
        encrypted += specialChars.substring(0, 4) + numbers.substring(4, 7) + unique.substring(7,plength);//Builds the users password
        
         invalidpass = false;//assumes the password is valid until if statements set it to false
        
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(encrypted);
        if (!m.find()) {//sets invalidpass var to true if the password lacks a special char
            System.out.println("Your password does not contain a special character!");
            invalidpass = true;
        }

        p = Pattern.compile("[^0-9]");
        m = p.matcher(encrypted);

        if (!m.find()) {//sets invalid car to true if password lacks a number
            System.out.println("Your password does not have a number!");
            invalidpass = true;
        }

        p = Pattern.compile("[^a-z]");
        m = p.matcher(encrypted);
        if (!m.find()) {//sets invalid var to true if the password lacks lowercase letters
            System.out.println("Your password does not have lowercase letters!");
            invalidpass = true;
            }
        
            p = Pattern.compile("[^A-Z]");
            m = p.matcher(encrypted);
            if (!m.find()) {//sets invalid var to true if the password lacks uppercase letters
                System.out.println("Your password does not have upper letters!");
                invalidpass = true;
                }

        if (!invalidpass) //if the password does not enter any of the if statements-its a valid password 
        break;            //and it is returned and uploaded
            
    }
    return encrypted;

    }
}