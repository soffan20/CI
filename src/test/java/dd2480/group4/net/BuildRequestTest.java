package dd2480.group4.net;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BuildRequestTest {

    @Test
    void fromJson() throws IOException {

        //GIVEN
        String json = "{\"repository\" : {\"id\": \"12345\", \"git_url\": \"url\", \"owner\" : {\"id\": \"456789\"},}, \"pusher\" : {\"name\": \"foo\", \"email\": \"bar\"}, \"after\": \"commithash\"}";

        //WHEN
        var buildRequest = BuildRequest.fromJson(json);

        //THEN
        assertEquals("foo", buildRequest.pusher.name, "name should be foo");
        assertEquals("bar", buildRequest.pusher.email, "email should be bar");
        assertEquals("commithash", buildRequest.hashId, "hashId should be commithash");
        assertEquals("url", buildRequest.repository.url, "url should be url");
        assertEquals("12345", buildRequest.repository.id, "repo id should be 12345");
        assertEquals("456789", buildRequest.repository.owner.id, "owner id should be 456789");


    }
}