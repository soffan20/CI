package dd2480.group4.net;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Defines the structure of a request to build the project
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class pushEvent {

    @JsonProperty("repository")
    public Repository repository;
    @JsonProperty("before")
    public String hashId;
    @JsonProperty("pusher")
    public Pusher pusher;

    /**
     * @param json the json file to read
     * @return a class representation of the json request.
     * @throws IOException
     */
    public static pushEvent fromJson(String json) throws IOException {
        var mapper = new ObjectMapper();
        return mapper.readValue(json, pushEvent.class);
    }

    /**
     * The class represents the repository
     */
    public static class Repository {
        @JsonProperty("name")
        String name;
        @JsonProperty("owner")
        Owner owner;
        @JsonProperty("git_url")
        String url;
    }

    /**
     * The class represents the pusher of the commit.
     */
    public static class Pusher {
        @JsonProperty("email")
        String email;
        @JsonProperty("name")
        String name;

    }

    /**
     * The class represents the owner of the repository.
     */
    public static class Owner {
        @JsonProperty("name")
        String name;
    }
}
