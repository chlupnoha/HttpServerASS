package handlers;

import server.HttpExchanger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import util.ServerTime;
import server.HttpResponseType;
import java.util.Base64;

/**
 * Abstract Handler for common operations in all Handlers
 * @author chlupnoha
 */
public abstract class AbstractHttpHandler {

    public void sendResponse(HttpResponseType code, HttpExchanger httpExchanger) {
        String httpResponse;
        if (code.equals(HttpResponseType._401_UNAUTHRORIZED)) {
            httpResponse = "HTTP/1.0 401 Authorization Required\n";
            httpResponse += "WWW-Authenticate: Basic realm=\"Secure Area\"" + "\r\n\r\n";
        } else {
            httpResponse = "HTTP/1.1 " + code.toString() + "\r\n\r\n";
        }
        try {
            httpExchanger.getSocket().getOutputStream().write(httpResponse.getBytes("UTF8"));
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
//        FileInputStream str = new FileInputStream(folder + "/.htaccess");
//        BufferedReader br = new BufferedReader(new InputStreamReader(str, "UTF8"));
//        str.close();
//        String line = br.readLine();
//        br.close();
         String line = new String(Files.readAllBytes(Paths.get(folder + "/.htaccess")), StandardCharsets.UTF_8);
        
        Logger.getLogger(GetHandler.class.getName()).log(Level.INFO, "Htacces data: {0}", line);
        Logger.getLogger(GetHandler.class.getName()).log(Level.INFO, "Authorization data(base64): {0}", authorization);

        if (authorization == null) {
            return false;
        }

        String decodedUserPass;
//        try {
            String[] isBasic = authorization.split(" ");
            System.out.println(Arrays.toString(isBasic));
            if (isBasic.length != 2 || !isBasic[0].equals("Basic")) {
                Logger.getLogger(GetHandler.class.getName()).log(Level.INFO, "No Basic in Authorization");
                return false;
            }
            decodedUserPass = new String(Base64.getDecoder().decode(isBasic[1]), StandardCharsets.UTF_8);
            String[] userPass = decodedUserPass.split(":");
            if (userPass.length != 2) {
                Logger.getLogger(GetHandler.class.getName()).log(Level.INFO, "No valid data user:base64(password)");
                return false;
            }
            decodedUserPass = userPass[0] + ":" + Base64.getEncoder().encodeToString(userPass[1].getBytes(StandardCharsets.UTF_8)) + "\n";
            Logger.getLogger(GetHandler.class.getName()).log(Level.INFO, "DECODED HEADER: {0}", decodedUserPass);
//        } catch (Base64DecodingException ex) {
//            Logger.getLogger(AbstractHttpHandler.class.getName()).log(Level.SEVERE, "Authorization cant be decoded.");
//            return false;
//        }
        return line.equals(decodedUserPass);
    }
}
