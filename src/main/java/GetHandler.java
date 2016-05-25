
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chlupnoha
 */
public class GetHandler extends AHttpHandler implements Handler {

    @Override
    public void handle(HttpExchanger httpRequest) {
        File file = new File("www/text.txt");
        try {
            String contentType = Files.probeContentType(Paths.get("www/text.txt"));
            FileInputStream fis;
            fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            sendResponse(HttpResponseType._200_OK, new String(data, "UTF-8"), contentType, httpRequest);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GetHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GetHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        //String str = new String(data, "UTF-8");
    }

}
