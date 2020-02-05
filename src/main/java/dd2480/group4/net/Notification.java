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
     * @param pushEvent The information of the repository name, owner name and hashid where the message gets sent
     * @param status The status sent.
     * @param statusMessage The message accompanying the status.
     * @return The Http-connection with the given URL where status and message is sent.
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

    enum Status {
        ERROR,
        FAILURE,
        PENDING,
        SUCCESS
    }
}
