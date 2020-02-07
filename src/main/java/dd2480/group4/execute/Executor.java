package dd2480.group4.execute;

import dd2480.group4.net.NotificationInterface;
import dd2480.group4.net.PushEvent;

import java.io.IOException;

/**
 * Interface for running a build.
 */
public interface Executor {
    /**
     * Runs the build
     *
     * @param req the request to be built.
     */
    void runBuild(PushEvent req, NotificationInterface notification) throws IOException, InterruptedException;
}
