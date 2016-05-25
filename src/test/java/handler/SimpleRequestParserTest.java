package handler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import server.SimpleRequestParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
public class SimpleRequestParserTest {

    private static SimpleRequestParser parse;
    
    public SimpleRequestParserTest() {
    }
    @BeforeClass
    public static void setUpClass() throws IOException {
        String headerSample = "PUT /route HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: 20\n"
                + "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/49.0.2623.108 Chrome/49.0.2623.108 Safari/537.36\n"
                + "Cache-Control: no-cache\n"
                + "Origin: chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop\n"
                + "test: test\n"
                + "Content-Type: text/plain;charset=UTF-8\n"
                + "Authorization: test\n"
                + "Postman-Token: 8c98aa4f-0ce4-8958-ae79-76727aa57113\n"
                + "Accept: */*\n"
                + "Accept-Encoding: gzip, deflate, sdch\n"
                + "Accept-Language: cs-CZ,cs;q=0.8,en;q=0.6\n"
                + "Cookie: nette-browser=moif77badm; PHPSESSID=q6eo38pj0krd1neccnmn3pd4v4\r\n\r\n" 
                + "tady pak hrozne body\n";

        InputStream is = new ByteArrayInputStream(headerSample.getBytes());
        parse = new SimpleRequestParser(is);
        System.out.println(parse.getMethod());
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

    @Test
    public void testInit() {
        assertNotNull(parse);
    }

    /**
     * Test of getMethod method, of class SimpleHeaderParser.
     */
    @Test
    public void testGetMethod() {
        assertEquals("PUT", parse.getMethod());
    }

    /**
     * Test of getAuthorization method, of class SimpleHeaderParser.
     */
    @Test
    public void testGetAuthorization() {
        assertEquals("test", parse.getAuthorization());
    }

    /**
     * Test of getUrl method, of class SimpleHeaderParser.
     */
    @Test
    public void testGetUrl() {
        assertEquals("localhost:8080", parse.getUrl());
    }

    /**
     * Test of getRoute method, of class SimpleHeaderParser.
     */
    @Test
    public void testGetRoute() {
        assertEquals("/route", parse.getRoute());
    }
    /**
     * Test of getBody method, of class SimpleRequestParser.
     */
    @Test
    public void testGetBody() {
        assertEquals("tady pak hrozne body", parse.getBody());
    }
    
}
