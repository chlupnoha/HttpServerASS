package server;

import com.sun.net.httpserver.HttpContext;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chlupnoha
 */
public class HttpImplServer implements Runnable {

    private static final Logger LOG = Logger.getLogger(HttpImplServer.class.getName());

    static final int SERVER_PORT = 8000;
    static final String FILES_DIR = "upload";
    static final String WEB_CONTENT_DIR = "www";

    private int corePoolSize = 10;
    private int maxPoolSize = 200;
    private long keepAliveTime = 2000;

    private HttpServer httpImplServer;

    public HttpImplServer() {

    }

    public HttpImplServer(int corePoolSize, int maxPoolSize, long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveTime = keepAliveTime;
    }
    
    public static void main(String[] args) {
        HttpImplServer s = new HttpImplServer();
        s.run();
    }

    @Override
    public void run() {
        try {

            ExecutorService threadPoolExecutor
                    = new ThreadPoolExecutor(
                            corePoolSize,
                            maxPoolSize,
                            keepAliveTime,
                            TimeUnit.MILLISECONDS,
                            new LinkedBlockingQueue<Runnable>()
                    );

            httpImplServer = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
            
            HttpContext cc = httpImplServer.createContext("/", new RequestHandler());
            cc.setAuthenticator(null);

            new File(FILES_DIR).mkdir();
            new File(WEB_CONTENT_DIR).mkdir();

            httpImplServer.setExecutor(threadPoolExecutor);
            httpImplServer.start();
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Server cant be initialize, Exception: {0}", new Object[]{e.toString()});
        }

    }
}
