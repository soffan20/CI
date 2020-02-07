package dd2480.group4.net;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegressionTest {

    @Test
    public void Issue21() throws IOException {
        // GIVEN
        String json = "{\"car\": \"volvo\", \"git_url\": \"url\", \"pusher\" : {\"name\": \"foo\", \"email\": \"bar\"}, \"after\": \"commithash\"}";

        // WHEN
        var buildRequest = PushEvent.fromJson(json);

        // THEN
        assertTrue(true, "testing that class buildRequest can ignore properties");
    }
}
