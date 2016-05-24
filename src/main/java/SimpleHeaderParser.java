
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author chlupnoha
 */
public class SimpleHeaderParser {

    private String method;
    private String authorization;
    private String route;
    private String url;

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
        
    public SimpleHeaderParser(InputStream is) throws IOException {
        reader = new BufferedReader(new InputStreamReader(is));
        parseRequest();
    }

    private void parseRequest() throws IOException {
        boolean firstLine = true;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] e = line.split(" ");
            if(firstLine){
                method = e[0];
                if(e.length == 3){
                    route = e[1];
                }
                firstLine = false;
            }
            switch (e[0]) {
                case "Host:":
                    url = e[1];
                    break;
                case "Authorization:":
                    authorization = e[1];
                    break;
            }
        }
        //reader.close();
    }

}
