package Maven;  
import java.net.MalformedURLException;
import java.util.*;


import com.mongodb.client.model.Filters;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;

import org.bson.Document;

public class User { // Each user has a list of websites; each website has a username and a password
    private ArrayList<Website> credentialsList = new ArrayList<Website>();
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

    public void setUser(String user){
        this.user = user;
    }

    public String getUserName(){
        return user;
    }

    public String getPassword() {
        return pass;
    }

    public String toString() {
        return user;
    }
    

    public void addWebsite(Website newWeb) {// when the user wants to create a password for a new website
        credentialsList.add(newWeb);
    }

    public Website checkExisting(String search) throws MalformedURLException {// checks user's list of websites
        Website searchW = new Website(search);
        for (int i = 0; i < credentialsList.size(); i++)
            if (credentialsList.get(i).toString().equals(searchW.toString()))
                return credentialsList.get(i);
        return null;
    }

    public boolean checkCredentials(String password) {
        if (password == pass)
            return true;
        return false;
    }

    public String createwebsitePassword(String domain, String domainuser) throws MalformedURLException {
        System.out.println("What is the max password length allowed by the website?");
        int maxlength = scanner.nextInt();
        return encryptPass(domainuser, domain, maxlength);
    }

    public void searchWebsites(String search, MongoCollection<Document> collection) throws MalformedURLException {// Prints credentials if the website the
                                                                            // user entered correctly matches the a
                                                                            // previously entered website; If the
                                                                            // website isn't found, the most similar
                                                                            // website is suggested
        
        FindIterable<Document> iterable =  collection.find(Filters.eq("domain", search));
        System.out.println(iterable.first());
        System.out.println(iterable.first().get("domain"));

        FindIterable<Document> iterDoc = collection.find(); //Listing objects 
      // Getting the iterator 
      Iterator<Document> it = iterDoc.iterator(); 
    
      while (it.hasNext()) {  
         System.out.println(it.next());  
      }  
        /*Website searchW = new Website(search);
        System.out.println(search);
        search = searchW.toString();
        int numSimLet;
        int indexMostsim = 0;
        int mostSim = 0;
        for (int i = 0; i < credentialsList.size(); i++) {
            numSimLet = 0;
            for (int j = 0; j < credentialsList.get(i).toString().length(); j++)
                for (int k = 0; k < search.length(); k++)
                    if (search.charAt(k) == credentialsList.get(i).toString().charAt(j)) {
                        numSimLet++;
                    }
            if (numSimLet >= mostSim)
                {indexMostsim = i;
            mostSim = numSimLet;}
        }
        if (search.length() != credentialsList.get(indexMostsim).toString().length()) {
            System.out.print("Couldn't find the website, were you searching for ");
            System.out.println(credentialsList.get(indexMostsim).toString() + "? " + '\n');
            String suggest = scanner.nextLine();
            if (suggest.indexOf('y') == -1) {
                System.out.println("Would you like to create a password for " + search+ "?");
                suggest = scanner.nextLine();
                if (suggest.indexOf("y") != -1){// no -
                    System.out.println("What is your username for the website?");
                createwebsitePassword(search, scanner.nextLine());}
            }

            else {
                System.out.println(
                        "Your username for " + search + " is " + credentialsList.get(indexMostsim).getdomainuser());
                System.out.println(
                        "Your password for " + search + " is " + credentialsList.get(indexMostsim).getdomainpass());
            }
        }
    */}

    private String encryptPass(String user, String domain, int maxlength) {// Encrypts password based on the user's
                                                                           // username and the domain of the website(TO
                                                                           // DO)
        String password = "";
        for (int i = 0; i < maxlength; i++)
            password += (int) (i * Math.random());
        String comp = user + domain;
        return comp;

    }
}