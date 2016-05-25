package handler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import server.SimpleHTTPServer;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import util.SteamUtil;

/**
 *
 * @author chlupnoha
 */
public class GetHandlerTest {

    public GetHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        String[] args = {};
        SimpleHTTPServer.main(args);
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        SimpleHTTPServer.stopServer();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of handle method, of class GetHandler.
     *
     * @throws java.net.MalformedURLException
     * @throws java.net.ProtocolException
     */
    @Test
    public void testHandleOK() throws MalformedURLException, ProtocolException, IOException {
        String url = "http://localhost:8080/text.txt";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //default method is GET
//        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nGet request URL: " + url);

        String webContent = SteamUtil.convertStreamToString(con.getInputStream());
        System.out.println("web content: " + webContent);

        String fileContent = new String(Files.readAllBytes(Paths.get(SimpleHTTPServer.WWW_DIR + "/text.txt")));
        System.out.println("fileContent: " + fileContent);

        assertTrue(responseCode == 200);
        assertTrue(fileContent.equals(webContent));
    }

    /**
     * Test of handle method, of class GetHandler.
     *
     * @throws java.net.MalformedURLException
     * @throws java.net.ProtocolException
     */
    @Test
    public void testHandleOKImage() throws MalformedURLException, ProtocolException, IOException {
        String url = "http://localhost:8080/img.jpg";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //default method is GET
//        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nGet request URL: " + url);

        String webContent = SteamUtil.convertStreamToString(con.getInputStream());
        String fileContent = new String(Files.readAllBytes(Paths.get(SimpleHTTPServer.WWW_DIR + "/img.jpg")));

        assertTrue(responseCode == 200);
        assertTrue(fileContent.equals(webContent));
    }

    /**
     * Test of handle method, of class GetHandler.
     *
     * @throws java.net.MalformedURLException
     */
    @Test
    public void testHandleNotFound() throws MalformedURLException, IOException {
        String url = "http://localhost:8080/notFound.txt";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        int responseCode = con.getResponseCode();
        assertTrue(responseCode == 404);
    }

    /**
     * Test of handle method, of class GetHandler.
     *
     * @throws java.net.MalformedURLException
     */
    @Test
    public void testHandleAuthorizationNotPass() throws MalformedURLException, IOException {
        String url = "http://localhost:8080/secret/text.txt";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        int responseCode = con.getResponseCode();
        System.out.println("responseCode " + responseCode);
        con.getHeaderFields().toString();
        assertTrue(responseCode == 401);
    }

    /**
     * Test of handle method, of class GetHandler.
     * @throws java.io.IOException
     */
    @Test
    public void testHandleAuthorizationPassed() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:8080/secret/text.txt")
                .get()
                .addHeader("Authorization", "Basic dXNlcjpwYXNzd29yZA==")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "7ebc7aa1-478e-5161-3153-a8c184a03944")
                .build();

        Response response = client.newCall(request).execute();
        
        System.out.println("code: " + response.code());
        
        assertTrue(response.code() == 200);
    }

}
