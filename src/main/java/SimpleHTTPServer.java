
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Guided by:
 * http://javarevisited.blogspot.com/2015/06/how-to-create-http-server-in-java-serversocket-example.html#ixzz49b6d5WqE
 * Java program to create a simple HTTP Server to demonstrate how to use *
 * ServerSocket and Socket class.
 *
 * @author chlupnoha
 * @author Javin Paul
 */
public class SimpleHTTPServer {

    private static final String WWW_DIR = "www";

    public static void main(String args[]) throws IOException {
        final ServerSocket server = new ServerSocket(8080);

        Logger.getLogger(SimpleHTTPServer.class.getName()).log(Level.INFO, "Listening for connection on port 8080 ....");

        FileUtil.createFolder(WWW_DIR);

        //Create Thread poll
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {

                    while (true) {
                        try {
                            Socket socket = server.accept();

//                            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
//                            BufferedReader reader = new BufferedReader(isr);
//                            String line;
                            System.out.println("---HEADER---");
//                            line = reader.readLine();
//                            while (!line.isEmpty()) {
//                                System.out.println(line);
//                                line = reader.readLine();
//                            }
                            //HttpServletRequest r = new HttpServletRequest();

                            //HttpParser parser = new HttpParser(socket.getInputStream());
                            SimpleHeaderParser parser = new SimpleHeaderParser(socket.getInputStream());
                            //System.out.println();
                            
                            System.out.println("---HEADER---");

                            //ZPRACOVANI HEADERU
                            Date today = new Date();
                            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
                            socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                            socket.close();
                        } catch (IOException ex) {
                            Logger.getLogger(SimpleHTTPServer.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }

            };
            thread.start();
            Logger.getLogger(SimpleHTTPServer.class.getName()).log(Level.INFO, "Create new thread; {0}", thread.getName());
        }
    }
}
