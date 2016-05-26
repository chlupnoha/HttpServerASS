package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author chlupnoha
 */
public class SimpleRequestParser {

    private String method;
    private String authorization;
    private String route;
    private String url;
    private String body = "";

    private final BufferedReader reader;

    public String getMethod() {
        return method;
    }

    public String getAuthorization() {
        return authorization;
    }

    public String getUrl() {
        return url;
    }

    public String getRoute() {
        return route;
    }

    public String getBody() {
        return body;
    }

    public SimpleRequestParser(InputStream is) throws IOException {
//        setByteBody(is);
        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        parseRequest();
    }

    private void parseRequest() throws IOException {
        boolean firstLine = true;
        int length = 0;

        String line = reader.readLine();
        while (line != null && line.length() > 0) {
            String[] e = line.split(" ");
            if (firstLine) {
                method = e[0];
                if (e.length == 3) {
                    route = e[1];
                }
                firstLine = false;
            }
            switch (e[0]) {
                case "Host:":
                    url = e[1];
                    break;
                case "Authorization:":
                    authorization = line.replace("Authorization: ", "");
                    break;
                case "Content-Length:":
                    length = Integer.parseInt(e[1]);
                    break;
                default:
                    break;

            }
            line = reader.readLine();
        }

        if (length > 0) {
            int ch;
            while ((ch = reader.read()) != -1) {
                body += ((char) ch);
                if (body.length() == length) {
                    break;
                }
            }
        }
        //reader.close();
    }

//    private void setByteBody(InputStream is) throws IOException{
//        byte[] bytes = IOUtils.toByteArray(is);
//        //pokus o hledani 2 "\n"
//        for(byte b : bytes){
//            System.out.println("b: " + b);
//        }
//        System.out.println("\n".getBytes(StandardCharsets.UTF_8));
//    }
}
