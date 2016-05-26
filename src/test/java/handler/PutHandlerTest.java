package handler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import server.SimpleHTTPServer;
import java.io.File;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
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
public class PutHandlerTest {

    public PutHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        String[] args = {};
        SimpleHTTPServer.main(args);
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of handle method, of class PutHandler.
     */
    @Test
    public void testHandle() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, "tady pak hrozne body");
        Request request = new Request.Builder()
                .url("http://localhost:8080/test/t.html")
                .put(body)
                .addHeader("authorization", "test")
                .addHeader("test", "test")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "c9847ad1-108d-fbc9-6827-604bee24aad1")
                .build();

        Response response = client.newCall(request).execute();
        
        assertTrue(response.code()==201);
        
        File file = new File(SimpleHTTPServer.WWW_DIR + "/test/t.html");
        assertTrue(file.exists());
    }

    /**
     * Test of handle method, of class PutHandler.
     */
    @Test
    public void testHandleNotFoundFolder() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, "tady pak hrozne body");
        Request request = new Request.Builder()
                .url("http://localhost:8080/test/unknown/t.html")
                .put(body)
                .addHeader("authorization", "test")
                .addHeader("test", "test")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "c9847ad1-108d-fbc9-6827-604bee24aad1")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println("code: " + response.code());
        
        assertTrue(response.code()==404);
    }

    /**
     * Test of handle method, of class PutHandler.
     */
    @Test
    public void testHandleAuthorizationNotPass()throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, "tady pak hrozne body");
        Request request = new Request.Builder()
                .url("http://localhost:8080/secret/t.html")
                .put(body)
                .addHeader("Authorization", "test")
                .addHeader("test", "test")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "c9847ad1-108d-fbc9-6827-604bee24aad1")
                .build();

        Response response = client.newCall(request).execute();
        
        assertTrue(response.code()==401);
    }

    /**
     * Test of handle method, of class PutHandler.
     */
    @Test
    public void testHandleAuthorizationPassed()throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, "tady pak hrozne body");
        Request request = new Request.Builder()
                .url("http://localhost:8080/secret/t.html")
                .put(body)
                .addHeader("Authorization", "Basic dXNlcjpwYXNzd29yZA==")
                .addHeader("test", "test")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "c9847ad1-108d-fbc9-6827-604bee24aad1")
                .build();

        Response response = client.newCall(request).execute();
        
        assertTrue(response.code()==201);
        
        File file = new File(SimpleHTTPServer.WWW_DIR + "/secret/t.html");
        assertTrue(file.exists());
    }

}
