package util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.io.IOUtils;

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
        boolean delete = theDir.delete();
        assertTrue(delete);
        //assertTrue(!theDir.exists());
    }

    /**
     * Test of createAuthorizationHtaccess method, of class FileUtil.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateAuthorizationHtaccess() throws Exception {
        System.out.println("createAuthorizationHtaccess");
        String webSecretFolder = "www/" + "secret";
        FileUtil.createFolder(webSecretFolder);
        FileUtil.createAuthorizationHtaccess(webSecretFolder, "user", "password");
        File f = new File(webSecretFolder + "/.htaccess");
        assertTrue(f.exists() && !f.isDirectory());

//        FileInputStream fileStream = new FileInputStream(f);
//        InputStreamReader inputStream = new InputStreamReader(fileStream, "UTF-8");
//        BufferedReader br = new BufferedReader(inputStream);
//        String line = br.readLine();
        String line =new String(Files.readAllBytes(Paths.get(webSecretFolder + "/.htaccess")), StandardCharsets.UTF_8);
        System.out.println("line: " + line);
//        String line = IOUtils.toString(fileStream, "UTF-8");

        assertTrue(line.equals("user:cGFzc3dvcmQ=\n"));
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
