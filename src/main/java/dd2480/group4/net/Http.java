package dd2480.group4.net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http requests used to update commit status on GitHub
 */
public class Http implements HttpInterface {

    /**
     * @param url  The endpoint to send the POST request to
     * @param json The body in the request in json format
     * @return Returns a HttpURLConnection with the given URL ready to send the JSON.
     * @throws IOException when it fails to send the message.
     */
    public HttpURLConnection post(URL url, String json) throws IOException {
        byte[] data = json.getBytes();
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setDoOutput(true);
        http.setInstanceFollowRedirects(false);
        String basicAuth = "Basic c29mZmFuYm90OjRlNzU3MjFiY2E0NTgzOTU5MDJlNDY0ZDJiOTc0MzUyMWIyNjk3NDc=";
        http.setRequestProperty("Authorization", basicAuth);
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
        System.out.println();
        return http;
    }
}
