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
    @JsonProperty("after")
    public String hashId;
    @JsonProperty("pusher")
    public Pusher pusher;

    public static BuildRequest fromJson(String json) throws IOException {
        var mapper = new ObjectMapper();
        return mapper.readValue(json, BuildRequest.class);
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class Repository {
        @JsonProperty("name")
        public String name;
        @JsonProperty("owner")
        public Owner owner;
        @JsonProperty("git_url")
        public String url;
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class Pusher {
        @JsonProperty("email")
        public String email;
        @JsonProperty("name")
        public String name;

    }
    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class Owner {
        @JsonProperty("name")
        public String name;
    }
}
