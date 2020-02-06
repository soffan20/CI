package dd2480.group4.net;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface NotificationInterface {
    HttpURLConnection setStatus(PushEvent pushEvent, Notification.Status status, String statusMessage) throws IOException;
}
