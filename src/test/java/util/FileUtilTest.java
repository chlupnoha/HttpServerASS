package util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chlupnoha
 */
public class FileUtilTest {

    public FileUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createFolder method, of class FileUtil.
     */
    @Test
    public void testCreateFolder() {
        System.out.println("createFolder");
        String name = "testFolder";
        //create
        FileUtil.createFolder(name);
        File theDir = new File(name);
        assertTrue(theDir.exists());
        //clear
        theDir.delete();
        assertTrue(!theDir.exists());
    }

    /**
     * Test of createAuthorizationHtaccess method, of class FileUtil.
     */
    @Test
    public void testCreateAuthorizationHtaccess() throws Exception {
        System.out.println("createAuthorizationHtaccess");
        String webSecretFolder = "www/" + "secret";
        FileUtil.createFolder(webSecretFolder);
        FileUtil.createAuthorizationHtaccess(webSecretFolder, "user", "password");
        File f = new File(webSecretFolder + "/.htaccess");
        assertTrue(f.exists() && !f.isDirectory());

        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        assertTrue(line.equals("user:cGFzc3dvcmQ="));
        br.close();
    }

    /**
     * Test of checkHtaccess method, of class FileUtil.
     */
    @Test
    public void testCheckHtaccess() {
        assertTrue(FileUtil.checkHtaccess("www/secret"));
        assertFalse(FileUtil.checkHtaccess("www"));
    }

    /**
     * Test of createTxtFile method, of class FileUtil.
     */
    @Test
    public void testCreateTxtFileAndDelete() {
        try {
            FileUtil.createFile("www", "try.txt", "content");
            assertTrue(true);
            FileUtil.createFile("www", "test/zkouska.html", "HTML CONTENTTR".getBytes(StandardCharsets.UTF_8));
            assertTrue(true);
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
            assertTrue(false);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            assertTrue(false);
        }

        try {
            FileUtil.deleteFile("www", "try.txt");
            assertTrue(true);
            FileUtil.deleteFile("www", "test/zkouska.html");
            assertTrue(true);
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
            assertTrue(false);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            assertTrue(false);
        }
    }

}
