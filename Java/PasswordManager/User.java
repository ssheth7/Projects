import java.net.MalformedURLException;
import java.util.*;

public class User { //Each user has a list of websites; each website has a username and a password
    private static ArrayList<Website> credentialsList = new ArrayList<Website>();
    private static String user;
    private static String pass;

    //Get and Set passwords for the variables
    public User(String username) {
        user = username;
    }

    public void setPassword(String password) {
        pass = password;
    }

    public String toString() {
        return user;
    }

    public void addWebsite(Website newWeb) {//when the user wants to create a password for a new website
        credentialsList.add(newWeb);
    }

    public boolean checkCredentials(String password) {
        if (password == pass)
            return true;
        return false;
    }

    public String createwebsitePassword(String domain, String domainuser) throws MalformedURLException {
        String webpass = encryptPass(user, domain);
        return webpass;
    }

    public void searchWebsites(String search) {//Prints credentials if the website the user entered correctly matches the a previously entered website;  If the website isn't found, the most similar website is suggested
        boolean found = false;
        for (int i = 0; i < credentialsList.size(); i++) {
            if (credentialsList.get(i).toString().equals(search)) {
                System.out.println("Your username for " + search + " is " + credentialsList.get(i).getdomainuser());
                System.out.println("Your password for " + search + " is " + credentialsList.get(i).getdomainpass());
                found = true;
            }
        }
        if(!found)
        {
            System.out.println("Couldn't find the website, were you searching for");
        int numSimLet;
        int indexMostsim = 0;
        int mostSim = 0;
        for(int i = 0; i < credentialsList.size();i++)
            {
                numSimLet = 0;
                for(int j = 0; j < credentialsList.get(i).toString().length();j++)
                for(int k = 0; k < search.length();k++)
                    if(search.charAt(k) == credentialsList.get(i).toString().charAt(j)){
                        numSimLet++;
                    }
                    if(numSimLet >= mostSim)
                        indexMostsim = i;
                        mostSim = numSimLet;
                }
            System.out.print(credentialsList.get(indexMostsim).toString() + "? " +'\n');
        }
    }

    private String encryptPass(String user, String domain) {//Encrypts password based on the user's username and the domain of the website(TODO)
        return user + domain;
    }
}