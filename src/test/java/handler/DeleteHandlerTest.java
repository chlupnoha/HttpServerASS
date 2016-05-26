/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import handlers.DeleteHandler;
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
import server.HttpExchanger;
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
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of handle method, of class PutHandler.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testHandle() throws IOException {
        FileUtil.createFile(SimpleHTTPServer.WWW_DIR, "delete.txt", "content");

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:8080/delete.txt")
                .delete(null)
                .addHeader("token", "ok2ffe9qubnk9dpmxgju5jlupmp6poijrxcw9zh99ttvsr5x7u")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "d1f26ec6-3ebf-283b-8421-428196612ab5")
                .build();

        Response response = client.newCall(request).execute();

        assertTrue(response.code() == 204);

        File file = new File(SimpleHTTPServer.WWW_DIR + "/delete.txt");
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

        Request request = new Request.Builder()
                .url("http://localhost:8080/unknown_file.txt")
                .delete(null)
                .addHeader("token", "ok2ffe9qubnk9dpmxgju5jlupmp6poijrxcw9zh99ttvsr5x7u")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "d1f26ec6-3ebf-283b-8421-428196612ab5")
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
        FileUtil.createFile(SimpleHTTPServer.WWW_DIR, "secret/delete.txt", "content");

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:8080/secret/delete.txt")
                .delete(null)
                .addHeader("token", "ok2ffe9qubnk9dpmxgju5jlupmp6poijrxcw9zh99ttvsr5x7u")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "d1f26ec6-3ebf-283b-8421-428196612ab5")
                .build();

        Response response = client.newCall(request).execute();

        assertTrue(response.code() == 401);
    }

    /**
     * Test of handle method, of class PutHandler.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testHandleAuthorizationPassed() throws IOException {
        FileUtil.createFile(SimpleHTTPServer.WWW_DIR, "secret/delete.txt", "content");

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:8080/secret/delete.txt")
                .delete(null)
                .addHeader("Authorization", "Basic dXNlcjpwYXNzd29yZA==")
                .addHeader("token", "ok2ffe9qubnk9dpmxgju5jlupmp6poijrxcw9zh99ttvsr5x7u")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "d1f26ec6-3ebf-283b-8421-428196612ab5")
                .build();

        Response response = client.newCall(request).execute();

        assertTrue(response.code() == 204);

        File file = new File(SimpleHTTPServer.WWW_DIR + "/secret/delete.txt");
        assertTrue(!file.exists());
    }

}
