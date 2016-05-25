
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author chlupnoha
 */
public abstract class AHttpHandler {

    public void checkHtacess(String location){
        
    }
    
    public void sendResponse(HttpResponseType code, HttpExchanger httpExchanger){
        String httpResponse = "HTTP/1.1 " + code.toString() + "\r\n\r\n";        
        try {
            httpExchanger.getSocket().getOutputStream().write(httpResponse.getBytes());
            httpExchanger.getSocket().close();
        } catch (IOException ex) {
            Logger.getLogger(GetHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public void sendResponse(HttpResponseType code, String message, HttpExchanger httpExchanger){
//        String httpResponse = "HTTP/1.1 " + code.toString() + "\r\n\r\n";        
//        httpResponse += "Content-Length: " + message.length() + "\n";
//        httpResponse += "Content-Type: " + "text/plain" + "\r\n\r\n";
//        
//        httpResponse += message;
//        try {
//            httpExchanger.getSocket().getOutputStream().write(httpResponse.getBytes());
//            httpExchanger.getSocket().close();
//        } catch (IOException ex) {
//            Logger.getLogger(GetHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    public void sendResponse(HttpResponseType code, String message, String contentType, HttpExchanger httpExchanger){
        String httpResponse = "HTTP/1.1 " + code.toString() + "\n";        
        httpResponse += "Content-Length: " + message.length() + "\n";
        httpResponse += "Content-Type: " + contentType + "\r\n\r\n";
        
        httpResponse += message;
        try {
            httpExchanger.getSocket().getOutputStream().write(httpResponse.getBytes());
            httpExchanger.getSocket().close();
        } catch (IOException ex) {
            Logger.getLogger(GetHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
