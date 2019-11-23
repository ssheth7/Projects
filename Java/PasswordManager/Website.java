import java.net.URL;
import java.net.MalformedURLException;
public class Website {
    private String domain;
    private String domainuser;
    private String webpassword;
    
    public Website(String url, String domainuser) throws MalformedURLException {
    URL lURL = new URL(url);
        domain = lURL.getHost();
        int endIndex = domain.indexOf(".com");
        domain = domain.substring(0, endIndex);
    }

    public String toString(){
        return domain;
    }

    public void setPassword(String encryptedpass){
        webpassword = encryptedpass;
    }

    public String getdomainuser(){
            return domainuser; 
    }

    public String getdomainpass(){
        return webpassword;
    }
}