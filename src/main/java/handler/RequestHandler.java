package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chlupnoha
 */
public class RequestHandler implements HttpHandler {

    private static final Logger LOG = Logger.getLogger(RequestHandler.class.getName());

    /**
     * Method for handling GET, DELETE, PUT methods
     * @param t
     * @throws IOException 
     */
    @Override
    public void handle(HttpExchange t) throws IOException {

        LOG.log(Level.INFO, "Handle HTTP {0}", new Object[]{t.toString()});

        //dont have to parse header 
        switch (t.getRequestMethod()) {
            case "GET":
//              GetHandler().handle(t);
                LOG.log(Level.INFO, "GET METHOD");
                return;
            case "DELETE":
                LOG.log(Level.INFO, "DELETE METHOD");
                return;
            case "PUT":
                LOG.log(Level.INFO, "PUT METHOD");
            }
    }
}
