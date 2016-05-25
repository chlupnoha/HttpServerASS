package util;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * idea:
 * http://stackoverflow.com/questions/3634853/how-to-create-a-directory-in-java
 *
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
    public static void createAuthorizationHtaccess(String pathToFolder, String user, String password) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(pathToFolder + "/.htaccess", "UTF-8");
        String encodedPassword = Base64.encode(password.getBytes(StandardCharsets.UTF_8));
        System.out.println(encodedPassword);
        writer.println(user + ":" + encodedPassword);
        writer.close();

    }

    public static boolean checkHtaccess(String folder) {
        return new File(folder, ".htaccess").exists();
    }

}
