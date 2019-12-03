package Maven;
import java.net.URL;

import org.bson.Document;
import com.mongodb.client.MongoDatabase;;

import java.net.MalformedURLException;

public class Website {
    private String domain;
    private String domainuser;
    private String webpassword;
    private Document toCol = new Document("domain", domain).append("Website username", domainuser).append("Website Password",webpassword);
    public Website(String url) throws MalformedURLException {
        domain = stripUrl(url);
    }

    public Website(String url, String domainuser, String webpassword) throws MalformedURLException  {//Each website has a domain name, username, and a website password
        domain = stripUrl(url);
        this.domainuser = domainuser;
        this.webpassword = webpassword;
    
    }

    public String toString() {
        return domain;
    }

    public void setPassword(String webpassword) {
        this.webpassword = webpassword;
    }

    public String getdomainuser() {
        return domainuser;
    }

    public String getdomainpass() {
        return webpassword;
    }
    public Document getDocument(){
        return toCol;
    }

    public String stripUrl(String url) throws MalformedURLException {// Strips url String from http://domain.com or // www.domain.com to "domain"
        if (url.indexOf("http") > -1) {//http:// exists in String
            URL lURL = new URL(url);
            url = lURL.getHost().toString();
        }
         else if(url.indexOf("www") > -1){//www does exist
            url = url.substring(4);
        }
        if (url.indexOf(".com") != -1) {
            url = url.substring(0, url.indexOf(".com"));
        }
        return url;
    }
}