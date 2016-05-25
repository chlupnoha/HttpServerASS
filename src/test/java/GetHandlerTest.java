/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class GetHandlerTest {
    
    public GetHandlerTest() {
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
     * Test of handle method, of class GetHandler.
     */
    @Test
    public void testHandleOK() {
        System.out.println("handle");
        HttpExchanger httpRequest = null;
        GetHandler instance = new GetHandler();
        instance.handle(httpRequest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handle method, of class GetHandler.
     */
    @Test
    public void testHandleNotFound() {
        System.out.println("handle");
        HttpExchanger httpRequest = null;
        GetHandler instance = new GetHandler();
        instance.handle(httpRequest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handle method, of class GetHandler.
     */
    @Test
    public void testHandleAuthorizationNotPass() {
        System.out.println("handle");
        HttpExchanger httpRequest = null;
        GetHandler instance = new GetHandler();
        instance.handle(httpRequest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handle method, of class GetHandler.
     */
    @Test
    public void testHandleAuthorizationPassed() {
        System.out.println("handle");
        HttpExchanger httpRequest = null;
        GetHandler instance = new GetHandler();
        instance.handle(httpRequest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
