import java.net.URL;
import java.net.MalformedURLException;

public class Website {
    private String domain;
    private String domainuser;
    private String webpassword;

    public Website(String url, String domainuser, String webpassword) throws MalformedURLException  {//Each website has a domain name, username, and a website password
        
        domain = stripUrl(url);
        this.domainuser = domainuser;
        this.webpassword = webpassword;
    }

    public String toString() {
        return domain;
    }

    public void setPassword(String encryptedpass) {
        webpassword = encryptedpass;
    }

    public String getdomainuser() {
        return domainuser;
    }

    public String getdomainpass() {
        return webpassword;
    }

    public  String stripUrl(String url) throws MalformedURLException {//Strips url String from http://domain.com or www.domain.com to "domain"
        if (url.indexOf("www") == -1) {
            URL lURL = new URL(url);
            url =  lURL.getHost().toString();
        } else {
            url = url.substring(4);
        }
        if (url.indexOf(".com") != -1) {
            url =  url.substring(0, url.indexOf(".com"));
        }
        return url;
    }
}