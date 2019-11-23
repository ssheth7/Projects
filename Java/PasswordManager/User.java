import java.net.MalformedURLException;
import java.util.*;

public class User {
    private static ArrayList<Website> credentialsList = new ArrayList<Website>();
    private static String user;
    private static String pass;

    public User(String username) {
        user = username;    
    }

    public void setPassword(String password){
        pass = password;
    }

    public String toString(){
        return user;
    }

    public void addWebsite(Website newWeb){
            credentialsList.add(newWeb);
    }

    public boolean checkCredentials(String password){
            if(password == pass)
            return true;
            return false;
    }

    public String createPassword(String domain, String domainuser) throws MalformedURLException {
       Website current = new Website(domain, domainuser);
        credentialsList.add(current);
        String webpass = encryptPass(user, domain);
        current.setPassword(webpass);
        return webpass;
    }

    private String encryptPass(String user, String domain){
        return user+domain;
    }
}