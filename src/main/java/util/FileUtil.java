package util;

import java.util.Base64;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
                boolean b = theDir.mkdir();
                if(b){
                    Logger.getLogger(FileUtil.class.getName()).log(Level.INFO, "FOLDER CREATED");
                }else{
                    Logger.getLogger(FileUtil.class.getName()).log(Level.INFO, "CANT BE CREATED");
                }
            } catch (SecurityException se) {
                Logger.getLogger(FileUtil.class.getName()).log(Level.WARNING, "CANT CREATE FILE");
            }
        }
    }

//    public static void createFile(String pathWithName, String content) throws FileNotFoundException, UnsupportedEncodingException {
//        PrintWriter writer = new PrintWriter(pathWithName, "UTF-8");
//        writer.println(content);
//        writer.close();
//    }
    public static void createFile(String pathToFolder, String name, String content) throws FileNotFoundException, UnsupportedEncodingException {
        String pathToFile = pathToFolder + "/" + name;
        PrintWriter writer = new PrintWriter(pathToFile, "UTF-8");
        writer.println(content);
        writer.close();
    }

    public static void createFile(String pathToFolder, String name, byte[] content) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        String pathToFile = pathToFolder + "/" + name;
        FileOutputStream stream = new FileOutputStream(pathToFile);
        try {
            stream.write(content);
        } finally {
            stream.close();
        }
    }

    public static void deleteFile(String pathToFolder, String name) throws FileNotFoundException, UnsupportedEncodingException, Exception {
        String pathToFile = pathToFolder + "/" + name;
        System.out.println("path: " + pathToFile);
        File file = new File(pathToFile);

        if (!file.exists() || file.isDirectory()) {
            System.out.println("soubor neexistuje");
            throw new Exception("File dont exist or is a directory");
        }
        boolean res = file.delete();
        if (!res) {
            throw new Exception("file cant be deleted");
        }
    }

    //create htacess
    public static void createAuthorizationHtaccess(String pathToFolder, String user, String password) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(pathToFolder + "/.htaccess", "UTF-8");
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes("UTF-8"));
        System.out.println(encodedPassword);
        writer.println(user + ":" + encodedPassword);
        writer.close();
    }

    public static boolean checkHtaccess(String folder) {
        return new File(folder, ".htaccess").exists();
    }

}
