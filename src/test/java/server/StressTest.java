package server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * source: http://hc.apache.org/httpclient-3.x/threading.html
 *
 * @author chlupnoha
 */
public class StressTest {

    public StressTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        String[] args = {};
        SimpleHTTPServer.main(args);
    }

    /**
     * Test of handle method, of class PutHandler.
     *
     * @throws java.io.IOException
     */
    @Test
    public void test100ReuestsInSameTime() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:8080/text.txt")
                .get()
                .addHeader("authorization", "Basic dXNlcjpwYXNzd29yZA==")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "ecf47821-3ef8-792b-a2ac-f66be68aad44")
                .build();

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {//dunno whats findBugs problem
                    try {
                        Response response = client.newCall(request).execute();
                        Assert.assertTrue(response.code() == 200);
                    } catch (IOException ex) {
                        Logger.getLogger(SimpleHTTPServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            };
            thread.start();
            Logger.getLogger(SimpleHTTPServer.class.getName()).log(Level.INFO, "Create new request; {0}", thread.getName());
        }

    }
    
}

