package dd2480.group4.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A class for notifying the end user of the status of a build run
 * by setting the commit status on the upstream repository
 */
public class Notification {

    /**
     * @param pushEvent The json classes from request from GitHub
     * @param status The status sent.
     * @param statusMessage The message accompanying the status.
     * @return The Http-connection with the given URL with payload and body
     * @throws IOException if it fails to send the message.
     */
    public HttpURLConnection setStatus(pushEvent pushEvent, Status status, String statusMessage) throws IOException {
        String repoName = pushEvent.repository.name;
        String ownerName = pushEvent.repository.owner.name;
        String hashId = pushEvent.hashId;
        var statusEndpoint = new URL("https://api.github.com/repos/" + repoName + "/" + ownerName + "/statuses/" + hashId);
        String jsonBody = "{\"state\": " + status + ", \"description\" : " + statusMessage + ", \"context\" :  \"soffan20/CI\"}";
        return Http.post(statusEndpoint, jsonBody);
    }

    /**
     * Valid statuses for updating GitHub commit status
     */

    enum Status {
        ERROR,
        FAILURE,
        PENDING,
        SUCCESS
    }
}
