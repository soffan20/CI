package dd2480.group4.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A class for notifying the end user of the status of a build run
 * by setting the commit status on the upstream repository
 */
public class Notification {

    public enum Status {
        error,
        failure,
        pending,
        success
    }

    public HttpURLConnection setStatus(BuildRequest buildRequest, Status status, String statusMessage) throws IOException {
        String repoName = buildRequest.repository.name;
        String ownerName = buildRequest.repository.owner.name;
        String hashId = buildRequest.hashId;
        var statusEndpoint = new URL("https://api.github.com/repos/" + repoName + "/" + ownerName + "/statuses/" + hashId);
        String jsonBody = "{\"state\": " + status + ", \"description\" : " + statusMessage + ", \"context\" :  \"soffan20/CI\"}";
        return Http.post(statusEndpoint, jsonBody);
    }
}
