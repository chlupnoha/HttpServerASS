/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
    
}
