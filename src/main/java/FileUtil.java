
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * idea: http://stackoverflow.com/questions/3634853/how-to-create-a-directory-in-java
 * @author chlupnoha
 */
public class FileUtil {

    public static void createFolder(String name) {
        File theDir = new File(name);
        // if the directory does not exist, create it
        if (!theDir.exists()) {
            try {
                theDir.mkdir();
                Logger.getLogger(FileUtil.class.getName()).log(Level.INFO, "FOLDER CREATED");
            } catch (SecurityException se) {
                Logger.getLogger(FileUtil.class.getName()).log(Level.WARNING, "CANT CREATE FILE");
            }
        }
    }
    
    //create htacess
    

}
