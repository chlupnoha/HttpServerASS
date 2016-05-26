package handlers;


import cache.FileCacheService;
import server.HttpExchanger;
import server.SimpleHTTPServer;
import handlers.Handler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.FileUtil;
import server.HttpResponseType;

/**
 *
 * @author chlupnoha
 */
public class GetHandler extends AbstractHttpHandler implements Handler {

    @Override
    public void handle(HttpExchanger httpRequest) {
        String route = SimpleHTTPServer.WWW_DIR + httpRequest.getSimpleRequestParser().getRoute();
        try {
            File file = FileCacheService.getInstance().getFileFromCach(route);
            //File file = new File(route);
            String folder = file.getParentFile().getPath();
            
            if (FileUtil.checkHtaccess(folder)
                    && !checkAuthorizationBase64(folder, httpRequest.getSimpleRequestParser().getAuthorization())) {
                sendResponse(HttpResponseType._401_UNAUTHRORIZED, httpRequest);
                return;
            }

            Path p = Paths.get(route);
            String contentType = Files.probeContentType(p);
//            FileInputStream fis = new FileInputStream(file);
            byte[] data = Files.readAllBytes(p);
//            fis.read(data);
//            fis.close();

            sendResponse(HttpResponseType._200_OK, data, contentType, httpRequest);
            Logger.getLogger(GetHandler.class.getName()).log(Level.INFO, "Odpoved 200 odeslana, route: {0}", folder);
        } catch (FileNotFoundException ex) {
            sendResponse(HttpResponseType._404_NOT_FOUND, httpRequest);
            Logger.getLogger(GetHandler.class.getName()).log(Level.SEVERE, "File nenalezen");
        } catch (IOException | NullPointerException ex) {
            sendResponse(HttpResponseType._404_NOT_FOUND, httpRequest);
            Logger.getLogger(GetHandler.class.getName()).log(Level.SEVERE, "File nenalezen");
        } catch (ExecutionException ex) {
            sendResponse(HttpResponseType._500_INTERNAL_SERVER_ERROR, httpRequest);
            Logger.getLogger(GetHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
