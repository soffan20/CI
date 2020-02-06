package dd2480.group4.net;

import com.fasterxml.jackson.core.io.JsonEOFException;
import dd2480.group4.execute.Executor;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * RequestHandler defines the jetty handler for the receiving GitHub webhooks
 */
public class RequestHandler extends AbstractHandler {

    Executor executor;
    boolean async;
    NotificationInterface notification;

    /**
     * Constructs a request handler, responsible for handling requests from jetty.
     * The request handler listens for webhooks and starts a new build with the given Executor
     *
     * @param executor the Executor responsible for running the build job
     * @param async    Specifies whether the handler runs the build executor in a separate thread
     */
    public RequestHandler(Executor executor, NotificationInterface notification, boolean async) {
        this.executor = executor;
        this.async = async;
        this.notification = notification;
    }

    /**
     * Constructs a request handler, responsible for handling requests from jetty.
     * The request handler listens for webhooks and starts a new build with the given Executor
     * @param executor the Executor responsible for running the build job
     */
    public RequestHandler(Executor executor, NotificationInterface notification) {
        this.executor = executor;
        this.async = true;
        this.notification = notification;
    }

    /**
     * handle defines the actual handler used by jetty to respond to HTTP requests.
     * It expects the webhook JSON in the body of a HTTP request. The handler validates
     * the json, starts the build in a new thread and returns 200.
     * @param target The target of the request - either a URI or a name.
     * @param baseRequest The original unwrapped request object.
     * @param request The request either as the {@link Request} object or a wrapper of that request. The
     * <code>{@link HttpConnection#getCurrentConnection()}.{@link HttpConnection#getHttpChannel() getHttpChannel()}.{@link HttpChannel#getRequest() getRequest()}</code>
     * method can be used access the Request object if required.
     * @param response The response as the {@link Response} object or a wrapper of that request. The
     * <code>{@link HttpConnection#getCurrentConnection()}.{@link HttpConnection#getHttpChannel() getHttpChannel()}.{@link HttpChannel#getResponse() getResponse()}</code>
     * method can be used access the Response object if required.
     */
    @Override
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
        baseRequest.setHandled(true);
        try {
            var bytes = request.getInputStream().readAllBytes();
            var json = new String(bytes, StandardCharsets.UTF_8);
            var buildRequest = PushEvent.fromJson(json);
            if (async) {
                new Thread(() ->
                {
                    try {
                        executor.runBuild(buildRequest, notification);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ).start();

            } else {
                executor.runBuild(buildRequest, notification);
            }
            response.setStatus(HttpStatus.OK_200);
        } catch (JsonEOFException e) {
            System.err.println("Error parsing json: " + e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST_400);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}