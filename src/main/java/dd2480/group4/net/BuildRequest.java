package dd2480.group4.net;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Defines the structure of a request to build the project
 * TODO: should model the repository at a given state (i.e. url, commit hash, branch, time and date)
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class BuildRequest {

    @JsonProperty("repository")
    public Repository repository;
    @JsonProperty("before")
    public String hashId;
    @JsonProperty("pusher")
    public Pusher pusher;

    public static BuildRequest fromJson(String json) throws IOException {
        var mapper = new ObjectMapper();
        return mapper.readValue(json, BuildRequest.class);
    }

    public static class Repository {
        @JsonProperty("name")
        String name;
        @JsonProperty("owner")
        Owner owner;
        @JsonProperty("git_url")
        String url;
    }


    public static class Pusher {
        @JsonProperty("email")
        String email;
        @JsonProperty("name")
        String name;

    }

    public static class Owner {
        @JsonProperty("name")
        String name;
    }
}
