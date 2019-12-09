package Maven;

import java.net.MalformedURLException;
import java.util.*;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.BasicDBObject;

//import static com.mongodb.client.model.Projections.excludeId;
import org.bson.Document;

public class User { // Each user has a list of websites; each website has a username and a password
    
    private String user;
    private String pass;
    private static Scanner scanner = new Scanner(System.in);

    // Get and Set passwords for the variables
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


    public Document checkExisting(String search, MongoCollection<Document> collection) throws MalformedURLException {// checks user's list of websites
        Website searchW = new Website(search);
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("Domain", searchW.toString());
        FindIterable<Document> iterable = collection.find(whereQuery);
        for(Document doc : iterable)
        {if(doc.getString("Domain").equals(searchW.toString()))
               System.out.println(doc); 
               return doc;}
        return null;
    }

    public String createwebsitePassword(String domain, String domainuser) throws MalformedURLException {
        System.out.println("What length would you like your password?");
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
        try {
            System.out.println("Your username for " + getwebsite + " is: " + iterable.first().get("Website Username"));
            System.out.println("Your password for " + getwebsite + " is: " + iterable.first().get("Website Password"));
        } catch (NullPointerException e) {
            System.out.println("You have not created credentials for " + getwebsite);
        }
    }

    private String encryptPass(String user, String domain, int plength) {// Encrypts password based on the user's
                                                                           // username and the domain of the website(TO
                                                                           // DO)
        String password = "";
        for (int i = 0; i < plength; i++)
            password += (int) (i * Math.random());
        String comp = user + domain;
        return comp;

    }
}