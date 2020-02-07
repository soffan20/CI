package dd2480.group4.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public interface HttpInterface {
    HttpURLConnection post(URL url, String json) throws IOException;
}
