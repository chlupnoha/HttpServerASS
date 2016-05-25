package handlers;


import server.SimpleHTTPServer;
import server.HttpExchanger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.FileUtil;
import server.HttpResponseType;



/**
 *
 * @author chlupnoha
 */
public class PutHandler extends AbstractHttpHandler implements Handler{

    /**
     * Can handle just raw Requests, cause of parser
     * @param httpRequest 
     */
    @Override
    public void handle(HttpExchanger httpRequest) {
        String route = SimpleHTTPServer.WWW_DIR + httpRequest.getSimpleRequestParser().getRoute();
        try {
            File file = new File(route);
            String folder = file.getParentFile().getPath();
            
            //htaccess for updating files in folder
            if (FileUtil.checkHtaccess(folder)
                    && !checkAuthorizationBase64(folder, httpRequest.getSimpleRequestParser().getAuthorization())) {
                sendResponse(HttpResponseType._401_UNAUTHRORIZED, httpRequest);
                return;
            }

            FileUtil.createFile(".", route, httpRequest.getSimpleRequestParser().getBody());
            
            sendResponse(HttpResponseType._201_CREATED, httpRequest);
            Logger.getLogger(GetHandler.class.getName()).log(Level.INFO, "Odpoved 201 odeslana");
        } catch (FileNotFoundException ex) {
            sendResponse(HttpResponseType._404_NOT_FOUND, httpRequest);
            Logger.getLogger(GetHandler.class.getName()).log(Level.SEVERE, "Folder nenalezen");
        } catch (IOException | NullPointerException ex) {
            sendResponse(HttpResponseType._500_INTERNAL_SERVER_ERROR, httpRequest);
            Logger.getLogger(GetHandler.class.getName()).log(Level.SEVERE, "Internal server eror");
        }
    }

}
