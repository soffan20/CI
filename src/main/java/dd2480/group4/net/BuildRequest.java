package dd2480.group4.net;

/**
 * Defines the structure of a request to build the project
 * TODO: should model the repository at a given state (i.e. url, commit hash, branch, time and date)
 */
public class BuildRequest {
    String json;
    public BuildRequest(String json) {
        this.json = json;
    }
}
