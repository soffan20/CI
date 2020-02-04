package dd2480.group4.net;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class BuildRequestTest {

    @Test
    void fromJson() throws IOException {

        //GIVEN
        String json = "{\"repository\" : {\"name\": \"soffan\", \"git_url\": \"url\", \"owner\" : {\"name\": \"testing\"}}, \"pusher\" : {\"name\": \"foo\", \"email\": \"bar\"}, \"before\": \"commithash\"}";

        //WHEN
        var buildRequest = BuildRequest.fromJson(json);

        //THEN
        assertEquals("foo", buildRequest.pusher.name, "name should be foo");
        assertEquals("bar", buildRequest.pusher.email, "email should be bar");
        assertEquals("commithash", buildRequest.hashId, "hashId should be commithash");
        assertEquals("url", buildRequest.repository.url, "url should be url");
        assertEquals("soffan", buildRequest.repository.name, "repo name should be soffan");
        assertEquals("testing", buildRequest.repository.owner.name, "owner name should be testing");


    }


}