/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

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
import server.SimpleHTTPServer;
import util.FileUtil;

/**
 *
 * @author chlupnoha
 */
public class DeleteHandlerTest {

    public DeleteHandlerTest() {
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
//
//    /**
//     * Test of handle method, of class DeleteHandler.
//     */
//    @Test
//    public void testHandle() {
//        System.out.println("handle");
//        HttpExchanger httpRequest = null;
//        DeleteHandler instance = new DeleteHandler();
//        instance.handle(httpRequest);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of handle method, of class PutHandler.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testHandle() throws IOException {
        FileUtil.createFile(SimpleHTTPServer.WWW_DIR + "/test", "delete.html", "content");

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, "delete body");
        Request request = new Request.Builder()
          .url("http://localhost:8080/test/delete.html")
          .delete(body)
          .addHeader("cache-control", "no-cache")
          .addHeader("postman-token", "007f7fa5-0374-4b22-f256-2e756bfcc557")
          .build();

Response response = client.newCall(request).execute();

        assertTrue(response.code() == 204);

        File file = new File(SimpleHTTPServer.WWW_DIR + "/test/delete.html");
        assertTrue(!file.exists());
    }

    /**
     * Test of handle method, of class PutHandler.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testHandleNotFoundFolder() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, "delete body");
        Request request = new Request.Builder()
                .url("http://localhost:8080/test/not_found.html")
                .delete(body)
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "754e9ef6-c2e1-94a6-1b16-77189ce2c032")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println("code: " + response.code());

        assertTrue(response.code() == 404);
    }

    /**
     * Test of handle method, of class PutHandler.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testHandleAuthorizationNotPass() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, "delete body");
        Request request = new Request.Builder()
                .url("http://localhost:8080/secret/t.html")
                .delete(body)
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "754e9ef6-c2e1-94a6-1b16-77189ce2c032")
                .build();

        Response response = client.newCall(request).execute();

        assertTrue(response.code() == 401);
    }

    /**
     * Test of handle method, of class PutHandler.
     */
    @Test
    public void testHandleAuthorizationPassed() throws IOException {
        FileUtil.createFile(SimpleHTTPServer.WWW_DIR + "/secret", "delete2.html", "content");

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, "delete body");
        Request request = new Request.Builder()
                .url("http://localhost:8080/secret/delete2.html")
                .delete(body)
                .addHeader("Authorization", "Basic dXNlcjpwYXNzd29yZA==")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "93eb1895-4e61-7e73-2a1c-5b8e83f31722")
                .build();

        Response response = client.newCall(request).execute();

        assertTrue(response.code() == 204);

        File file = new File(SimpleHTTPServer.WWW_DIR + "/secret/delete2.html");
        assertTrue(!file.exists());
    }

}
