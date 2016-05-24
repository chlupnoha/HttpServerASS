/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
public class SimpleHeaderParserTest {

    private static SimpleHeaderParser parse;

    public SimpleHeaderParserTest() throws IOException {
    }

    @BeforeClass
    public static void setUpClass() throws IOException{
        String headerSample = "GET /route HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/49.0.2623.108 Chrome/49.0.2623.108 Safari/537.36\n"
                + "Cache-Control: no-cache\n"
                + "Authorization: pass\n"
                + "token: ok2ffe9qubnk9dpmxgju5jlupmp6poijrxcw9zh99ttvsr5x7u\n"
                + "Postman-Token: e6197af4-e72b-ffe5-9fb6-cb0abb14b801\n"
                + "Accept: */*\n"
                + "Accept-Encoding: gzip, deflate, sdch\n"
                + "Accept-Language: cs-CZ,cs;q=0.8,en;q=0.6\n"
                + "Cookie: nette-browser=moif77badm; PHPSESSID=q6eo38pj0krd1neccnmn3pd4v4";

        InputStream is = new ByteArrayInputStream(headerSample.getBytes());
        parse = new SimpleHeaderParser(is);
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
    public void testInit(){
        assertNotNull(parse);
    }
    
    /**
     * Test of getMethod method, of class SimpleHeaderParser.
     */
    @Test
    public void testGetMethod() {
        assertEquals("GET", parse.getMethod());
    }

    /**
     * Test of getAuthorization method, of class SimpleHeaderParser.
     */
    @Test
    public void testGetAuthorization() {
        assertEquals("pass", parse.getAuthorization());
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

}
