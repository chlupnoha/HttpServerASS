package handlers;


import server.HttpExchanger;
import handlers.GetHandler;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.util.Arrays;
import util.ServerTime;
import server.HttpResponseType;

/**
 *
 * @author chlupnoha
 */
public abstract class AbstractHttpHandler {

    public void sendResponse(HttpResponseType code, HttpExchanger httpExchanger) {
        String httpResponse;
        if(code.equals(HttpResponseType._401_UNAUTHRORIZED)){
            httpResponse = "HTTP/1.0 401 Authorization Required\n";
            httpResponse += "WWW-Authenticate: Basic realm=\"Secure Area\"" + "\r\n\r\n";
        }else{
            httpResponse = "HTTP/1.1 " + code.toString() + "\r\n\r\n";
        }
        try {
            httpExchanger.getSocket().getOutputStream().write(httpResponse.getBytes());
            httpExchanger.getSocket().close();
        } catch (IOException ex) {
            Logger.getLogger(GetHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendResponse(HttpResponseType code, byte[] data, String contentType, HttpExchanger httpExchanger) {
        String httpResponse = "HTTP/1.1 " + code.toString() + "\n";
        httpResponse += "Date: " + ServerTime.getServerTime() + "\n";
        httpResponse += "Content-Length: " + data.length + "\n";
        httpResponse += "Content-Type: " + contentType + "\r\n\r\n";

        try {
            httpExchanger.getSocket().getOutputStream().write(httpResponse.getBytes("UTF-8"));
            httpExchanger.getSocket().getOutputStream().write(data);
            httpExchanger.getSocket().close();
        } catch (IOException ex) {
            Logger.getLogger(GetHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkAuthorizationBase64(String folder, String authorization) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(folder + "/.htaccess"));
        String line = br.readLine();
        Logger.getLogger(GetHandler.class.getName()).log(Level.INFO, "Htacces data: {0}", line);
        Logger.getLogger(GetHandler.class.getName()).log(Level.INFO, "Authorization data(base64): {0}", authorization);
        br.close();
        
        if(authorization == null){
            return false;
        }

        String decodedUserPass;
        try {
            String[] isBasic = authorization.split(" ");
            System.out.println(Arrays.toString(isBasic));
            if(isBasic.length != 2 || !isBasic[0].equals("Basic")){
                Logger.getLogger(GetHandler.class.getName()).log(Level.INFO, "No Basic in Authorization");
                return false;
            }
            decodedUserPass = new String(Base64.decode(isBasic[1]));
            String[] userPass = decodedUserPass.split(":");
            if(userPass.length != 2){
                Logger.getLogger(GetHandler.class.getName()).log(Level.INFO, "No valid data user:base64(password)");
                return false;
            }
            decodedUserPass = userPass[0] + ":" + Base64.encode(userPass[1].getBytes(StandardCharsets.UTF_8));
            Logger.getLogger(GetHandler.class.getName()).log(Level.INFO, "DECODED HEADER: {0}", decodedUserPass);
        } catch (Base64DecodingException ex) {
            Logger.getLogger(AbstractHttpHandler.class.getName()).log(Level.SEVERE, "Authorization cant be decoded.");
            return false;
        }
        return line.equals(decodedUserPass);
    }
}
