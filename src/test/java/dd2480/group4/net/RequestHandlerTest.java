package dd2480.group4.net;

import dd2480.group4.execute.Executor;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

class   RequestHandlerTest {
    @Test
    public void handle() throws Exception {
        // GIVEN
        var executor = new Executor() {
            public PushEvent request;

            @Override
            public void runBuild(PushEvent req, NotificationInterface notification) {
                this.request = req;

            }
        };
        var server = newServer(executor);
        var url = toUrl(server);
        var http = Http.post(url, "{\"git_url\": \"url\", \"pusher\" : {\"name\": \"foo\", \"email\": \"bar\"}, \"after\": \"commithash\"}");
        assertEquals(http.getResponseCode(), HttpStatus.OK_200, "response code should be OK");

        // WHEN
        var response = Http.post(url, "{\"repository\": {\"git_url\": \"url\"}, \"pusher\" : {\"name\": \"foo\", \"email\": \"bar\"}, \"after\": \"commithash\"}");
        var invalidJson = Http.post(url, "{\"");

        // THEN
        assertEquals(HttpStatus.BAD_REQUEST_400, invalidJson.getResponseCode(), "response code should be 500 for invalid JSON");
        assertEquals(HttpStatus.OK_200, response.getResponseCode(), "response code should be OK for valid JSON");
        assertEquals("foo", executor.request.pusher.name, "Expected json name to be set to foo");
        assertEquals("bar", executor.request.pusher.email, "email should be bar");
        assertEquals("commithash", executor.request.hashId, "hashId should be commithash");
        assertEquals("url", executor.request.repository.url, "url should be url");
        server.stop();
    }

    private Server newServer(Executor executor) {
        var rng = ThreadLocalRandom.current();
        var port = rng.nextInt(1000) + 8000;
        var server = new Server(port);
        server.setHandler(new RequestHandler(executor, new Notification(), false));
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return server;
    }

    private URL toUrl(Server server) throws IOException {
        return new URL("http", "localhost", server.getURI().getPort(), "/");
    }
}
