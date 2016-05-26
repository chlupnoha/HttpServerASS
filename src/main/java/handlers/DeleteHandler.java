package handlers;


import cache.FileCacheService;
import server.HttpExchanger;
import server.SimpleHTTPServer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.FileUtil;
import server.HttpResponseType;

/**
 * Handle DELETE Method
 * @author chlupnoha
 */
public class DeleteHandler extends AbstractHttpHandler implements Handler {

    @Override
    public void handle(HttpExchanger httpRequest) {
        String route = SimpleHTTPServer.WWW_DIR + httpRequest.getSimpleRequestParser().getRoute();
        try {
            File file = new File(route);
            String folder = file.getParentFile().getPath();
            
            if (FileUtil.checkHtaccess(folder)
                    && !checkAuthorizationBase64(folder, httpRequest.getSimpleRequestParser().getAuthorization())) {
                sendResponse(HttpResponseType._401_UNAUTHRORIZED, httpRequest);
                return;
            }

            boolean b= file.delete();
            if(b){
                sendResponse(HttpResponseType._204_NO_CONTENT, httpRequest);
                FileCacheService.getInstance().refreshKey(route);
                Logger.getLogger(DeleteHandler.class.getName()).log(Level.INFO, "Odpoved 204 odeslana, file na route: {0} byl smazan", folder);
            }else{
                sendResponse(HttpResponseType._404_NOT_FOUND, httpRequest);
                Logger.getLogger(DeleteHandler.class.getName()).log(Level.SEVERE, "File nenalezen");                
            }            
        } catch (FileNotFoundException ex) {
            sendResponse(HttpResponseType._404_NOT_FOUND, httpRequest);
            Logger.getLogger(DeleteHandler.class.getName()).log(Level.SEVERE, "File nenalezen");
        } catch (IOException | NullPointerException ex) {
            sendResponse(HttpResponseType._404_NOT_FOUND, httpRequest);
            Logger.getLogger(DeleteHandler.class.getName()).log(Level.SEVERE, "File nenalezen");
        }
    }

}
