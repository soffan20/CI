package dd2480.group4.net;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationTest {


    @Test
    public void setStatusTest() throws IOException {
        Notification notification = new Notification(new FakeHttpUrlConnection(null, null));
        PushEvent pe = new PushEvent();
        pe.repository = new PushEvent.Repository();
        pe.repository.name = "Repo-name";
        pe.repository.url = "URL";
        pe.repository.owner = new PushEvent.Owner();
        pe.repository.owner.name = "Repo-owner";
        pe.hashId = "2";
        pe.pusher = new PushEvent.Pusher();
        pe.pusher.name = "Pusher-name";
        pe.pusher.email = "a@mail.com";

        FakeHttpUrlConnection result = (FakeHttpUrlConnection) notification.setStatus(pe, Notification.Status.PENDING, "foo");
        String json = result.json;
        URL url = result.url;
        assertEquals("https://api.github.com/repos/Repo-owner/Repo-name/statuses/2", url.toString(), "The URL is incorrect");
        String[] parts = json.split(" ");
        assertEquals("\"pending\",", parts[1], "The status message is incorrect");
        assertEquals("\"foo\",", parts[4], "The description is incorrect");
    }

    private class FakeHttpUrlConnection extends HttpURLConnection implements HttpInterface {
        public URL url;
        public String json;

        public FakeHttpUrlConnection(URL url, String json) {
            super(url);
            this.url = url;
            this.json = json;
        }

        @Override
        public HttpURLConnection post(URL url, String json) throws IOException {
            return new FakeHttpUrlConnection(url, json);
        }

        @Override
        public void disconnect() {

        }

        @Override
        public boolean usingProxy() {
            return false;
        }

        @Override
        public void connect() throws IOException {

        }
    }
}
