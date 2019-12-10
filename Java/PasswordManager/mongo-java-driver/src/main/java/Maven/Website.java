package Maven;
import java.net.URL;

import org.bson.Document;

import java.net.MalformedURLException;

public class Website {
    private String domain;//google
    private String domainuser;//google username
    private String webpassword;//google pass
    private Document toCol;//Document that is uploaded to MongoDb

    public Website(String url) throws MalformedURLException {
        domain = stripUrl(url);
    }//splices url to domain name

    public Website(String url, String domainuser, String webpassword) throws MalformedURLException  {//Each website has a domain name, 
                                                            //username, and a website password
        domain = stripUrl(url);
        this.domainuser = domainuser;
        this.webpassword = webpassword;
        toCol = new Document("Domain", domain).append("Website Username", domainuser).append("Website Password",webpassword);
    }//creates a website that is then converted to a document to be uploaded to the mongodb server


    public String toString() {
        return domain;
    }
    //get and set methods for private variables
    public void setdomain(String domain){this.domain = domain;}

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