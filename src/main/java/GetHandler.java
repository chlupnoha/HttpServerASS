
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.FileUtil;

/**
 *
 * @author chlupnoha
 */
public class GetHandler extends AbstractHttpHandler implements Handler {

    @Override
    public void handle(HttpExchanger httpRequest) {
        String route = SimpleHTTPServer.WWW_DIR + httpRequest.getSimpleRequestParser().getRoute();
        try {
            File file = new File(route);
            String folder = file.getParentFile().getPath();
            System.out.println(folder);
            System.out.println(FileUtil.checkHtaccess(folder));

            if (FileUtil.checkHtaccess(folder)
                    && !checkAuthorizationBase64(folder, httpRequest.getSimpleRequestParser().getAuthorization())) {
                sendResponse(HttpResponseType._401_UNAUTHRORIZED, httpRequest);
                return;
            }

            Path p = Paths.get(route);
            String contentType = Files.probeContentType(p);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            sendResponse(HttpResponseType._200_OK, data, contentType, httpRequest);
            Logger.getLogger(GetHandler.class.getName()).log(Level.INFO, "Odpoved 200 odeslana, content: {0}", new Object[]{new String(data, StandardCharsets.UTF_8)});
        } catch (FileNotFoundException ex) {
            sendResponse(HttpResponseType._404_NOT_FOUND, httpRequest);
            Logger.getLogger(GetHandler.class.getName()).log(Level.SEVERE, "File nenalezen");
        } catch (IOException | NullPointerException ex) {
            sendResponse(HttpResponseType._404_NOT_FOUND, httpRequest);
            Logger.getLogger(GetHandler.class.getName()).log(Level.SEVERE, "File nenalezen");
        }
        //401, pak poslat prosbu o prihlaseni
        //a v principu hotovo
        //String str = new String(data, "UTF-8");
        
        //401, pak poslat prosbu o prihlaseni 

        //a v principu hotovo
        //String str = new String(data, "UTF-8");
    }

}
