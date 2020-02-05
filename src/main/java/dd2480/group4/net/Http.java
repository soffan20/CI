package dd2480.group4.net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class Http {

    public static HttpURLConnection post(URL url, String json) throws IOException {
        byte[] data = json.getBytes();
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setDoOutput(true);
        http.setInstanceFollowRedirects(false);
        String auth = "soffanbot:a47e4ee5e7ded5b59962becd064ee30861194722";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(auth.getBytes()));
        http.setRequestProperty ("Authorization", basicAuth);
        http.setRequestMethod("POST");
        http.setRequestProperty("Content-Type", "application/json");
        http.setRequestProperty("charset", "utf-8");
        http.setRequestProperty("Content-Length", Integer.toString(data.length));
        http.setUseCaches(false);
        try (DataOutputStream wr = new DataOutputStream(http.getOutputStream())) {
            wr.write(data);
        }

        System.out.println("Notificiation status");
        System.out.println("====================");
        System.out.println("Url: " + url);
        System.out.println("Status response code: " + http.getResponseCode());
        System.out.println("Response body:\n" + http.getResponseMessage());
        System.out.println("");
        return http;
    }
}
